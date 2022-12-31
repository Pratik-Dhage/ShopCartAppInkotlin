package com.example.shopcartappinkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.databinding.ItemHomeProductBinding
import com.example.shopcartappinkotlin.model.AddProductModel

class ProductAdapter(val context : Context, val list: ArrayList<AddProductModel>) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderClass {

        val view: ItemHomeProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_home_product, parent, false
        )
        return MyViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: MyViewHolderClass, position: Int) {

        val a  = list[position]
        val context = holder.itemView.context

        holder.binding.txtProductName.text = a.productName
        holder.binding.txtProductDescription.text = a.productDescription
        holder.binding.txtProductMRP.text = a.productMrp
        holder.binding.txtProductSP.text = a.productSp
        Glide.with(context).load(a.productCoverImg).into(holder.binding.ivProductList)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyViewHolderClass(var binding: ItemHomeProductBinding) :
        RecyclerView.ViewHolder(binding.root)


}