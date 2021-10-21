package com.example.psimanagement

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.psimanagement.databinding.MainActivityBinding
import com.example.psimanagement.ui.main.InventoryFragment
import com.example.psimanagement.ui.main.ProfileFragment
import com.example.psimanagement.ui.main.PurchaseFragment
import com.example.psimanagement.ui.main.SalesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        val purchaseFragment = PurchaseFragment()
        val salesFragment = SalesFragment()
        val profileFragemnt = ProfileFragment()
        val inventoryFragment = InventoryFragment()
    }

    private lateinit var navController: NavController

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.main_activity)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, inventoryFragment)
                .commit()
        navView.selectedItemId = R.id.navigation_inventory
        navView.setOnNavigationItemSelectedListener(listener)
    }

    private var listener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_purchase -> {
//                    title = "進貨"
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.fragmentContainerView, MainActivity.purchaseFragment).commit()

                }                //之後按鈕會連結fragment
                R.id.navigation_sales -> {
//                    title = "銷售"
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView, MainActivity.salesFragment).commit()
                }
                R.id.navigation_inventory -> {
//                    title = "存貨"
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.fragmentContainerView, MainActivity.inventoryFragment).commit()
                }
                R.id.navigation_scrap -> {
//                    val t = supportFragmentManager.beginTransaction()
//                    t.replace(R.id.fragmentContainerView, MainActivity.inventoryFragment).commit()
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
