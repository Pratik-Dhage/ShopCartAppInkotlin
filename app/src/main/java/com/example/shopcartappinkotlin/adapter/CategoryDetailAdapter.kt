package com.example.shopcartappinkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.databinding.ItemCategoryDetailBinding
import com.example.shopcartappinkotlin.model.AddProductModel
import com.example.shopcartappinkotlin.model.CategoryModel

class CategoryDetailAdapter(val context : Context, val list : ArrayList<AddProductModel>) : RecyclerView.Adapter<CategoryDetailAdapter.MyViewHolderClass>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderClass {
       val view : ItemCategoryDetailBinding = DataBindingUtil.inflate(
           LayoutInflater.from(parent.context), R.layout.item_category_detail,parent,false)

        return MyViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: MyViewHolderClass, position: Int) {
        val a = list[position]
        val context = holder.itemView.context

        holder.binding.txtDetailCategoryProductName.text = a.productName
        holder.binding.txtDetailCategoryProductSp.text = a.productSp
        Glide.with(context).load(a.productCoverImg).into(holder.binding.ivCategoryDetail)

    }

    override fun getItemCount(): Int {
       return list.size
    }

    class MyViewHolderClass(var binding : ItemCategoryDetailBinding) : RecyclerView.ViewHolder(binding.root)

}