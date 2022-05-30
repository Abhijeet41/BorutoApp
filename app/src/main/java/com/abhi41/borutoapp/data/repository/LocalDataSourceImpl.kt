package com.abhi41.borutoapp.data.repository

import com.abhi41.borutoapp.data.local.BorutoDatabase
import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(borutoDatabase: BorutoDatabase) : LocalDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelectedHero(heroId = heroId)
    }
}