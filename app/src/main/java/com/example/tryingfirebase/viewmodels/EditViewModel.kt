package com.example.tryingfirebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tryingfirebase.models.UserEditModel
import com.example.tryingfirebase.models.UserModel
import com.example.tryingfirebase.repository.UserDAO

class EditViewModel(application: Application): AndroidViewModel(application) {

    private val userDAO = UserDAO()

    fun edit(user: UserEditModel){
        userDAO.edit(user)
    }
}