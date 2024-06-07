package com.logihub.model.response

data class Page<T>(

    var content: T,

    var pageNumber: Int,

    var pageSize: Int
)