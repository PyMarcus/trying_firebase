package com.example.tryingfirebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tryingfirebase.listener.FirebaseListener
import com.example.tryingfirebase.repository.AuthenticationRepository

class RegisterViewModel(application: Application): AndroidViewModel(application) {
    private val repositoryAuth = AuthenticationRepository()
    private var _registered: MutableLiveData<Boolean> = MutableLiveData()
    private var _errorMessage: MutableLiveData<String> = MutableLiveData()
    var registered: LiveData<Boolean> = _registered
    var errorMessage: LiveData<String> = _errorMessage

    fun doRegister(name: String, email: String, password: String){
        repositoryAuth.register(email, password, object : FirebaseListener<Boolean>{
            override fun response(data: Boolean) {
                _registered.value = data
            }

            override fun error(message: String) {
                if(message.length > 5){
                    _errorMessage.value = message
                }else{
                    _errorMessage.value = ""
                }
            }
        })
    }
}