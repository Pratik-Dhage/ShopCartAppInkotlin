package com.example.shopcartappinkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.shopcartappinkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var view : View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initializeFields()
        fragmentSetUp()
        onClickListeners()
    }

    private fun fragmentSetUp() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_nav)
        binding.bottomNavBar.setupWithNavController(popupMenu.menu,navController)

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
    }

    private fun onClickListeners() {
       // TODO("Not yet implemented")
    }

    private fun initializeFields() {
       binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
       view = binding.root

    }
}