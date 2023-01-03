package com.example.shopcartappinkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    private lateinit var binding : FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentCartBinding.inflate(layoutInflater)
        isFromProductDetailActivity()

        return binding.root
    }

    private fun isFromProductDetailActivity() {
        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isAddedInCart",false)
        editor.apply()
    }


}