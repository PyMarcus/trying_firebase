package com.example.tryingfirebase.models

import java.text.SimpleDateFormat

data class UserModel(val name: String,
                     val age: Int,
                     val disability: Boolean,
                     val created: SimpleDateFormat,
                     val updated: SimpleDateFormat)
