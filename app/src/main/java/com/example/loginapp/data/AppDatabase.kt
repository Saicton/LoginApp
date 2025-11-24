package com.example.loginapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 2, exportSchema = false)  // Incrementé la versión
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(contexto: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    contexto.applicationContext,
                    AppDatabase::class.java,
                    "app.db"
                )
                    .fallbackToDestructiveMigration()  // Agregado: recrea la BD cuando hay cambios
                    .build()
                    .also { INSTANCE = it }
            }
    }
}