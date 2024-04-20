package com.example.tryingfirebase.listener

interface FirebaseListener<T> {
    fun response(data: T)

    fun error(message: String)
}