package com.example.psimanagement

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.psimanagement.databinding.MainActivityBinding
import com.example.psimanagement.ui.main.LoginFragment
import com.example.psimanagement.ui.main.MainFragment
import com.example.psimanagement.ui.main.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.contracts.*

class MainActivity : AppCompatActivity(){

    companion object{
        val loginFragment = LoginFragment()
        val mainFragment = MainFragment()
        val profileFragemnt = ProfileFragment()
    }

    private lateinit var navController: NavController

    private lateinit var binding : MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.main_activity)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, mainFragment)
                .commit()
        navView.selectedItemId = R.id.navigation_home
        navView.setOnNavigationItemSelectedListener(listener)
    }
    private var listener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_home-> {
                    Log.d("IANIAN","navigation_home");
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView, MainActivity.mainFragment).commit()
                }                //之後按鈕會連結fragment
                R.id.navigation_dashboard -> {
                    Log.d("IANIAN","navigation_dashboard");
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.fragmentContainerView, MainActivity.loginFragment).commit()
                }
                R.id.navigation_test -> {
                    Log.d("IANIAN","navigation_test");
                }
                R.id.navigation_notifications -> {
                    Log.d("IANIAN","navigation_notifications");
                }
            }
            return true
        }
    }

//        navController = findNavController(R.id.fragmentContainerView)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, LoginFragment.newInstance())
//                .commitNow()
//        }


//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        navController = navHostFragment.navController

        // Set up the action bar for use with the NavController
//        setupActionBarWithNavController(navController)
//    }

//    override fun onSupportNavigateUp() =
//        findNavController(this, R.id.fragmentContainerView).navigateUp()
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
}
