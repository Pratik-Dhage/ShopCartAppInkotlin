package com.example.shopcartappinkotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.databinding.ActivityProductDetailBinding
import com.example.shopcartappinkotlin.helping_classes.Global
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailBinding
    private lateinit var view : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeFields()
        getProductDetails(intent.getStringExtra("productId"))
    }

    private fun getProductDetails(proId: String?) {

       Firebase.firestore.collection("products").document(proId!!)
           .get().addOnSuccessListener {

               binding.txtProductDetailName.text = it.getString("productName")
               binding.txtProductDetailDescription.text = it.getString("productDescription")
               binding.txtProductDetailSp.text = it.getString("productSp")

               val list = it.get("productImages") as ArrayList<String>

              val sliderList = ArrayList<SlideModel>()
               for(images in list){
                   sliderList.add(SlideModel(images,ScaleTypes.FIT))
               }

               binding.imageSlider.setImageList(sliderList)  //sets images in slider


           }.addOnFailureListener {
               Global.showSnackBar(view,it.toString())
           }


    }

    private fun initializeFields() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_product_detail)
        view = binding.root
    }
}