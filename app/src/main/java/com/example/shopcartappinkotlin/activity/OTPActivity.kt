package com.example.shopcartappinkotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.shopcartappinkotlin.MainActivity
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.databinding.ActivityLoginBinding
import com.example.shopcartappinkotlin.databinding.ActivityOtpactivityBinding
import com.example.shopcartappinkotlin.helping_classes.Global
import com.example.shopcartappinkotlin.helping_classes.NetworkUtilities
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.ktx.Firebase

class OTPActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOtpactivityBinding
    private lateinit var view: View
    private lateinit var dialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeFields()
        onClickListener()

    }

    private fun initializeFields() {
        binding = DataBindingUtil. setContentView(this,R.layout.activity_otpactivity)
        view = binding.root
    }

    private fun onClickListener() {
       binding.btnOTPVerify.setOnClickListener {

           val otp = binding.edtOTP.text.toString()
           if(NetworkUtilities.getConnectivityStatus(this)){
               if(otp.isNotEmpty()){
                     showProgressDialog()
                       verifyOTP(otp)       
               }
               else{
                   Global.showSnackBar(view,resources.getString(R.string.please_provide_otp))
               }
           }
           else{
               Global.showSnackBar(view,resources.getString(R.string.check_internet))
           }

               }
    }

    private fun verifyOTP(otp: String) {

        val verificationId = intent.getStringExtra("verificationId")
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    dialog.dismiss()

                    //store mobile number to display in Address Activity
                    val mobileNumber = intent.getStringExtra("mobileNumber")
                    Global.saveStringInSharedPref(this,"mobileNumber",mobileNumber!!)

                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Global.showSnackBar(view,task.exception.toString())
                    }

                }
            }
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