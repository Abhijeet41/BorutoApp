package com.abhi41.borutoapp.presentation.screen.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchHeroes = MutableStateFlow<PagingData<Hero>>(PagingData.empty())
    val searchedHeroes = _searchHeroes

    fun updateSearchQuery(query: String){
        _searchQuery.value = query
    }

    fun searchHeroes(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.searchHeroesUsecase(query = query)
                .cachedIn(viewModelScope).collect {
                    _searchHeroes.value = it
                }
            //note: We used cachedIn so on rotation changed search data it should remains as it is
        }
    }
}