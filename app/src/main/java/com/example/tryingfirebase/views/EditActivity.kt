package com.example.tryingfirebase.views

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tryingfirebase.databinding.ActivityEditBinding
import com.example.tryingfirebase.models.UserModel
import com.example.tryingfirebase.viewmodels.EditViewModel

class EditActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityEditBinding
    private lateinit var viewModel: EditViewModel
    private lateinit var userM: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setData()

        viewModel = ViewModelProvider(this)[EditViewModel::class.java]

        handleEvents()

    }

    override fun onClick(v: View) {
        when(v.id){
            binding.btnEditUser.id -> edit()
        }
    }

    private fun handleEvents(){
        binding.btnEditUser.setOnClickListener(this)
    }

    private fun edit(){
        viewModel.edit(userM)
    }

    private fun setData(){
        var name = intent.getStringExtra("name_user")
        val age = intent.getIntExtra("age_user", 0)
        val di = intent.getBooleanExtra("disability_user", false)
        if(name == null) name = ""
        userM = UserModel(name, age, di)
        binding.name.setText(name)
        binding.age.setText("${age} anos")
        binding.debility.isChecked = di

    }
}