package com.example.funfusion.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.funfusion.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.WriterException
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ResultParser
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.Result

class QRCodeFragment : Fragment() {

    private lateinit var imageViewQRCode: ImageView
    private lateinit var textViewName: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_qrcode, container, false)
        imageViewQRCode = view.findViewById(R.id.imageViewQRCode)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        textViewName = view.findViewById(R.id.textViewName)


        val qrCodeData = "Your QR Code Data Here"


        generateQRCode(qrCodeData)


        extractDataFromQRCode()
    }

    private fun generateQRCode(data: String) {
        val qrCodeWriter = QRCodeWriter()
        try {
            val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            imageViewQRCode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }

    }

    private fun extractDataFromQRCode() {
        val bitmap = (imageViewQRCode.drawable as? BitmapDrawable)?.bitmap ?: return

        // Convert the Bitmap to a binary bitmap
        val source = RGBLuminanceSource(bitmap.width, bitmap.height, IntArray(bitmap.width * bitmap.height).also {
            bitmap.getPixels(it, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        })
        val binarizer = HybridBinarizer(source)
        val binaryBitmap = BinaryBitmap(binarizer)

        // Decode the binary bitmap to a Result
        val reader = QRCodeReader()
        try {
            val result: Result = reader.decode(binaryBitmap)
            // Parse the result to get data
            val parsedResult: ParsedResult = ResultParser.parseResult(result)
            val data = parsedResult.displayResult
            // Update TextView with extracted data
            textViewName.text = data

            val namePattern = Regex("Nom: (\\w+), Prénom: (\\w+)")
            val matchResult = namePattern.find(data)
            val (nom, prenom) = matchResult!!.destructured

            // Imprimer le nom et le prénom dans les logs
            Log.d("QRCodeFragment", "Nom: $nom, Prénom: $prenom")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(): QRCodeFragment {
            return QRCodeFragment()
        }
    }
}
