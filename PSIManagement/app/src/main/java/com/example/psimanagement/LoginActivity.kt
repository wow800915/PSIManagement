package com.example.psimanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.psimanagement.databinding.ActivityLoginBinding
import com.example.psimanagement.databinding.MainActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.button.setOnClickListener {
//            Handler().postDelayed({
//                startActivity(Intent(this, MainActivity::class.java))
//            }, 100)
//        }
//       延遲解法： https://segmentfault.com/q/1010000007605188
        binding.button.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
        }
    }
}