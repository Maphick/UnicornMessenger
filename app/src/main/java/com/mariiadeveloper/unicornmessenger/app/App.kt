package com.mariiadeveloper.unicornmessenger.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.makashovadev.filmsearcher.data.DAO.UserDao
import com.makashovadev.filmsearcher.data.Entity.ApiConstants
import com.makashovadev.filmsearcher.data.Entity.MainRepository
import com.mariiadeveloper.unicornmessenger.data.Entity.User
import com.mariiadeveloper.unicornmessenger.data.db.UserRoomDatabase
import com.mariiadeveloper.unicornmessenger.data.settings.PreferenceProvider
import com.mariiadeveloper.unicornmessenger.domain.Interactor
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// инициализировать и запусть модули, которые должны работать только когда приложение запускается
// то их нужно запускать в классе app
// тк тут есть метод onCreate callBack ЖЦ приложения
//@HiltAndroidApp
// приложение использует хилт для инжекта зависимостей
class App: Application() {

    lateinit var repo: MainRepository
    lateinit var interactor: Interactor
    lateinit var retrofit:Retrofit
    lateinit var appContext:Context
    // для хранения ключей и времени последнего обновления
    lateinit var preferences: PreferenceProvider
    lateinit var userDatabase: UserRoomDatabase


    //= PreferenceProvider

    override fun onCreate() {
        super.onCreate()
        //Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this
        //Инициализируем репозиторий
        /*repo = MainRepository(
            userDao = userDao
        )*/
        interactor = Interactor()
        appContext = applicationContext
        preferences =  PreferenceProvider(appContext)
        userDatabase = makeUserDatabase(context = appContext)
        //var newUSER = User()
     //   userDatabase.UserDao().addUser(
          //  newUSER
       // )
    }


    fun makeUserDatabase(context: Context): UserRoomDatabase {
        return Room.databaseBuilder(
            context,
            UserRoomDatabase::class.java,
            "userDatabase.db"
        ).build()
    }


    companion object {
        //Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            //Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}