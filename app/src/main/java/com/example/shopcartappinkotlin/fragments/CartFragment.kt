package com.example.shopcartappinkotlin.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.activity.AddressActivity
import com.example.shopcartappinkotlin.adapter.CartAdapter
import com.example.shopcartappinkotlin.databinding.FragmentCartBinding
import com.example.shopcartappinkotlin.helping_classes.Global
import com.example.shopcartappinkotlin.roomDB.ProductDB
import com.example.shopcartappinkotlin.roomDB.ProductModel


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

        val productIdsList = ArrayList<String>()

        val dao = ProductDB.getInstance(requireContext()).productDao()

        dao.getAllProducts().observe(requireActivity()){
            //set data in Cart RecyclerView
            binding.rvCartFragment.adapter = CartAdapter(requireContext(),it)

            productIdsList.clear()
            for(data in it){

                productIdsList.add(data.productId)
            }

          //  totalCost(it) //to calculate total items and payment in Cart

            // Below code is to calculate total items and payment in Cart
            var total = 0
            for(item in it){
                total += item.productSp.toInt()
            }

            binding.txtCartItems.text = resources.getString(R.string.total_items_in_cart)+" ${productIdsList.size}"
            binding.txtCartTotal.text = resources.getString(R.string.total_payment)+total.toString()

            binding.btnCheckout.setOnClickListener {
                val i = Intent(requireContext(), AddressActivity::class.java)
                i.putExtra("totalCost",total.toString())
                i.putExtra("productIdsList",productIdsList) // to further pass in CheckOut Activity
                startActivity(i)
            }

        }
    }

    @SuppressLint("SetTextI18n")
  /*  private fun totalCost(list: List<ProductModel>) {
        var total = 0
        for(item in list){
            total += item.productSp.toInt()
        }

        binding.txtCartItems.text = resources.getString(R.string.total_items_in_cart)+" ${list.size}"
        binding.txtCartTotal.text = resources.getString(R.string.total_payment)+total.toString()

        binding.btnCheckout.setOnClickListener {
            val i = Intent(requireContext(), AddressActivity::class.java)
            i.putExtra("totalCost",total.toString())
            startActivity(i)
        }
    }*/

    private fun isFromProductDetailActivity() {
        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isAddedInCart",false)
        editor.apply()
    }


}