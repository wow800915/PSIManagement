package com.example.psimanagement

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getSupportActionBar() != null){
            getSupportActionBar()?.hide();
        }
        setContentView(R.layout.activity_splash)



        Handler().postDelayed({
//            startActivity(Intent(this, MainActivity::class.java))
            startActivity(Intent(this, LoginActivity::class.java))
        }, 1000)

    }
}