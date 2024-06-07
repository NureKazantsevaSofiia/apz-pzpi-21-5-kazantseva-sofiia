package com.logihub.ui.activity.truck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.logihub.R
import com.logihub.model.response.TruckDTO
import com.logihub.service.ApiServiceImpl
import com.logihub.ui.activity.general.MainPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TruckPage : AppCompatActivity() {

    private val activity: AppCompatActivity = this@TruckPage
    private val apiService = ApiServiceImpl()
    private lateinit var token: String
    private lateinit var userId: String
    private lateinit var truckId: String
    private lateinit var number: TextView
    private lateinit var width: TextView
    private lateinit var length: TextView
    private lateinit var height: TextView
    private lateinit var weight: TextView
    private lateinit var editButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_truck_page)
        init();
    }

    private fun init() {
        getExtra()
        userId = MainPage.getUserId().toString()
        token = MainPage.getToken().toString()

        number = findViewById(R.id.number)
        width = findViewById(R.id.width)
        length = findViewById(R.id.length)
        height = findViewById(R.id.height)
        weight = findViewById(R.id.weight)
        editButton = findViewById(R.id.button)

        editButton.let { s ->
            s.setOnClickListener {
                val intent = Intent(activity, UpdateTruckPage::class.java)
                intent.putExtra("truckId", truckId)
                startActivity(intent)
            }
        }

        displayData()
    }

    private fun getExtra() {
        val arguments = intent.extras
        if (arguments != null) {
            if (arguments.containsKey("truckId")) {
                truckId = arguments.getString("truckId").toString()
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            val intent = Intent(this, TrucksPage::class.java)
            startActivity(intent)
        }
    }

    private fun displayData() {
        apiService.getTruck("Bearer " + token, userId.toLong(), truckId.toLong(),
            object :
                Callback<TruckDTO> {
                override fun onResponse(
                    call: Call<TruckDTO>,
                    response: Response<TruckDTO>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            fillData(data)
                        } else {
                            Toast.makeText(
                                activity,
                                "Something went wrong!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<TruckDTO>, t: Throwable) {
                    Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        )
    }

    private fun fillData(data: TruckDTO) {
        number.text = data.number
        width.text = width.getText().toString() + ": " + data.width
        length.text = length.getText().toString() + ": " + data.length
        height.text = height.getText().toString() + ": " + data.height
        weight.text = weight.getText().toString() + ": " + data.weight
    }
}