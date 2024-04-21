package com.example.tryingfirebase.views

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tryingfirebase.databinding.ActivityEditBinding
import com.example.tryingfirebase.models.UserEditModel
import com.example.tryingfirebase.models.UserModel
import com.example.tryingfirebase.viewmodels.EditViewModel

class EditActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityEditBinding
    private lateinit var viewModel: EditViewModel
    private lateinit var userM: UserEditModel

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
        println("XNAMEX ")

        var name = binding.name.text.toString()
        val age = binding.age.text
        val di = binding.debility.isChecked
        userM.name = name
        userM.age = age.toString().toInt()
        userM.disability = di
        println("XNAME ${userM.age} ${di}")
        viewModel.edit(userM)
        finish()
    }

    private fun setData(){
        var name = intent.getStringExtra("name_user")
        val age = intent.getIntExtra("age_user", 0)
        val di = intent.getBooleanExtra("disability_user", false)
        if(name == null) name = ""
        userM = UserEditModel(name, age, di, name)
        binding.name.setText(name)
        binding.age.setText("${age}")
        binding.debility.isChecked = di

    }
}