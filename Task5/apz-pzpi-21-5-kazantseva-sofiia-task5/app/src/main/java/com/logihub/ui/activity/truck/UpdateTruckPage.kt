package com.logihub.ui.activity.truck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.logihub.R
import com.logihub.model.request.UpdateTruckRequest
import com.logihub.model.response.TruckDTO
import com.logihub.service.ApiServiceImpl
import com.logihub.ui.activity.general.MainPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateTruckPage : AppCompatActivity() {

    private val activity: AppCompatActivity = this@UpdateTruckPage
    private val apiService = ApiServiceImpl()
    private lateinit var token: String
    private lateinit var userId: String
    private lateinit var truckId: String
    private lateinit var number: TextView
    private lateinit var width: EditText
    private lateinit var length: EditText
    private lateinit var height: EditText
    private lateinit var weight: EditText
    private lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_truck_page)
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
        updateButton = findViewById(R.id.updateTruck)

        updateButton.let { s ->
            s.setOnClickListener {
                updateTruck()
            }
        }

        displayData()
    }

    private fun updateTruck() {
        val widthValue = width.text.toString().toDouble()
        val lengthValue = length.text.toString().toDouble()
        val heightValue = height.text.toString().toDouble()
        val weightValue = weight.text.toString().toDouble()

        val truck = UpdateTruckRequest(widthValue, heightValue, lengthValue, weightValue)

        if (widthValue > 0 && lengthValue > 0 && heightValue > 0 && weightValue > 0) {
            apiService.updateTruck("Bearer " + token, userId.toLong(), truckId.toLong(), truck,
                object :
                    Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (!response.isSuccessful) {
                            Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG)
                                .show()
                        }
                        val intent = Intent(activity, TruckPage::class.java)
                        intent.putExtra("truckId", truckId)
                        startActivity(intent)
                        activity.finish()
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            )
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            val intent = Intent(this, TruckPage::class.java)
            intent.putExtra("truckId", truckId)
            startActivity(intent)
        }
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
        width.setText(data.width.toString())
        length.setText(data.length.toString())
        height.setText(data.height.toString())
        weight.setText(data.weight.toString())
    }
}