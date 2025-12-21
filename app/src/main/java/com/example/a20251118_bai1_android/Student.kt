package com.example.a20251118_bai1_android

import java.io.Serializable

data class Student(
    var studentId: String = "",
    var fullName: String = "",
    var phone: String = "",
    var address: String = ""
) : Serializable