package com.example.tryingfirebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tryingfirebase.repository.AuthenticationRepository
import com.example.tryingfirebase.repository.SecurityPreferences

class ContentViewModel(application: Application): AndroidViewModel(application) {
    private val authenticationRepository = AuthenticationRepository()
    private val securityPreferences = SecurityPreferences(application.applicationContext)

    fun doLogoff(){
        securityPreferences.remove("name")
        authenticationRepository.logout()
    }

}