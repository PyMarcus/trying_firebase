package com.example.tryingfirebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tryingfirebase.listener.FirebaseListener
import com.example.tryingfirebase.repository.AuthenticationRepository

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val authenticationRepository = AuthenticationRepository()
    private var _login: MutableLiveData<Boolean> = MutableLiveData()
    private var _errorMessage: MutableLiveData<String> = MutableLiveData()
    private var _isLogged: MutableLiveData<Boolean> = MutableLiveData()
    var login: LiveData<Boolean> = _login
    var errorMessage: LiveData<String> = _errorMessage
    var loged: LiveData<Boolean> = _isLogged

    fun doLogin(email: String, password: String){
        authenticationRepository.login(email, password, object : FirebaseListener<Boolean>{
            override fun response(data: Boolean) {
                _login.value = data
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

    fun logged(){
        _isLogged.value = authenticationRepository.isLogged()
    }

}