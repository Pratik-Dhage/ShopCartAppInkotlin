package com.example.shopcartappinkotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.adapter.CategoryDetailAdapter
import com.example.shopcartappinkotlin.adapter.ProductAdapter
import com.example.shopcartappinkotlin.databinding.ActivityCategoryDetailBinding
import com.example.shopcartappinkotlin.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCategoryDetailBinding
    private lateinit var view : View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeFields()
        getCategoryProducts(intent.getStringExtra("catName"))
    }


    private fun initializeFields() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_category_detail)
        view = binding.root
    }

    private fun getCategoryProducts(categoryName: String?) {

        val list = arrayListOf<AddProductModel>()

        //get data from FireStore
        Firebase.firestore.collection("products").whereEqualTo("productCategory",categoryName)
            .get().addOnSuccessListener {
            list.clear()
            for(doc in it.documents){

                val data = doc.toObject(AddProductModel::class.java)
                list.add(data!!)
            }

            //setUpRecyclerViewData
            binding.rvCategoryItem.adapter = CategoryDetailAdapter(this,list)
        }
    }
}