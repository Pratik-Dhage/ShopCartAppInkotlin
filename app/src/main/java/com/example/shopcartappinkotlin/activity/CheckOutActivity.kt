package com.example.shopcartappinkotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.helping_classes.Global
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class CheckOutActivity : AppCompatActivity() , PaymentResultListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        Checkout.preload(applicationContext)
        val checkout = Checkout()

     checkout.setKeyID("rzp_live_XXXXXXXXXXXXXX") //Add RazorPay Api key here


        try {
            val options = JSONObject()
            options.put("name","ShopCartAppInKotlin ")
            options.put("description","ShopCartApp By Pratik")
            //You can omit the image option to fetch the image from the dashboard
          //  options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#FFEFEA");
            options.put("currency","INR");
            options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","500")//pass amount in currency subunits

          /*  val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);*/

            val prefill = JSONObject()
            prefill.put("email","dhagepratik94@gmail.com")
            prefill.put("contact","8652782314")

            options.put("prefill",prefill)
            checkout.open(this,options)
        }catch (e: Exception){
           Global.showToast(this,e.localizedMessage!!)
        }


    }

    override fun onPaymentSuccess(result: String?) {
        Global.showToast(this, "Payment Success:"+result.toString())

    }

    override fun onPaymentError(p0: Int, error: String?) {
        Global.showToast(this,"Payment Failed:"+error.toString())
    }
}