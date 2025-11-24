
package com.example.loginapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    //Instancia estática
    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(contexto: Context): AppDatabase =
            INSTANCE ?: synchronized(lock:this) {
                INSTANCE ?: Room.databaseBuilder(
                    contexto,
                    AppDatabase::class.java,
                    name:"app.db"
                ).build().also {INSTANCE = it}
            }
    }

    
}
