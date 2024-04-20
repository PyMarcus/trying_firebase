package com.example.tryingfirebase.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tryingfirebase.databinding.ActivityRegisterBinding
import com.example.tryingfirebase.repository.SecurityPreferences
import com.example.tryingfirebase.viewmodels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        setContentView(binding.root)

        // remove status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        securityPreferences = SecurityPreferences(this)
        // events
        eventHandler()

        // observers
        observers()
    }

    override fun onClick(v: View) {
        when(v.id){
            binding.loginTxt.id -> startLoginActivity()
            binding.registerBtn.id -> register()
        }
    }

    private fun eventHandler(){
        binding.loginTxt.setOnClickListener(this)
        binding.registerBtn.setOnClickListener(this)
    }

    private fun startLoginActivity(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun register(){
        val name = binding.nameTxt.text.toString()
        val email = binding.emailTxt.text.toString()
        val password = binding.passwordTxt.text.toString()

        if(email.isNullOrEmpty()  || name.isNullOrEmpty()  || password.isNullOrEmpty()
            ){
                alertError(binding.root, "Preencha corretamente todos os campos")
        }else if(password.length < 6){
            alertError(binding.root, "A senha deve possuir, no mínimo, 6 caracteres!")
        }else if(!email.contains("@")){
            alertError(binding.root, "Digite um e-mail válido!")
        }
        else{
            viewModel.doRegister(name, email, password)
        }
    }

    private fun observers(){
        viewModel.registered.observe(this){
            registered -> if(registered){
                alertSuccess(binding.root, "Cadastro efetuado!")
                securityPreferences.store("name", binding.nameTxt.text.toString())
                binding.nameTxt.text.clear()
                binding.emailTxt.text.clear()
                binding.passwordTxt.text.clear()
            }else{
                alertError(binding.root, "Falha ao cadastrar usuário!")
            }
        }

        viewModel.errorMessage.observe(this){
            message -> if(message != ""){
                alertError(binding.root, message)
            }
        }
    }

    private fun alertError(view: View, message: String){
        val snackbar = Snackbar.make(view,
            message,
            Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }

    private fun alertSuccess(view: View, message: String){
        val snackbar = Snackbar.make(view,
            message,
            Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.BLUE)
        snackbar.show()
    }
}