package com.abhi41.borutoapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhi41.borutoapp.domain.model.Hero

@Dao
interface HeroDao {
    /*
      PagingSource is a base class for our asynchronous loading snapshot of data with
    help of this we retrieve all item from our database in paginated way
     */
    @Query("SELECT * FROM hero_table ORDER BY id ASC")
    fun getAllheroes(): PagingSource<Int, Hero>

    @Query("SELECT * FROM hero_table WHERE id = :heroId")
    fun getSelectedHero(heroId: Int): Hero

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHeroes(hero: List<Hero>)

    @Query("DELETE FROM hero_table")
    suspend fun deleteAllHeroes()
}