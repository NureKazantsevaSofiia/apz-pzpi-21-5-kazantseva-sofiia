package com.logihub.ui.activity.profile

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.logihub.R
import com.logihub.model.response.TruckManagerDTO
import com.logihub.service.ApiServiceImpl
import com.logihub.ui.activity.general.Login
import com.logihub.ui.activity.general.MainPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class Profile : Fragment() {

    private val activity: Fragment = this@Profile
    private lateinit var token: String
    private var userId: Long = 0L

    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var email: TextView
    private lateinit var company: TextView
    private lateinit var language: TextView
    private lateinit var lang: String

    private val apiService = ApiServiceImpl()
    private lateinit var editUserButton: MaterialButton
    private lateinit var signout: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.activity_profile, container, false)
        init(v)
        return v
    }

    private fun init(v: View) {
        userId = MainPage.getUserId()!!
        token = MainPage.getToken().toString()

        firstName = v.findViewById(R.id.firstNameText)
        lastName = v.findViewById(R.id.lastNameText)
        email = v.findViewById(R.id.emailText)
        company = v.findViewById(R.id.companyText)
        language = v.findViewById(R.id.translate)

        editUserButton = v.findViewById(R.id.editUserButton)
        signout = v.findViewById(R.id.singOut)

        lang = if (Locale.getDefault().language == "en") {
            "uk"
        } else {
            "en"
        }

        language.let { s ->
            s.setOnClickListener {
                setLocale(lang)
                lang = if (lang == "en") {
                    "uk"
                } else {
                    "en"
                }
            }
        }

        editUserButton.let { e ->
            e.setOnClickListener {
                editUser()
            }
        }

        signout.let { s ->
            s.setOnClickListener {
                logout()
            }
        }

        getUser()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun getUser() {
        apiService.getManager("Bearer " + token, userId, object :
            Callback<TruckManagerDTO> {
            override fun onResponse(
                call: Call<TruckManagerDTO>,
                response: Response<TruckManagerDTO>
            ) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        fillData(user)
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<TruckManagerDTO>, t: Throwable) {
                Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun fillData(user: TruckManagerDTO) {
        firstName.text = user.firstName
        lastName.text = user.lastName
        email.text = email.text.toString() + ": " + user.email
        company.text = company.text.toString() + ": " + user.company.name
    }

    private fun editUser() {
        val intent = Intent(activity.context, UpdateProfile::class.java)
        startActivity(intent)
    }

    private fun logout() {
        val intent = Intent(activity.context, Login::class.java)
        startActivity(intent)
        activity.requireParentFragment().requireActivity().finish()
    }
}