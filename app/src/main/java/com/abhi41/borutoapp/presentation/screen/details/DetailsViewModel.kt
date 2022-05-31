package com.abhi41.borutoapp.presentation.screen.details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.borutoapp.data.local.entity.Hero
import com.abhi41.borutoapp.domain.use_cases.UseCases
import com.abhi41.borutoapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DetailsViewModel"

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle //savedStateHandle allow us to retrieve heroId from DetailScreen
) : ViewModel() {

    private val _selectedHero: MutableStateFlow<Hero?> = MutableStateFlow(null)
    val selectedHero: StateFlow<Hero?> = _selectedHero

    init {
        viewModelScope.launch(Dispatchers.IO) {
            /* so with the help of savedStateHandle
              we get heroId which is passed through HeroScreen or SearchScreen*/
            val heroId = savedStateHandle.get<Int>(Constants.DETAILS_ARGUMENTS_KEY)
            _selectedHero.value = heroId?.let {
                useCases.getSelectedHeroUseCase(heroId = heroId)
            }
            /*   _selectedHero.value?.name?.let { log purpose only
                   Log.d(TAG, it)
               }*/
        }
    }

    /*
          Note: why we use SharedFlow: because with the shared flow, we are going to be able to
           trigger one time events and it will not trigger again even if we rotate our screen
          */
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val _colorPalette: MutableState<Map<String, String>> = mutableStateOf(mapOf())
    val colorPalette: State<Map<String, String>> = _colorPalette

    fun generateColorPalette() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.GenerateColorPalette)
        }
    }

    fun setColorPalette(colors: Map<String, String>) {
        _colorPalette.value = colors
    }
}

sealed class UiEvent {
    object GenerateColorPalette : UiEvent()
}

