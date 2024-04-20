package com.example.tryingfirebase.models

import java.util.Date

data class UserModel(val name: String,
                     val age: Int,
                     val disability: Boolean,
                     val created: Date,
                     val updated: Date)
