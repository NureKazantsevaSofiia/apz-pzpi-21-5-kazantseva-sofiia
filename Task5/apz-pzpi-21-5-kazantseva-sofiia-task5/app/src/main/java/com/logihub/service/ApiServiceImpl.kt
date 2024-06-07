package com.logihub.service

import com.logihub.model.request.LoginRequest
import com.logihub.model.request.UpdateTruckRequest
import com.logihub.model.request.UpdateUserRequest
import com.logihub.model.request.UserRequest
import com.logihub.model.response.InvoiceDTO
import com.logihub.model.response.LoginResponse
import com.logihub.model.response.Page
import com.logihub.model.response.ShortInvoiceDTO
import com.logihub.model.response.ShortTruckDTO
import com.logihub.model.response.TruckDTO
import com.logihub.model.response.TruckManagerDTO
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceImpl : ApiService {

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://7ac1-46-98-183-185.ngrok-free.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val service = retrofit.create(Api::class.java)
    override fun login(user: LoginRequest, callback: Callback<LoginResponse>) {
        val credentials = user.username + ":" + user.password
        val auth = "Basic " + android.util.Base64.encodeToString(
            credentials.toByteArray(),
            android.util.Base64.NO_WRAP
        )
        service.login(auth).enqueue(callback)
    }

    override fun register(user: UserRequest, callback: Callback<Void>) {
        service.register(user).enqueue(callback)
    }

    override fun getManager(token: String, userId: Long, callback: Callback<TruckManagerDTO>) {
        service.getManager(token, userId).enqueue(callback)
    }

    override fun updateManager(
        token: String,
        userId: Long,
        user: UpdateUserRequest,
        callback: Callback<Void>
    ) {
        service.updateManager(token, userId, user).enqueue(callback)
    }

    override fun getTrucks(token: String, userId: Long, callback: Callback<List<ShortTruckDTO>>) {
        service.getTrucks(token, userId).enqueue(callback)
    }

    override fun getTruck(
        token: String,
        userId: Long,
        truckId: Long,
        callback: Callback<TruckDTO>
    ) {
        service.getTruck(token, userId, truckId).enqueue(callback)
    }

    override fun updateTruck(
        token: String,
        userId: Long,
        truckId: Long,
        truck: UpdateTruckRequest,
        callback: Callback<Void>
    ) {
        service.updateTruck(token, userId, truckId, truck).enqueue(callback)
    }

    override fun getInvoices(
        token: String,
        userId: Long,
        callback: Callback<Page<List<ShortInvoiceDTO>>>
    ) {
        service.getInvoices(token, userId).enqueue(callback)
    }

    override fun getInvoicesSearch(
        token: String,
        userId: Long,
        truckNumber: String,
        callback: Callback<Page<List<ShortInvoiceDTO>>>
    ) {
        service.getInvoicesSearch(token, userId, truckNumber).enqueue(callback)
    }

    override fun getInvoice(
        token: String,
        userId: Long,
        invoiceId: Long,
        callback: Callback<InvoiceDTO>
    ) {
        service.getInvoice(token, userId, invoiceId).enqueue(callback)
    }

    override fun signInvoice(
        token: String,
        userId: Long,
        invoiceId: Long,
        callback: Callback<Void>
    ) {
        service.signInvoice(token, userId, invoiceId).enqueue(callback)
    }

    override fun getNotSignedByParkingManagerInvoices(
        token: String,
        userId: Long,
        truckNumber: String,
        callback: Callback<Page<List<ShortInvoiceDTO>>>
    ) {
        service.getNotSignedByParkingManagerInvoices(token, userId, truckNumber).enqueue(callback)
    }

    override fun getNotSignedByTruckManagerInvoices(
        token: String,
        userId: Long,
        truckNumber: String,
        callback: Callback<Page<List<ShortInvoiceDTO>>>
    ) {
        service.getNotSignedByTruckManagerInvoices(token, userId, truckNumber).enqueue(callback)
    }

    override fun getSignedInvoices(
        token: String,
        userId: Long,
        truckNumber: String,
        callback: Callback<Page<List<ShortInvoiceDTO>>>
    ) {
        service.getSignedInvoices(token, userId, truckNumber).enqueue(callback)
    }

    override fun downloadInvoice(
        token: String,
        userId: Long,
        invoiceId: Long,
        callback: Callback<ResponseBody>
    ) {
        service.downloadInvoice(token, userId, invoiceId).enqueue(callback)
    }
}