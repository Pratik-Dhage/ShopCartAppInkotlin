package com.example.shopcartappinkotlin.roomDB

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//We can also use AddProductModel as data class here

@Entity(tableName = "products")
data class ProductModel(
    @PrimaryKey
    @NonNull
    val productId : String,
    @ColumnInfo(name ="productName")
    val productName : String ="",
    @ColumnInfo(name ="productCoverImg")
    val productCoverImg : String ="",
    @ColumnInfo(name ="productSp")
    val productSp : String ="",
    )
