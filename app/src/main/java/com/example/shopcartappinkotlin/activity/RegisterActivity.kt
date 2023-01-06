package com.example.shopcartappinkotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.databinding.ActivityRegisterBinding
import com.example.shopcartappinkotlin.helping_classes.Global
import com.example.shopcartappinkotlin.helping_classes.NetworkUtilities
import com.example.shopcartappinkotlin.model.RegisterModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var view: View
    private lateinit var dialog : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initializeFields()
        onClickListener()
    }

    private fun initializeFields() {
        binding = DataBindingUtil. setContentView(this,R.layout.activity_register)
        view = binding.root

    }


    private fun onClickListener() {

        binding.btnSignUp.setOnClickListener {

                if(NetworkUtilities.getConnectivityStatus(this)){
                    if (validateData()) {
                        showProgressDialog()
                       storeData() // store data in FireStore Database
                    }
                    else{
                        Global.showSnackBar(view,resources.getString(R.string.kindly_fill_up_all_fields))
                    }
                }
                else{
                    Global.showSnackBar(view,resources.getString(R.string.check_internet))
                }
            }

        binding.txtSignIn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        }

    private fun storeData() {

       val email = binding.edtEmail.text.toString().trim()
       val  password = binding.edtPass.text.toString().trim()
       val fullName = binding.edtFullName.text.toString().trim()
       val  mobileNumber = binding.edtMobile.text.toString().trim()
       val  address = binding.edtAddress.text.toString().trim()

   val userData = RegisterModel(fullName = fullName,
                                address = address,
                                mobileNumber = mobileNumber,
                                email = email,
                                password = password)

        //This will also work
      /*  val userData = hashMapOf<String,Any>()
        userData["fullName"] = fullName
        userData["address"] = address
        userData["mobileNumber"] = mobileNumber
        userData["email"] = email
        userData["password"] = password*/



        Firebase.firestore.collection("users").document(mobileNumber).set(userData)
            .addOnSuccessListener {
                dialog.dismiss()

                //store user data to display in  Address Activity for Confirmation
                Global.saveStringInSharedPref(this,"fullName",fullName)
                Global.saveStringInSharedPref(this,"mobileNumber",mobileNumber)
                Global.saveStringInSharedPref(this,"address",address)
                Global.saveStringInSharedPref(this,"email",email)
                Global.saveStringInSharedPref(this,"password",password)


                Global.showToast(this,resources.getString(R.string.user_registered_successfully))
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }.addOnFailureListener {
                Global.showToast(this,it.toString())
            }

    }

    private fun validateData() : Boolean{

        if(binding.edtFullName.text.toString().isEmpty()){
            binding.edtFullName.error = resources.getString(R.string.full_name_cannot_be_empty)
            return false
        }

        if(binding.edtAddress.text.toString().isEmpty()){
            binding.edtAddress.error = resources.getString(R.string.address_cannot_be_empty)
            return false
        }

        if(binding.edtMobile.text.toString().isEmpty()){
            binding.edtMobile.error = resources.getString(R.string.mobile_number_cannot_be_empty)
            return false
        }

        if(binding.edtEmail.text.toString().isEmpty()){
            binding.edtEmail.error = resources.getString(R.string.email_cannot_be_empty)
            return false
        }

        if(binding.edtPass.text.toString().isEmpty()){
            binding.edtPass.error = resources.getString(R.string.password_cannot_be_empty)
            return false
        }

        if(!Global.isValidEmail(binding.edtEmail.text.toString())){
            binding.edtEmail.error = resources.getString(R.string.enter_valid_email)
            return false
        }

        return true
    }

    private fun showProgressDialog(){
        dialog = AlertDialog.Builder(this)
           .setTitle("Loading..")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
            dialog.show()
    }




}