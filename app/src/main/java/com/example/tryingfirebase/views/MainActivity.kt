package com.example.tryingfirebase.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tryingfirebase.databinding.ActivityMainBinding
import com.example.tryingfirebase.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)

        // remove status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)


        // events
        eventHandler()

        // observers
        observers()

    }

    override fun onStart() {
        super.onStart()
        viewModel.logged()
    }

    override fun onClick(v: View) {
        when(v.id){
            binding.registerTxt.id -> startRegisterActivity()

            binding.loginBtn.id -> login()
        }
    }

    private fun eventHandler(){
        binding.registerTxt.setOnClickListener(this)
        binding.loginBtn.setOnClickListener(this)
    }

    private fun startRegisterActivity(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun login(){
        val email = binding.emailTxt.text.toString()
        val password = binding.passwordTxt.text.toString()

        if(email.isNullOrEmpty()  || password.isNullOrEmpty()
            || password.length < 6){
            alertError(binding.root)
        }else{
            viewModel.doLogin(email, password)
        }
    }

    private fun observers(){
        viewModel.loged.observe(this){
            logged -> if(logged){
                navigateToMainScreen()
            }
        }
        viewModel.login.observe(this){success->
            if(success){
                navigateToMainScreen()
            }else{
                alertErrorLogin(binding.root, "Falha ao realizar login! " +
                        "Caso não tenha uma conta, cadastre-se!")
            }
        }

        viewModel.errorMessage.observe(this){
            message -> if(message != ""){
                alertErrorLogin(binding.root, message)
            }
        }
    }

    private fun alertError(view: View){
        val snackbar = Snackbar.make(view,
            "Preencha corretamente todos os campos!",
            Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }

    private fun alertErrorLogin(view: View, message: String){
        val snackbar = Snackbar.make(view,
            message,
            Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }

    private fun navigateToMainScreen(){
        startActivity(Intent(this, ContentActivity::class.java))
    }
}