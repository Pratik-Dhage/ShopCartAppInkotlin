package com.example.shopcartappinkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.shopcartappinkotlin.activity.LoginActivity
import com.example.shopcartappinkotlin.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var view : View
    private var i = 0 // for fragment changes and onBackPressed()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCurrentUserLoggedIn()
        initializeFields()
        fragmentSetUp()
       // onClickListeners()
    }

    private fun isCurrentUserLoggedIn() {
        if(FirebaseAuth.getInstance().currentUser==null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    private fun initializeFields() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        view = binding.root

    }


    private fun fragmentSetUp() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_nav)
        binding.bottomNavBar.setupWithNavController(popupMenu.menu,navController)

        //for changing Title AFTER changing fragments
        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                title = when(destination.id){
                    R.id.cartFragment-> resources.getString(R.string.cart)
                    R.id.moreFragment-> resources.getString(R.string.profile)

                    else ->  resources.getString(R.string.shopcart)
                }
            }

        })

        //for changing fragments
        binding.bottomNavBar.onItemSelected = {

            when(it){
                0 -> { i = 0
                    navController.navigate(R.id.homeFragment)}

                1 -> { i = 1 }
                   // navController.navigate(R.id.cartFragment)}

                2 -> { i = 2 }
                   // navController.navigate(R.id.moreFragment)}
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(i==0){
            finish()
        }
    }


}