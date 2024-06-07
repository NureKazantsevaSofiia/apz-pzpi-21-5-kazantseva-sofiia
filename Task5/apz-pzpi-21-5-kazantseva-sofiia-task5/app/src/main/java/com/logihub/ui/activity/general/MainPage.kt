package com.logihub.ui.activity.general

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.logihub.R
import com.logihub.ui.activity.invoice.InvoicesPage
import com.logihub.ui.activity.profile.Profile
import com.logihub.ui.activity.truck.TrucksPage

class MainPage : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    private val activity: BottomNavigationView.OnNavigationItemSelectedListener = this@MainPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        getExtra()

        bottomNavigationView = findViewById(R.id.bottomMenu);

        bottomNavigationView.let { b ->
            b.setOnNavigationItemSelectedListener(activity)
            b.setSelectedItemId(R.id.invoices)
            b.setItemIconTintList(null)
        }
    }

    private fun getExtra() {
        val arguments = intent.extras
        if (arguments != null) {
            if (arguments.containsKey("userId")) {
                userId = arguments.getLong("userId")
                setUserId(userId!!)
            }
            if (arguments.containsKey("token")) {
                token = arguments.getString("token")
                setToken(token!!)
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }


    companion object {
        private var userId: Long? = null
        private var token: String? = null

        fun getUserId(): Long? {
            return userId
        }

        fun getToken(): String? {
            Log.d("token", token + " ")
            return token
        }
    }


    private fun setUserId(userIdValue: Long) {
        userId = userIdValue
    }

    private fun setToken(tokenValue: String) {
        token = tokenValue
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.invoices -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.Fragment,
                    InvoicesPage()
                ).commit()
                return true
            }

            R.id.profile -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.Fragment,
                    Profile()
                ).commit()
                return true
            }

            R.id.trucks -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.Fragment,
                    TrucksPage()
                ).commit()
                return true
            }
        }
        return false
    }
}