package com.logihub.model.request

data class UserRequest(

    var firstName: String,

    var lastName: String,

    var email: String,

    var password: String,

    var companyId: Long,
)