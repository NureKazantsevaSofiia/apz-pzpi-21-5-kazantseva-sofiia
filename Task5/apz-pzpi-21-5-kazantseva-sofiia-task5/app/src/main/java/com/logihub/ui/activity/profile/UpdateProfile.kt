package com.logihub.ui.activity.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.logihub.R
import com.logihub.model.request.UpdateUserRequest
import com.logihub.model.response.TruckManagerDTO
import com.logihub.service.ApiServiceImpl
import com.logihub.ui.activity.general.MainPage
import com.logihub.util.Validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfile : AppCompatActivity() {

    private val activity: AppCompatActivity = this@UpdateProfile
    private lateinit var token: String
    private lateinit var userId: String

    private val apiService = ApiServiceImpl()
    private val validator = Validator()

    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameInput: TextInputEditText
    private lateinit var updateButton: Button

    private lateinit var firstNameContainer: TextInputLayout
    private lateinit var lastNameContainer: TextInputLayout
    private lateinit var avatar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        init()
    }

    private fun init() {
        userId = MainPage.getUserId().toString()
        token = MainPage.getToken().toString()

        firstNameInput = findViewById(R.id.firstName)
        lastNameInput = findViewById(R.id.lastName)

        firstNameContainer = findViewById(R.id.firstNameContainer)
        lastNameContainer = findViewById(R.id.lastNameContainer)

        updateButton = findViewById(R.id.updateProfile)

        updateButton.let { s ->
            s.setOnClickListener {
                updateProfile()
            }
        }

        getUser()
        validate()
    }

    private fun getUser() {
        apiService.getManager("Bearer " + token, userId.toLong(), object :
            Callback<TruckManagerDTO> {
            override fun onResponse(
                call: Call<TruckManagerDTO>,
                response: Response<TruckManagerDTO>
            ) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        avatar = user.avatar
                        fillData(user)
                    } else {
                        Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(activity, Profile::class.java)
                        startActivity(intent)
                        activity.finish()
                    }
                } else {
                    Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                    val intent = Intent(activity, Profile::class.java)
                    startActivity(intent)
                    activity.finish()
                }
            }

            override fun onFailure(call: Call<TruckManagerDTO>, t: Throwable) {
                Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG).show()
                val intent = Intent(activity, Profile::class.java)
                startActivity(intent)
                activity.finish()
            }
        })
    }

    fun fillData(user: TruckManagerDTO) {
        firstNameInput.setText(user.firstName)
        lastNameInput.setText(user.lastName)
    }

    private fun validate() {
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

    private fun validateFirstName(): String {
        return validator.validateFirstName(firstNameInput.text.toString().trim())
    }

    private fun validateLastName(): String {
        return validator.validateLastName(lastNameInput.text.toString().trim())
    }

    private fun updateProfile() {
        val firstName = firstNameInput.text?.toString()
        val lastName = lastNameInput.text?.toString()
        avatar = ""

        if (firstName != null && lastName != null) {

            val user = UpdateUserRequest(
                firstName,
                lastName,
                avatar
            )

            apiService.updateManager("Bearer " + token, userId.toLong(), user,
                object :
                    Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (!response.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "Something went wrong during updating!",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        } else {
                            Toast.makeText(activity, "User is updated!", Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent(activity, MainPage::class.java)
                            intent.putExtra("userId", userId.toLong())
                            intent.putExtra("token", token)
                            startActivity(intent)
                            activity.finish()
                        }

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(
                            activity,
                            "Something went wrong during updating!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                })
        }
    }
}