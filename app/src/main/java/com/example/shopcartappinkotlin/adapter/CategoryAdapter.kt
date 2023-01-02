package com.example.shopcartappinkotlin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.activity.CategoryDetailActivity
import com.example.shopcartappinkotlin.databinding.ItemHomeCategoryBinding
import com.example.shopcartappinkotlin.model.CategoryModel

class CategoryAdapter(val context : Context, val list : ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.MyViewHolderClass>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderClass {
        val view : ItemHomeCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_home_category,parent,false)

        return MyViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: MyViewHolderClass, position: Int) {

        val a = list[position]
        val context = holder.itemView.context

        holder.binding.txtCategoryName.text = a.catName
        Glide.with(context).load(a.catImage).into(holder.binding.ivItemCategory)

        holder.itemView.setOnClickListener {
            val i = Intent(context,CategoryDetailActivity::class.java)
            i.putExtra("catName", a.catName)
            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }

    class MyViewHolderClass(var binding: ItemHomeCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}