package com.example.tryingfirebase.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tryingfirebase.databinding.ActivityContentBinding
import com.example.tryingfirebase.repository.SecurityPreferences
import com.example.tryingfirebase.viewmodels.ContentViewModel

class ContentActivity : AppCompatActivity(), OnClickListener {
    lateinit var viewModel: ContentViewModel
    lateinit var securityPreferences: SecurityPreferences
    lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContentBinding.inflate(layoutInflater)
        securityPreferences = SecurityPreferences(this)
        viewModel = ViewModelProvider(this)[ContentViewModel::class.java]
        setContentView(binding.root)

        handleEvents()

    }

    override fun onClick(v: View) {
        when(v.id){
            binding.btnLogoff.id -> logoff()
        }
    }

    private fun handleEvents(){
        binding.btnLogoff.setOnClickListener(this)
    }

    private fun logoff(){
        viewModel.doLogoff()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}