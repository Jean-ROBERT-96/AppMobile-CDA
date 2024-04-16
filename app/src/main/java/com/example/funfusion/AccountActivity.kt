package com.example.funfusion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AccountActivity : AppCompatActivity() {
    private val userDetailsFile: File by lazy {
        File(cacheDir, "user_details.json")
    }

    private lateinit var editTextLastname: EditText
    private lateinit var editTextFirstname: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextPostalCode: EditText
    private lateinit var editTextCity: EditText
    private lateinit var editTextFidelityCard: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        editTextLastname = findViewById(R.id.editTextLastname)
        editTextFirstname = findViewById(R.id.editTextFirstname)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextPostalCode = findViewById(R.id.editTextPostalCode)
        editTextCity = findViewById(R.id.editTextCity)
        editTextFidelityCard = findViewById(R.id.editTextFidelityCard)

        val editTextArray = arrayOf(
            editTextLastname,
            editTextFirstname,
            editTextEmail,
            editTextAddress,
            editTextPostalCode,
            editTextCity,
            editTextFidelityCard
        )

        val submitBtn = findViewById<Button>(R.id.submitBtn)
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

                updateJson(userDetails)

                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val errorMessage = Snackbar.make(
                    findViewById(R.id.accountLayout),
                    R.string.account_creation_error_empty_field,
                    Snackbar.LENGTH_SHORT
                )
                errorMessage.show()
            }
        }

        readJsonAndPopulateFields()
    }

    private fun updateJson(userDetails: UserDetails) {
        val gson = Gson()
        val json = gson.toJson(userDetails)

        try {
            val outputStream = FileOutputStream(userDetailsFile)
            outputStream.write(json.toByteArray())
            outputStream.close()

            startActivity(Intent(this,HomeActivity::class.java))
        } catch (e: IOException) {
            e.printStackTrace()

            val errorMessage = Snackbar.make(
                findViewById(R.id.accountLayout),
                R.string.common_error,
                Snackbar.LENGTH_SHORT
            )
            errorMessage.show()
        }
    }

    private fun readJsonAndPopulateFields() {
        if (userDetailsFile.exists()) {
            try {
                val json = userDetailsFile.readText()
                val userDetails = Gson().fromJson(json, UserDetails::class.java)

                editTextLastname.setText(userDetails.lastName)
                editTextFirstname.setText(userDetails.firstName)
                editTextEmail.setText(userDetails.email)
                editTextAddress.setText(userDetails.address)
                editTextPostalCode.setText(userDetails.zipCode)
                editTextCity.setText(userDetails.city)
                editTextFidelityCard.setText(userDetails.cardRef)
            } catch (e: IOException) {
                e.printStackTrace()

                val errorMessage = Snackbar.make(
                    findViewById(R.id.accountLayout),
                    R.string.common_error,
                    Snackbar.LENGTH_SHORT
                )
                errorMessage.show()
            }
        }
    }
}
