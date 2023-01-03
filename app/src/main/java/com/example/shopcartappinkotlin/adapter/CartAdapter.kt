package com.example.shopcartappinkotlin.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopcartappinkotlin.R
import com.example.shopcartappinkotlin.activity.ProductDetailActivity
import com.example.shopcartappinkotlin.databinding.ItemCartLayoutBinding
import com.example.shopcartappinkotlin.roomDB.ProductDB
import com.example.shopcartappinkotlin.roomDB.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context : Context, val list : List<ProductModel>) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view : ItemCartLayoutBinding = DataBindingUtil.inflate(
          LayoutInflater.from(parent.context), R.layout.item_cart_layout , parent , false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val a  = list[position]
        val context = holder.itemView.context

        holder.binding.txtCartProductName.text = a.productName
        holder.binding.txtCartProductSp.text = a.productSp
        Glide.with(context).load(a.productCoverImg).into(holder.binding.ivCartLayout)

        //to delete cart item
        holder.binding.ivCartDelete.setOnClickListener{

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
            builder.setCancelable(true)
            builder.setMessage("Do you wish to delete this item")
            builder.setPositiveButton("Yes") { dialog, _ ->

                val dao = ProductDB.getInstance(context).productDao()

                //GlobalScope means as long as Application is Alive
                GlobalScope.launch(Dispatchers.IO) {
                    dao.deleteProduct(ProductModel(
                        a.productId,a.productName,a.productCoverImg,a.productSp))
                }
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()

        }

        //goto ProductDetailActivity
        holder.itemView.setOnClickListener {
            val i = Intent(context,ProductDetailActivity::class.java)
            i.putExtra("productId",a.productId)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(var binding : ItemCartLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}