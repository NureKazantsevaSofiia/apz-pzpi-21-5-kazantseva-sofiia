package com.logihub.model.response

data class ShortInvoiceDTO(

    var id: Long,

    var type: String,

    var truckNumber: String,

    var placeNumber: String,

    var truckManagerEmail: String,

    var parkingManagerEmail: String,

    var creationDate: String,

    var price: Double
)