package com.example.shopcartappinkotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.databinding.ActivityAddressBinding
import com.example.shopcartappinkotlin.helping_classes.Global

class AddressActivity : AppCompatActivity() {

private lateinit var binding : ActivityAddressBinding
private lateinit var view : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


         initializeFields()
         getUserData()
         onClickListener()
    }

    private fun getUserData() {

        // We will get user data coming from either Register/Login
        binding.txtFullName.text = Global.getStringFromSharedPref(this, "fullName")
        binding.txtMobile.text = Global.getStringFromSharedPref(this, "mobileNumber")
        binding.edtAddress.setText( Global.getStringFromSharedPref(this, "address"))

    }

    private fun onClickListener() {
       binding.btnProceed.setOnClickListener {

           if(binding.edtAddress.text.toString().isEmpty()){
               binding.edtAddress.error = resources.getString(R.string.address_cannot_be_empty)
           }
            else{
            // val address = binding.edtAddress.text.toString().trim()
               startActivity(Intent(this,CheckOutActivity::class.java))
            }
       }
    }

    private fun initializeFields() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_address)
        view = binding.root
    }

}