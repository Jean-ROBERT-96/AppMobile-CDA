package com.example.funfusion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.funfusion.qrcode.DisplayQRCodeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val editTextLastname = findViewById<EditText>(R.id.editTextLastname)
        val editTextFirstname = findViewById<EditText>(R.id.editTextFirstname)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextAddress = findViewById<EditText>(R.id.editTextAddress)
        val editTextPostalCode = findViewById<EditText>(R.id.editTextPostalCode)
        val editTextCity = findViewById<EditText>(R.id.editTextCity)
        val editTextFidelityCard = findViewById<EditText>(R.id.editTextFidelityCard)

        val userDetailsJson = intent.getStringExtra("user_details_json")

        if (userDetailsJson != null) {
            val gson = Gson()
            val userDetails = gson.fromJson(userDetailsJson, UserDetails::class.java)

            editTextLastname.setText(userDetails.lastName)
            editTextFirstname.setText(userDetails.firstName)
            editTextEmail.setText(userDetails.email)
            editTextAddress.setText(userDetails.address)
            editTextPostalCode.setText(userDetails.zipCode)
            editTextCity.setText(userDetails.city)
            editTextFidelityCard.setText(userDetails.cardRef)
        }


        val editTextArray = arrayOf(
            editTextLastname,
            editTextFirstname,
            editTextEmail,
            editTextAddress,
            editTextPostalCode,
            editTextCity,
            editTextFidelityCard
        )

        val submitBtn=findViewById<Button>(R.id.submitBtn)
        submitBtn.setOnClickListener {
            var emptyFields = false

            for (editText in editTextArray) {
                if (editText.text.isEmpty()) {
                    emptyFields = true

                    break
                }
            }

            if (!emptyFields) {
                val userDetails = UserDetails(
                    editTextLastname.text.toString(),
                    editTextFirstname.text.toString(),
                    editTextEmail.text.toString(),
                    editTextAddress.text.toString(),
                    editTextPostalCode.text.toString(),
                    editTextCity.text.toString(),
                    editTextFidelityCard.text.toString()
                )

                val gson = Gson()
                val json = gson.toJson(userDetails)

                val file = File(cacheDir, "user_details.json")
                try {
                    val outputStream = FileOutputStream(file)
                    outputStream.write(json.toByteArray())
                    outputStream.close()
                    val intent = Intent(this, DisplayQRCodeActivity::class.java)
                    intent.putExtra("user_details_json", json)
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                } catch (e: IOException) {
                    e.printStackTrace()

                    val errorMessage = Snackbar.make(
                        findViewById(R.id.createAccountLayout),
                        R.string.common_error,
                        Snackbar.LENGTH_SHORT
                    )

                    errorMessage.show()
                }
            } else {
                val errorMessage = Snackbar.make(
                    findViewById(R.id.createAccountLayout),
                    R.string.account_creation_error_empty_field,
                    Snackbar.LENGTH_SHORT
                )

                errorMessage.show()
            }
        }
    }
}