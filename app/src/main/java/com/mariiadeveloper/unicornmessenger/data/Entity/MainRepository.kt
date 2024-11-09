package com.makashovadev.filmsearcher.data.Entity

import androidx.lifecycle.LiveData
import com.makashovadev.filmsearcher.data.DAO.UserDao
import com.mariiadeveloper.unicornmessenger.data.Entity.User
//import com.makashovadev.filmsearcher.data.DAO.FilmDao
import java.util.concurrent.Executors


// Операции взаимодействия с БД,
class  MainRepository (private val userDao:UserDao) {

    val usersDataBase: MutableList<User> = mutableListOf()

    fun getUsers(): List<User> {
        return usersDataBase
    }


    fun putToDb(films: List<User>) {
        //Запросы в БД должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            userDao.insertAll(films)
        }
    }

    fun getAllFromDB(): LiveData<List<User>> {
        return userDao.getUsersFromBD()
    }


    fun clearAll() {
        //Запросы в БД должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            //filmDao.deleteAll()
        }
    }
}