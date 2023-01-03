package com.example.shopcartappinkotlin.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductModel::class], version = 1)
abstract class ProductDB : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {

        private var database: ProductDB? = null
        private val DATABASE_NAME = "ProductDataBase"

        @Synchronized // this will allow only one instance throughout the app
        fun getInstance(context: Context): ProductDB {

            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDB::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database!!
        }
    }
}