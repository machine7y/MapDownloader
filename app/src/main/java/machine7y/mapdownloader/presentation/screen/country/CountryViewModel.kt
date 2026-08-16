package machine7y.mapdownloader.presentation.screen.country

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import machine7y.mapdownloader.domain.usecase.EnqueueDownloadUseCase
import machine7y.mapdownloader.domain.usecase.EnqueueDownloadUseCaseParam
import machine7y.mapdownloader.domain.usecase.GetRegionUseCase
import machine7y.mapdownloader.domain.usecase.ObserveDownloadStatesUseCase
import machine7y.mapdownloader.domain.usecase.ObserveDownloadStatesUseCaseParam
import machine7y.mapdownloader.domain.usecase.RegionUseCaseParam
import machine7y.mapdownloader.presentation.base.mvvm.BaseViewModel
import machine7y.mapdownloader.presentation.entity.RegionUiItem
import machine7y.mapdownloader.presentation.navigation.Router
import machine7y.mapdownloader.presentation.screen.Screen
import machine7y.mapdownloader.presentation.screen.country.CountryEvent.OnRegionClicked
import machine7y.mapdownloader.presentation.screen.country.CountryLabel.ShowNoNestedRegionsMessage
import machine7y.mapdownloader.presentation.screen.country.mapper.CountryRegionUiMapper

@HiltViewModel(assistedFactory = CountryViewModelFactory::class)
class CountryViewModel @AssistedInject constructor(
    @Assisted internalState: CountryInternalState,
    private val getRegionUseCase: GetRegionUseCase,
    private val enqueueDownloadUseCase: EnqueueDownloadUseCase,
    private val observeDownloadStatesUseCase: ObserveDownloadStatesUseCase,
    private val countryRegionUiMapper: CountryRegionUiMapper,
    private val router: Router,
) : BaseViewModel<CountryState, CountryInternalState, CountryEvent, CountryLabel>(
    initialState = CountryState(),
    initialInternalState = internalState,
) {
    init {
        loadRegion()
        observeDownloadStates(state.regionList)
    }

    override fun onEvent(event: CountryEvent) {
        when (event) {
            CountryEvent.OnBackClicked -> router.pop()
            is OnRegionClicked -> onRegionClicked(event)
        }
    }

    private fun onRegionClicked(event: OnRegionClicked) {
        when {
            event.isMap -> enqueueDownload(event.downloadName)
            event.hasChildren -> router.navigate(Screen.Country(event.localRegionId))
            else -> launch { publishLabel(ShowNoNestedRegionsMessage) }
        }
    }

    private fun enqueueDownload(fileId: String) = launch {
        enqueueDownloadUseCase(EnqueueDownloadUseCaseParam(fileId = fileId))
    }

    private fun loadRegion() = launch {
        val region = getRegionUseCase(RegionUseCaseParam(localRegionId = internalState.localRegionId))
        val regionList = countryRegionUiMapper.map(region)

        updateUiState {
            copy(
                name = region.name,
                regionList = regionList,
            )
        }

        regionList
    }

    private fun observeDownloadStates(regionList: List<RegionUiItem.CountryUiItem>) = launch {
        val fileIds = regionList.filter { it.isMap }
            .map { it.downloadName }
            .toSet()

        if (fileIds.isEmpty()) return@launch

        observeDownloadStatesUseCase(ObserveDownloadStatesUseCaseParam(fileIds)).collect { downloadStates ->
            updateUiState {
                copy(
                    downloadStates = downloadStates,
                )
            }
        }
    }
}
