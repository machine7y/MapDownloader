package machine7y.mapdownloader.presentation.screen.countrylist

import dagger.hilt.android.lifecycle.HiltViewModel
import machine7y.mapdownloader.domain.usecase.GetInternalStorageMemoryStateUseCase
import machine7y.mapdownloader.domain.usecase.GetRegionListUseCase
import machine7y.mapdownloader.presentation.base.mvvm.BaseViewModel
import machine7y.mapdownloader.presentation.navigation.Router
import machine7y.mapdownloader.presentation.screen.Screen
import machine7y.mapdownloader.presentation.screen.countrylist.CountryListEvent.OnCountryClicked
import machine7y.mapdownloader.presentation.screen.countrylist.CountryListLabel.ShowNoNestedRegionsMessage
import machine7y.mapdownloader.presentation.screen.countrylist.mapper.MemoryUiMapper
import machine7y.mapdownloader.presentation.screen.countrylist.mapper.RegionUiMapper
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val getInternalStorageMemoryStateUseCase: GetInternalStorageMemoryStateUseCase,
    private val getRegionListUseCase: GetRegionListUseCase,
    private val memoryUiMapper: MemoryUiMapper,
    private val regionUiMapper: RegionUiMapper,
    private val router: Router,
) : BaseViewModel<CountryListState, CountryListInternalState, CountryListEvent, CountryListLabel>(
    initialState = CountryListState(),
    initialInternalState = CountryListInternalState(),
) {
    init {
        loadMemoryState()
        loadRegionList()
    }

    override fun onEvent(event: CountryListEvent) {
        when (event) {
            is OnCountryClicked -> onCountryClicked(event)
        }
    }

    private fun onCountryClicked(event: OnCountryClicked) {
        if (event.hasChildren) {
            router.navigate(Screen.Country(event.localRegionId))
        } else {
            launch { publishLabel(ShowNoNestedRegionsMessage) }
        }
    }

    private fun loadMemoryState() = launch {
        val memory = getInternalStorageMemoryStateUseCase()
        updateUiState { copy(memory = memoryUiMapper.map(memory)) }
    }

    private fun loadRegionList() = launch {
        val regionList = getRegionListUseCase()
        updateUiState { copy(regionList = regionUiMapper.map(regionList)) }
    }
}
