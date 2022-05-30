package com.abhi41.borutoapp.domain.repository

import com.abhi41.borutoapp.data.local.entity.Hero

interface LocalDataSource {

    suspend fun getSelectedHero(heroId: Int): Hero

}