package com.example.tryingfirebase.repository

import com.example.tryingfirebase.listener.FirebaseListener
import com.example.tryingfirebase.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UserDAO {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun add(user: UserModel, firebaseListener: FirebaseListener<Boolean>){

        val useradd = hashMapOf("name" to user.name, "age" to user.age, "have_disability" to user.disability)

        db.collection("users_collection").document(user.name).
                set(useradd).addOnCompleteListener{
                    firebaseListener.response(true)
                }.addOnFailureListener {
                    firebaseListener.error("Falha ao salvar usuário!")
                }
    }

    fun read(firebaseListener: FirebaseListener<List<UserModel>>) {
        val userList = mutableListOf<UserModel>()

        db.collection("users_collection").get().addOnSuccessListener {result->
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))
            for (document in result) {
                val name = document.getString("name") ?: ""
                val age = document.getLong("age") ?: 0
                val disability = document.getBoolean("have_disability") ?: false
                val user = UserModel(name, age.toInt(), disability, dateFormat, dateFormat)
                println("USER ${user.name}")
                userList.add(user)
            }
            firebaseListener.response(userList)
        }
    }

}