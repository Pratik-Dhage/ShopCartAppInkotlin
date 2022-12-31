package com.example.shopcartappinkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.adapter.CategoryAdapter
import com.example.shopcartappinkotlin.adapter.ProductAdapter
import com.example.shopcartappinkotlin.databinding.FragmentHomeBinding
import com.example.shopcartappinkotlin.model.AddProductModel
import com.example.shopcartappinkotlin.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

   private lateinit var binding : FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding =FragmentHomeBinding.inflate(layoutInflater)
       getHomeCategory()
        getHomeSlider()
        getHomeProduct()
        return binding.root
    }

    private fun getHomeProduct() {

        val list = arrayListOf<AddProductModel>()

        //get data from FireStore
        Firebase.firestore.collection("products").get().addOnSuccessListener {
            list.clear()
            for(doc in it.documents){

                val data = doc.toObject(AddProductModel::class.java)
                list.add(data!!)
            }

            binding.rvHomeProduct.adapter = ProductAdapter(requireContext(),list)
        }



    }

    private fun getHomeCategory() {
        val list = arrayListOf<CategoryModel>()

        //get data from FireStore
        Firebase.firestore.collection("categories").get().addOnSuccessListener {
            list.clear()
            for(doc in it.documents){

                val data = doc.toObject(CategoryModel::class.java)
                list.add(data!!)
            }

            binding.rvHomeCategory.adapter = CategoryAdapter(requireContext(),list)
    }
}

    private fun getHomeSlider(){

        Firebase.firestore.collection("slider")
            .document("item").get().addOnSuccessListener {

          Glide.with(requireContext()).load(it.get("img")).into(binding.ivCardView)

        }
    }


}