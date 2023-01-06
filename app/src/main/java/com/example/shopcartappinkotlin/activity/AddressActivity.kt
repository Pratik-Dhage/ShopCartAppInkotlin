package com.example.shopcartappinkotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.databinding.ActivityAddressBinding
import com.example.shopcartappinkotlin.helping_classes.Global
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

        val mobileNumber =  Global.getStringFromSharedPref(this, "mobileNumber")

        // We will get user data coming from either Register/Login
       /* binding.txtFullName.text = Global.getStringFromSharedPref(this, "fullName")
        binding.txtMobile.text = Global.getStringFromSharedPref(this, "mobileNumber")
        binding.edtAddress.setText( Global.getStringFromSharedPref(this, "address"))*/

        Firebase.firestore.collection("users").document(mobileNumber)
            .get().addOnSuccessListener {
                binding.txtFullName.text = it.getString("fullName").toString()
                binding.edtAddress.setText(it.getString("address").toString())
                binding.txtMobile.text = it.getString("mobileNumber").toString()
                binding.txtEmail.text = it.getString("email").toString()
                binding.txtPassword.text = it.getString("password").toString()
            }

    }

    private fun onClickListener() {

        val fullName = binding.txtFullName.text.toString()
        val mobileNumber = binding.txtMobile.text.toString()
        val address = binding.edtAddress.text.toString()
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        val updateData = hashMapOf<String,Any>()
        updateData ["address"] = address
        updateData ["fullName"] = fullName
        updateData ["mobileNumber"] = mobileNumber
        updateData ["email"] = email
        updateData ["password"] = password

        binding.btnUpdateProfile.setOnClickListener{

            //update address
            Firebase.firestore.collection("users")
                .document(mobileNumber).set(updateData).addOnSuccessListener {

                    //also store updated data in Shared Preference
                    Global.saveStringInSharedPref(this,"address",address)

                    Global.showSnackBar(view,resources.getString(R.string.profile_updated_successfully))

                }.addOnFailureListener {
                    Global.showSnackBar(view,it.localizedMessage!!)
                }

        }

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