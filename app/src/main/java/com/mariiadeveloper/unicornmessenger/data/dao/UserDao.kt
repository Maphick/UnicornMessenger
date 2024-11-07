package com.makashovadev.filmsearcher.data.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
//import com.makashovadev.filmsearcher.data.Entity.Film

//Помечаем, что это не просто интерфейс, а Dao-объект
/*@Dao
interface UserDao {
    //Запрос на всю таблицу
    @Query("SELECT * FROM cached_films")
    fun getCachedFilms(): LiveData<List<Film>>

    //Кладём списком в БД, в случае конфликта перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)

    @Query("DELETE FROM cached_films")
    fun deleteAll()
}
*/