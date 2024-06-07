package com.logihub.model.response

data class LoginResponse(
    var id: Long,

    var token: String,

    var name: String,

    var surname: String,

    var role: String,

    var avatar: String,
)