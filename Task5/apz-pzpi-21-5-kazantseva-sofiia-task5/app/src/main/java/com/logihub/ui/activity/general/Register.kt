package com.logihub.ui.activity.general

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.logihub.R
import com.logihub.model.request.UserRequest
import com.logihub.service.ApiServiceImpl
import com.logihub.util.Validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {

    private val activity: AppCompatActivity = this@Register
    private val apiService = ApiServiceImpl()
    private val validator = Validator()
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameInput: TextInputEditText
    private lateinit var emailContainer: TextInputLayout
    private lateinit var passwordContainer: TextInputLayout
    private lateinit var firstNameContainer: TextInputLayout
    private lateinit var lastNameContainer: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        emailInput = findViewById<TextInputEditText>(R.id.emailEditText)
        passwordInput = findViewById<TextInputEditText>(R.id.passwordEditText)
        firstNameInput = findViewById<TextInputEditText>(R.id.firstNameEditText)
        lastNameInput = findViewById<TextInputEditText>(R.id.lastNameEditText)

        emailContainer = findViewById<TextInputLayout>(R.id.emailContainer)
        passwordContainer = findViewById<TextInputLayout>(R.id.passwordContainer)
        firstNameContainer = findViewById<TextInputLayout>(R.id.firstNameContainer)
        lastNameContainer = findViewById<TextInputLayout>(R.id.lastNameContainer)

        validate()
    }

    private fun validate() {
        emailInput.let { u ->
            u.doOnTextChanged { _, _, _, _ ->
                emailContainer.let { c -> c.helperText = validateEmail() }
            }
        }
        passwordInput.let { u ->
            u.doOnTextChanged { _, _, _, _ ->
                passwordContainer.let { c -> c.helperText = validatePassword() }
            }
        }
        firstNameInput.let { u ->
            u.doOnTextChanged { _, _, _, _ ->
                firstNameContainer.let { c -> c.helperText = validateFirstName() }
            }
        }
        lastNameInput.let { u ->
            u.doOnTextChanged { _, _, _, _ ->
                lastNameContainer.let { c -> c.helperText = validateLastName() }
            }
        }

    }

    private fun validateEmail(): String {
        return validator.validateEmail(emailInput.text.toString().trim())
    }

    private fun validatePassword(): String {
        return validator.validatePassword(passwordInput.text.toString().trim())
    }

    private fun validateFirstName(): String {
        return validator.validateFirstName(firstNameInput.text.toString().trim())
    }

    private fun validateLastName(): String {
        return validator.validateLastName(lastNameInput.text.toString().trim())
    }

    fun register(view: View) {

        val user = UserRequest(
            firstNameInput.text.toString().trim(),
            lastNameInput.text.toString().trim(),
            emailInput.text.toString().trim(),
            passwordInput.text.toString().trim(),
            3L
        )

        Log.d("User", user.toString())

        apiService.register(user,
            object :
                Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (!response.isSuccessful) {
                        Log.d("Error1", response.code().toString())
                        AlertDialog.Builder(activity)
                            .setTitle("Sing up")
                            .setMessage("Something went wrong. Try again later.")
                            .setPositiveButton("Okay") { _, _ ->
                                val intent = Intent(activity, Login::class.java)
                                startActivity(intent)
                                activity.finish()
                            }
                            .show()
                    } else {
                        AlertDialog.Builder(activity)
                            .setTitle("Sign up")
                            .setMessage("Registration was successful. You can try to log into your account.")
                            .setPositiveButton("Okay") { _, _ ->
                                val intent = Intent(activity, Login::class.java)
                                startActivity(intent)
                                activity.finish()
                            }
                            .show()
                    }

                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("Error2", t.message.toString())
                    AlertDialog.Builder(activity)
                        .setTitle("Sing up")
                        .setMessage("Something went wrong. Try again later.")
                        .setPositiveButton("Okay") { _, _ ->
                            val intent = Intent(activity, Login::class.java)
                            startActivity(intent)
                            activity.finish()
                        }
                        .show()
                }
            })

    }
}