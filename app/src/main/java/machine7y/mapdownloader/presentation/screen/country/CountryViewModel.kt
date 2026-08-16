package machine7y.mapdownloader.presentation.screen.country

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import machine7y.mapdownloader.domain.usecase.GetRegionUseCase
import machine7y.mapdownloader.domain.usecase.RegionUseCaseParam
import machine7y.mapdownloader.presentation.base.mvvm.BaseViewModel

@HiltViewModel(assistedFactory = CountryViewModelFactory::class)
class CountryViewModel @AssistedInject constructor(
    @Assisted internalState: CountryInternalState,
    private val getRegionUseCase: GetRegionUseCase,
) : BaseViewModel<CountryState, CountryInternalState, CountryEvent, CountryLabel>(
    initialState = CountryState(),
    initialInternalState = internalState,
) {
    init {
        loadRegionName()
    }

    private fun loadRegionName() = launch {
        val name = getRegionUseCase(RegionUseCaseParam(localRegionId = internalState.localRegionId)).name

        updateUiState {
            copy(
                name = name,
            )
        }
    }
}
