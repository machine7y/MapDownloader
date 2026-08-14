package machine7y.mapdownloader.presentation.screen.countrylist

import dagger.hilt.android.lifecycle.HiltViewModel
import machine7y.mapdownloader.domain.usecase.GetInternalStorageMemoryState
import machine7y.mapdownloader.presentation.base.mvvm.BaseViewModel
import machine7y.mapdownloader.presentation.mapper.MemoryUiMapper
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val getInternalStorageMemoryState: GetInternalStorageMemoryState,
    private val memoryUiMapper: MemoryUiMapper,
) : BaseViewModel<CountryListState, CountryListInternalState, CountryListEvent, CountryListLabel>(
    initialState = CountryListState(),
    initialInternalState = CountryListInternalState(),
) {
    init {
        loadMemoryState()
    }

    override fun onEvent(event: CountryListEvent) {
        when (event) {
            CountryListEvent.OnCountryClicked -> Unit
        }
    }

    private fun loadMemoryState() = launch {
        val memory = getInternalStorageMemoryState()
        updateUiState { copy(memory = memoryUiMapper.map(memory)) }
    }
}
