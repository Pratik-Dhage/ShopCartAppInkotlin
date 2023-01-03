package com.example.shopcartappinkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.adapter.CartAdapter
import com.example.shopcartappinkotlin.databinding.FragmentCartBinding
import com.example.shopcartappinkotlin.roomDB.ProductDB


class CartFragment : Fragment() {

    private lateinit var binding : FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        isFromProductDetailActivity()
        getCartDataFromRoomDB()

        return binding.root
    }

    private fun getCartDataFromRoomDB() {

        val dao = ProductDB.getInstance(requireContext()).productDao()

        dao.getAllProducts().observe(requireActivity()){
            //set data in Cart RecyclerView
            binding.rvCartFragment.adapter = CartAdapter(requireContext(),it)
        }
    }

    private fun isFromProductDetailActivity() {
        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isAddedInCart",false)
        editor.apply()
    }


}