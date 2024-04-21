package com.example.tryingfirebase.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryingfirebase.adapter.UserAdapter
import com.example.tryingfirebase.databinding.ActivityContentBinding
import com.example.tryingfirebase.listener.UserListener
import com.example.tryingfirebase.repository.SecurityPreferences
import com.example.tryingfirebase.viewmodels.ContentViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ContentActivity : AppCompatActivity(), OnClickListener {
    private lateinit var viewModel: ContentViewModel
    private lateinit var securityPreferences: SecurityPreferences
    private lateinit var binding: ActivityContentBinding
    private val userAdapter: UserAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContentBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.recycle.layoutManager = LinearLayoutManager(this)
        binding.recycle.adapter = userAdapter

        securityPreferences = SecurityPreferences(this)
        viewModel = ViewModelProvider(this)[ContentViewModel::class.java]

        handleEvents()

        viewModel.listAll()

        observers()


        // listener
        val listener = object : UserListener{
            override fun onListClick(id: Int) {
                TODO("Not yet implemented")
            }

            override fun onDeleteClick(id: Int) {
                TODO("Not yet implemented")
            }

        }

        userAdapter.attachListener(listener)

    }

    override fun onClick(v: View) {
        when(v.id){
            binding.btnLogoff.id -> logoff()
            binding.btnAddUser.id -> add()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.listAll()
    }

    private fun handleEvents(){
        binding.btnLogoff.setOnClickListener(this)
        binding.btnAddUser.setOnClickListener(this)
    }

    private fun observers(){
        viewModel.usersData.observe(this){
            data -> userAdapter.updateUsers(data)
        }

        viewModel.saveUser.observe(this){
            it -> if(it){
                alert(binding.root, "Usuário salvo!")
            }
        }

        viewModel.message.observe(this){
                message -> if(message != ""){
                    alert(binding.root, message)
            }
        }
    }

    private fun logoff(){
        viewModel.doLogoff()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun add(){
        val name = binding.name.text.toString()
        val a = binding.age.text.toString()
        var age = 0
        if(a != ""){
            age = a.toInt()
        }
        val debility = binding.debility.isChecked

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))
        val dateTime = dateFormat.format(calendar.time)
        val created = dateFormat
        val updated = dateFormat
        if(!name.isNullOrEmpty() && age != 0){
            viewModel.addUser(name, age, debility, created, updated)
        }else{
            alert(binding.root, "Preencha os campos!")
        }
    }

    private fun alert(view: View, message: String){
        val snackbar = Snackbar.make(view,
            message,
            Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.YELLOW)
        snackbar.show()
    }

}