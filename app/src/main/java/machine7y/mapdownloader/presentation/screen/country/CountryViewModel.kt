package machine7y.mapdownloader.presentation.screen.country

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import machine7y.mapdownloader.domain.usecase.DownloadMapUseCase
import machine7y.mapdownloader.domain.usecase.DownloadMapUseCaseParam
import machine7y.mapdownloader.domain.usecase.GetRegionUseCase
import machine7y.mapdownloader.domain.usecase.RegionUseCaseParam
import machine7y.mapdownloader.presentation.base.mvvm.BaseViewModel
import machine7y.mapdownloader.presentation.navigation.Router
import machine7y.mapdownloader.presentation.screen.Screen
import machine7y.mapdownloader.presentation.screen.country.CountryEvent.OnRegionClicked
import machine7y.mapdownloader.presentation.screen.country.CountryLabel.ShowNoNestedRegionsMessage
import machine7y.mapdownloader.presentation.screen.country.mapper.CountryRegionUiMapper

@HiltViewModel(assistedFactory = CountryViewModelFactory::class)
class CountryViewModel @AssistedInject constructor(
    @Assisted internalState: CountryInternalState,
    private val getRegionUseCase: GetRegionUseCase,
    private val downloadMapUseCase: DownloadMapUseCase,
    private val countryRegionUiMapper: CountryRegionUiMapper,
    private val router: Router,
) : BaseViewModel<CountryState, CountryInternalState, CountryEvent, CountryLabel>(
    initialState = CountryState(),
    initialInternalState = internalState,
) {
    init {
        loadRegion()
    }

    override fun onEvent(event: CountryEvent) {
        when (event) {
            CountryEvent.OnBackClicked -> router.pop()
            is OnRegionClicked -> onRegionClicked(event)
        }
    }

    private fun onRegionClicked(event: OnRegionClicked) {
        when {
            event.isMap -> downloadMap(event.downloadName)
            event.hasChildren -> router.navigate(Screen.Country(event.localRegionId))
            else -> launch {
                publishLabel(ShowNoNestedRegionsMessage)
                // TODO download map?
//                downloadMapUseCase(DownloadMapUseCaseParam(downloadName = event.downloadName))
            }
        }
    }

    private fun downloadMap(downloadName: String) = launch {
        downloadMapUseCase(DownloadMapUseCaseParam(downloadName = downloadName))
    }

    private fun loadRegion() = launch {
        val region = getRegionUseCase(RegionUseCaseParam(localRegionId = internalState.localRegionId))

        updateUiState {
            copy(
                name = region.name,
                regionList = countryRegionUiMapper.map(region),
            )
        }
    }
}
