package com.example.shopcartappinkotlin.activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.databinding.ActivityLoginBinding
import com.example.shopcartappinkotlin.helping_classes.Global
import com.example.shopcartappinkotlin.helping_classes.NetworkUtilities
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var view: View
    private lateinit var dialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeFields()
        onClickListener()
    }

    private fun onClickListener() {

        val mobile = binding.edtMobile.text.toString().trim()
       // val password = binding.edtPass.text.toString().trim()

       binding.btnSignIn.setOnClickListener {

           if(NetworkUtilities.getConnectivityStatus(this)){
               if (validateData()) {
                   authenticateWithOTP(mobile)

               }
               else{
                   Global.showSnackBar(view,resources.getString(R.string.kindly_fill_up_all_fields))
               }
           }
           else{
               Global.showSnackBar(view,resources.getString(R.string.check_internet))
           }
       }

        binding.txtSignUp.setOnClickListener {
           startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    private fun authenticateWithOTP(mobile: String) {

        val mobileNumber = binding.edtMobile.text.toString().trim()

        showProgressDialog()
       val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
          // .setPhoneNumber("+91$mobile")
           .setPhoneNumber(mobileNumber)
           .setTimeout(60L,TimeUnit.SECONDS)
           .setActivity(this)
           .setCallbacks(callbacks)
           .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

     val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {


        }

        override fun onVerificationFailed(e: FirebaseException) {

                Global.showToast(this@LoginActivity,e.message!!)
                println("Here :"+e.message!!)
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            dialog.dismiss()
            val mobileNumber = binding.edtMobile.text.toString()

            val i = Intent(this@LoginActivity,OTPActivity::class.java)
            i.putExtra("verificationId",verificationId)
            i.putExtra("mobileNumber",mobileNumber)
            startActivity(i)

            Global.saveStringInSharedPref(this@LoginActivity,"mobileNumber",mobileNumber)
        }
    }



    private fun validateData() : Boolean{
       if(binding.edtMobile.text.toString().isEmpty()){
           binding.edtMobile.error = resources.getString(R.string.mobile_number_cannot_be_empty)
       return false
       }

        return true
    }

    private fun initializeFields() {
       binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        view = binding.root
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