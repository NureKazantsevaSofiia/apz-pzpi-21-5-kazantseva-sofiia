package com.logihub.model.response

data class TruckManagerDTO(

    var id: Long,

    var firstName: String,

    var lastName: String,

    var email: String,

    var avatar: String,

    var company: CompanyDTO
)