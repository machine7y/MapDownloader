package machine7y.mapdownloader.presentation.screen.country

import dagger.assisted.AssistedFactory

@AssistedFactory
interface CountryViewModelFactory {

    fun create(internalState: CountryInternalState): CountryViewModel
}
