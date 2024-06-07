package com.logihub.model.response

data class InvoiceDTO(

    var id: Long,

    var type: String,

    var truckNumber: String,

    var placeNumber: String,

    var truckManagerEmail: String,

    var parkingManagerEmail: String,

    var creationDate: String,

    var description: String,

    var price: Double,

    var signedByTruckManagerDate: String,

    var signedByParkingManagerDate: String,

    var signedByTruckManager: Boolean,

    var signedByParkingManager: Boolean
)