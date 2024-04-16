package com.example.funfusion

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.zxing.integration.android.IntentIntegrator
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }

        val fillFormBtn=findViewById<Button>(R.id.fillFormBtn)
        fillFormBtn.setOnClickListener {
            startActivity(Intent(this,CreateAccountActivity::class.java))
            finish()
        }

        val userDetailsFile = File(cacheDir, "user_details.json")

        if (userDetailsFile.exists()) {
            startActivity(Intent(this,TabbarActivity::class.java))
            finish()
        }

        val qrCodeImage = findViewById<ImageView>(R.id.qrCodeIcon)
        qrCodeImage.setOnClickListener {
            startQrCodeScan()
        }
    }

    private fun startQrCodeScan() {
        val integrator = IntentIntegrator(this)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val jsonObject = JsonParser.parseString(result.contents).asJsonObject

                if (isUserDetailsJSON(jsonObject)) {
                    Log.i("qrCodeScan", jsonObject.toString())

                    val userDetails = UserDetails(
                        jsonObject.get("lastName").asString,
                        jsonObject.get("firstName").asString,
                        jsonObject.get("email").asString,
                        jsonObject.get("address").asString,
                        jsonObject.get("zipcode").asString,
                        jsonObject.get("city").asString,
                        jsonObject.get("cardRef").asString
                    )

                    val gson = Gson()
                    val json = gson.toJson(userDetails)


                    val intent = Intent(this, CreateAccountActivity::class.java)
                    intent.putExtra("user_details_json", json)
                    startActivity(intent)
                } else {
                    val errorMessage = Snackbar.make(
                        findViewById(R.id.mainActivityLayout),
                        R.string.account_creation_error_wrong_qrcode,
                        Snackbar.LENGTH_SHORT
                    )

                    errorMessage.show()
                }
            }
        }
    }

    private fun isUserDetailsJSON(jsonObject: JsonObject): Boolean {
        return jsonObject.has("lastName") &&
                jsonObject.has("firstName") &&
                jsonObject.has("email") &&
                jsonObject.has("address") &&
                jsonObject.has("zipcode") &&
                jsonObject.has("city") &&
                jsonObject.has("cardRef")
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}