package com.mariiadeveloper.unicornmessenger.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.makashovadev.filmsearcher.data.DAO.UserDao
import com.mariiadeveloper.unicornmessenger.data.Entity.User

@Database(entities = [(User::class)], version = 1)
abstract class UserRoomDatabase: RoomDatabase() {

    abstract fun UserDao(): UserDao

    // реализуем синглтон
    companion object {
        private var INSTANCE: UserRoomDatabase? = null
        fun getInstance(context: Context): UserRoomDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java,
                        "User_database"

                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}