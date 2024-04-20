package com.example.tryingfirebase.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryingfirebase.adapter.UserAdapter
import com.example.tryingfirebase.databinding.ActivityContentBinding
import com.example.tryingfirebase.listener.UserListener
import com.example.tryingfirebase.models.UserModel
import com.example.tryingfirebase.repository.SecurityPreferences
import com.example.tryingfirebase.viewmodels.ContentViewModel
import java.util.Date

class ContentActivity : AppCompatActivity(), OnClickListener {
    lateinit var viewModel: ContentViewModel
    lateinit var securityPreferences: SecurityPreferences
    lateinit var binding: ActivityContentBinding
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
        }
    }

    private fun handleEvents(){
        binding.btnLogoff.setOnClickListener(this)
    }

    private fun observers(){
        val user = UserModel("Jose", 18, false, Date(2022, 10, 21)
            ,Date(2022, 10, 21))
        val user2 = UserModel("Jose 2", 118, true, Date(2023, 11, 11)
            ,Date(2021, 11, 31))
        val user3 = UserModel("Jose 2", 118, true, Date(2023, 11, 11)
            ,Date(2021, 11, 31))
        val user4 = UserModel("Jose 2", 118, true, Date(2023, 11, 11)
            ,Date(2021, 11, 31))
        userAdapter.updateUsers(arrayListOf(user, user2, user3, user4))
    }

    private fun logoff(){
        viewModel.doLogoff()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}