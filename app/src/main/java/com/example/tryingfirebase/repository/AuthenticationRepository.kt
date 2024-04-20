package com.example.tryingfirebase.repository

import com.example.tryingfirebase.listener.FirebaseListener
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository {
    private val auth = FirebaseAuth.getInstance()

    fun register(email: String, password: String, firebaseListener: FirebaseListener<Boolean>){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            response -> if(response.isSuccessful){
                firebaseListener.response(true)
            }else{
                firebaseListener.response(false)
            }
        }.addOnFailureListener{exception ->
            val message = when(exception){
                is FirebaseNetworkException -> "Sem conexão com a internet"
                else -> "Falha!"
            }
            firebaseListener.error(message)

        }
    }

    fun login(email: String, password: String, firebaseListener: FirebaseListener<Boolean>){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            result -> if(result.isSuccessful){
                firebaseListener.response(true)
            }else{
                firebaseListener.response(false)
            }
        }.addOnFailureListener {exception ->
            val message = when(exception){
                is FirebaseNetworkException -> "Sem conexão com a internet"
                else -> "Falha ao efetuar login"
            }
            firebaseListener.error(message)
        }
    }

    fun isLogged(): Boolean{
        return auth.currentUser != null
    }

    fun logout(){
        auth.signOut()
    }
}