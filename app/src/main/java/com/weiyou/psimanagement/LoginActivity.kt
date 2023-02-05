package com.weiyou.psimanagement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weiyou.psimanagement.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)

        super.onCreate(savedInstanceState)

        if (getSupportActionBar() != null){
            getSupportActionBar()?.hide();
        }

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