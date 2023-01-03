package com.example.shopcartappinkotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopcartappinkotlin.MainActivity
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.databinding.ActivityProductDetailBinding
import com.example.shopcartappinkotlin.helping_classes.Global
import com.example.shopcartappinkotlin.roomDB.ProductDB
import com.example.shopcartappinkotlin.roomDB.ProductDao
import com.example.shopcartappinkotlin.roomDB.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailBinding
    private lateinit var view : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeFields()
        getProductDetails(intent.getStringExtra("productId"))

    }

    private fun initializeFields() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_product_detail)
        view = binding.root
    }

    private fun getProductDetails(proId: String?) {

       Firebase.firestore.collection("products").document(proId!!)
           .get().addOnSuccessListener {

               val id = it.getString("productId")
               val name = it.getString("productName")
               val sellingPrice = it.getString("productSp")
               val coverImage = it.getString("productCoverImg")
               val description = it.getString("productDescription")


               binding.txtProductDetailName.text = name
               binding.txtProductDetailDescription.text = description
               binding.txtProductDetailSp.text = sellingPrice

               val list = it.get("productImages") as ArrayList<String>

              val sliderList = ArrayList<SlideModel>()
               for(images in list){
                   sliderList.add(SlideModel(images,ScaleTypes.FIT))
               }
               binding.imageSlider.setImageList(sliderList)  //sets images in slider

               cartAction(id!!,name,sellingPrice,coverImage)

           }.addOnFailureListener {
               Global.showSnackBar(view,it.toString())
           }


    }

    private fun cartAction(prod_Id: String, prod_Name: String?, prod_Sp: String?, prod_Cover_Img: String?) {

        val productDao = ProductDB.getInstance(this).productDao()

        if(productDao.isExisting(prod_Id)!=null){
            binding.btnCart.text = resources.getString(R.string.go_to_cart)
        }
        else {
            binding.btnCart.text = resources.getString(R.string.add_to_cart)
        }

        //onClickListener
        binding.btnCart.setOnClickListener {

            if(productDao.isExisting(prod_Id)!=null){
            openCart()
            }
            else{
                addToCart(productDao,prod_Id,prod_Name!!,prod_Sp!!,prod_Cover_Img!!)
            }
        }

    }

    private fun addToCart(prodDao: ProductDao, prodId: String, prodName: String, prodSp: String, prodCoverImg: String) {

        val cartData = ProductModel(prodId,prodName,prodCoverImg,prodSp)

        //LifecycleScope exists as long as Activity exists
        // Dispatchers.IO : to run on IO thread
        lifecycleScope.launch(Dispatchers.IO) {

            prodDao.insertProduct(cartData)
            binding.btnCart.text = resources.getString(R.string.go_to_cart)
        }
    }

    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isAddedInCart",true)
        editor.apply()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }



}