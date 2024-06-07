package com.logihub.ui.activity.invoice

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.logihub.R
import com.logihub.model.response.InvoiceDTO
import com.logihub.service.ApiServiceImpl
import com.logihub.ui.activity.general.MainPage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InvoicePage : AppCompatActivity() {
    private val activity: AppCompatActivity = this@InvoicePage
    private val apiService = ApiServiceImpl()
    private lateinit var token: String
    private lateinit var userId: String
    private lateinit var invoiceId: String
    private lateinit var date: TextView
    private lateinit var truckNumber: TextView
    private lateinit var truckManagerEmail: TextView
    private lateinit var placeNumber: TextView
    private lateinit var parkingManagerEmail: TextView
    private lateinit var description: TextView
    private lateinit var price: TextView
    private lateinit var parkStatus: ImageView
    private lateinit var truckStatus: ImageView
    private lateinit var signButton: Button
    private lateinit var exportButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_invoice_page)
        init();
    }

    private fun init() {
        getExtra()
        userId = MainPage.getUserId().toString()
        token = MainPage.getToken().toString()

        date = findViewById(R.id.date)
        truckNumber = findViewById(R.id.truckNumber)
        truckManagerEmail = findViewById(R.id.truckManager)
        placeNumber = findViewById(R.id.placeNumber)
        parkingManagerEmail = findViewById(R.id.parkingManager)
        description = findViewById(R.id.description)

        price = findViewById(R.id.price)
        parkStatus = findViewById(R.id.parkStatus)
        truckStatus = findViewById(R.id.truckStatus)
        signButton = findViewById(R.id.signInvoice)
        exportButton = findViewById(R.id.exportInvoice)

        signButton.let { s ->
            s.setOnClickListener {
                signInvoice()
            }
        }

        exportButton.let { s ->
            s.setOnClickListener {
                exportInvoice()
            }
        }

        displayData()
    }

    private fun exportInvoice() {
        apiService.downloadInvoice("Bearer " + token, userId.toLong(), invoiceId.toLong(),
            object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            val file = saveFile(body)
                            if (file != null) {
                                Toast.makeText(activity, "Invoice downloaded!", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Failed to save the file.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } ?: run {
                            Toast.makeText(activity, "Response body is null.", Toast.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun saveFile(body: ResponseBody): File? {
        return try {
            val downloadDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadDir.exists()) {
                downloadDir.mkdirs()
            }
            val file = File(downloadDir, "invoice.pdf")
            Log.d("SaveFile", "Saving to: ${file.absolutePath}")
            var inputStream: InputStream? = null
            var outputStream: FileOutputStream? = null

            try {
                inputStream = body.byteStream()
                outputStream = FileOutputStream(file)
                val buffer = ByteArray(4096)
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    outputStream.write(buffer, 0, read)
                }
                outputStream.flush()
                file
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("SaveFile", "Error: ${e.message}")
            null
        }
    }

    private fun signInvoice() {
        apiService.signInvoice("Bearer " + token, userId.toLong(), invoiceId.toLong(),
            object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (!response.isSuccessful) {
                        Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG)
                            .show()
                    }
                    val intent = Intent(activity, MainPage::class.java)
                    intent.putExtra("userId", userId.toLong())
                    intent.putExtra("token", token)
                    startActivity(intent)
                    activity.finish()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        )
    }

    private fun getExtra() {
        val arguments = intent.extras
        if (arguments != null) {
            if (arguments.containsKey("invoiceId")) {
                invoiceId = arguments.getString("invoiceId").toString()
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, MainPage::class.java)
            intent.putExtra("userId", userId.toLong())
            intent.putExtra("token", token)
            startActivity(intent)
            activity.finish()
        }
    }

    private fun displayData() {
        apiService.getInvoice("Bearer " + token, userId.toLong(), invoiceId.toLong(),
            object :
                Callback<InvoiceDTO> {
                override fun onResponse(
                    call: Call<InvoiceDTO>,
                    response: Response<InvoiceDTO>
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

                override fun onFailure(call: Call<InvoiceDTO>, t: Throwable) {
                    Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        )
    }

    private fun fillData(data: InvoiceDTO) {
        val dateT = LocalDateTime.parse(data.creationDate)
        date.text = dateT.format(DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss"))
        truckNumber.text = data.truckNumber
        truckManagerEmail.text = data.truckManagerEmail
        placeNumber.text = data.placeNumber
        parkingManagerEmail.text = data.parkingManagerEmail
        description.text = data.description
        price.text = data.price.toString()

        if (data.signedByParkingManager) {
            parkStatus.setImageResource(R.drawable.check)
        } else {
            parkStatus.setImageResource(R.drawable.wait)
        }

        if (data.signedByTruckManager) {
            truckStatus.setImageResource(R.drawable.check);
        } else {
            truckStatus.setImageResource(R.drawable.wait);
        }

        if (data.signedByTruckManager && data.signedByParkingManager) {
            exportButton.visibility = VISIBLE
        } else {
            exportButton.visibility = GONE
        }

        if (data.signedByParkingManager && !data.signedByTruckManager) {
            signButton.visibility = VISIBLE
        } else {
            signButton.visibility = GONE
        }
    }
}