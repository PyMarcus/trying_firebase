package com.example.tryingfirebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tryingfirebase.listener.FirebaseListener
import com.example.tryingfirebase.models.UserModel
import com.example.tryingfirebase.repository.AuthenticationRepository
import com.example.tryingfirebase.repository.SecurityPreferences
import com.example.tryingfirebase.repository.UserDAO
import java.text.SimpleDateFormat

class ContentViewModel(application: Application): AndroidViewModel(application) {
    private val authenticationRepository = AuthenticationRepository()
    private val securityPreferences = SecurityPreferences(application.applicationContext)

    private var _saveUser: MutableLiveData<Boolean> = MutableLiveData()
    var saveUser: LiveData<Boolean> = _saveUser

    private var _usersData: MutableLiveData<List<UserModel>> = MutableLiveData()
    var usersData: LiveData<List<UserModel>> = _usersData
    private val userDAO = UserDAO()

    private var _message: MutableLiveData<String> = MutableLiveData()
    var message: LiveData<String> = _message

    fun addUser(name: String, age: Int, debility: Boolean){
        val user = UserModel(name, age, debility)
        userDAO.add(user, object : FirebaseListener<Boolean>{
            override fun response(data: Boolean) {
                if(data){
                    _saveUser.value = data
                    listAll()
                }
                else _saveUser.value = false
            }

            override fun error(message: String) {
                if(message.length > 5){
                    _message.value = message
                }else{
                    _message.value = ""
                }
            }

        })
    }

    fun listAll(){
        userDAO.read(object : FirebaseListener<List<UserModel>>{
            override fun response(data: List<UserModel>) {
                _usersData.value = data
            }

            override fun error(message: String) {
                TODO("Not yet implemented")
            }

        })

    }

    fun doLogoff(){
        securityPreferences.remove("name")
        authenticationRepository.logout()
    }

}