package com.logihub.service

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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @Headers("Content-Type: application/json")
    @POST("/api/login")
    fun login(@Header("Authorization") auth: String): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/api/truck-manager/register")
    fun register(@Body registrationData: UserRequest): Call<Void>

    @GET("/api/truck-manager/{user-id}")
    fun getManager(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long
    ): Call<TruckManagerDTO>

    @PATCH("/api/truck-manager/{user-id}")
    @Headers("Content-Type: application/json")
    fun updateManager(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long,
        @Body user: UpdateUserRequest
    ): Call<Void>

    @GET("/api/truck-manager/{user-id}/trucks/all")
    fun getTrucks(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long
    ): Call<List<ShortTruckDTO>>

    @GET("/api/truck-manager/{user-id}/trucks/{truck-id}")
    fun getTruck(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long,
        @Path("truck-id") truckId: Long
    ): Call<TruckDTO>

    @PATCH("/api/truck-manager/{user-id}/trucks/{truck-id}")
    fun updateTruck(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long,
        @Path("truck-id") truckId: Long,
        @Body truck: UpdateTruckRequest
    ): Call<Void>

    @GET("/api/truck-manager/{user-id}/invoices")
    fun getInvoices(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long
    ): Call<Page<List<ShortInvoiceDTO>>>

    @GET("/api/truck-manager/{user-id}/invoices/truck-number/{truck-number}")
    fun getInvoicesSearch(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long,
        @Path("truck-number") truckNumber: String
    ): Call<Page<List<ShortInvoiceDTO>>>

    @GET("/api/truck-manager/{user-id}/invoices/{invoice-id}")
    fun getInvoice(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long,
        @Path("invoice-id") invoiceId: Long
    ): Call<InvoiceDTO>

    @PATCH("/api/truck-manager/{user-id}/invoices/{invoice-id}")
    fun signInvoice(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long,
        @Path("invoice-id") invoiceId: Long
    ): Call<Void>

    @GET("/api/truck-manager/{user-id}/invoices/not-signed-by-parking-manager")
    fun getNotSignedByParkingManagerInvoices(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long,
        @Query("truckNumber") truckNumber: String
    ): Call<Page<List<ShortInvoiceDTO>>>

    @GET("/api/truck-manager/{user-id}/invoices/not-signed-by-truck-manager")
    fun getNotSignedByTruckManagerInvoices(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long,
        @Query("truckNumber") truckNumber: String
    ): Call<Page<List<ShortInvoiceDTO>>>

    @GET("/api/truck-manager/{user-id}/invoices/signed")
    fun getSignedInvoices(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long,
        @Query("truckNumber") truckNumber: String
    ): Call<Page<List<ShortInvoiceDTO>>>

    @GET("/api/truck-managers/{user-id}/invoices/{invoice-id}/export")
    fun downloadInvoice(
        @Header("Authorization") token: String,
        @Path("user-id") userId: Long,
        @Path("invoice-id") invoiceId: Long
    ): Call<ResponseBody>
}