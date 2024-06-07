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
import okhttp3.ResponseBody
import retrofit2.Callback

interface ApiService {

    fun login(
        user: LoginRequest,
        callback: Callback<LoginResponse>
    )

    fun register(
        user: UserRequest,
        callback: Callback<Void>
    )

    fun getManager(
        token: String,
        userId: Long,
        callback: Callback<TruckManagerDTO>
    )

    fun updateManager(
        token: String,
        userId: Long,
        user: UpdateUserRequest,
        callback: Callback<Void>
    )

    fun getTrucks(
        token: String,
        userId: Long,
        callback: Callback<List<ShortTruckDTO>>
    )

    fun getTruck(
        token: String,
        userId: Long,
        truckId: Long,
        callback: Callback<TruckDTO>
    )

    fun updateTruck(
        token: String,
        userId: Long,
        truckId: Long,
        truck: UpdateTruckRequest,
        callback: Callback<Void>
    )

    fun getInvoices(
        token: String,
        userId: Long,
        callback: Callback<Page<List<ShortInvoiceDTO>>>
    )

    fun getInvoicesSearch(
        token: String,
        userId: Long,
        truckNumber: String,
        callback: Callback<Page<List<ShortInvoiceDTO>>>
    )

    fun getInvoice(
        token: String,
        userId: Long,
        invoiceId: Long,
        callback: Callback<InvoiceDTO>
    )

    fun signInvoice(
        token: String,
        userId: Long,
        invoiceId: Long,
        callback: Callback<Void>
    )

    fun getNotSignedByParkingManagerInvoices(
        token: String,
        userId: Long,
        truckNumber: String,
        callback: Callback<Page<List<ShortInvoiceDTO>>>
    )

    fun getNotSignedByTruckManagerInvoices(
        token: String,
        userId: Long,
        truckNumber: String,
        callback: Callback<Page<List<ShortInvoiceDTO>>>
    )

    fun getSignedInvoices(
        token: String,
        userId: Long,
        truckNumber: String,
        callback: Callback<Page<List<ShortInvoiceDTO>>>
    )

    fun downloadInvoice(
        token: String,
        userId: Long,
        invoiceId: Long,
        callback: Callback<ResponseBody>
    )
}