package com.weiyou.psimanagement

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.weiyou.psimanagement.databinding.MainActivityBinding
import com.weiyou.psimanagement.ui.main.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
//        var testFragment = TestFragment()
        val purchaseFragment = PurchaseFragment()
        val salesFragment = SalesFragment()
        val inventoryFragment = InventoryFragment()
        val moreFragment = MoreFragment()
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
        navView.itemIconTintList = null


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
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.fragmentContainerView, MainActivity.moreFragment).commit()
//                    pickDateRange()
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
