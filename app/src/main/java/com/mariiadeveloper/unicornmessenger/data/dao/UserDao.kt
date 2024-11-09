package com.makashovadev.filmsearcher.data.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mariiadeveloper.unicornmessenger.data.Entity.User

//import com.makashovadev.filmsearcher.data.Entity.Film

//Помечаем, что это не просто интерфейс, а Dao-объект
//Помечаем, что это не просто интерфейс, а Dao-объект
@Dao
interface UserDao {

    @Insert
    fun addUser(user: User)

    //Запрос на всю таблицу
    @Query("SELECT * FROM user")
    fun getUsersFromBD(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE phoneNumber=:phone")
    fun getUserByPhone(phone: String): User?

    //Кладём списком в БД, в случае конфликта перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<User>)

    //@Query("DELETE FROM user")
    //fun deleteAll()

    @Delete
    fun removeUser(user: User)
}