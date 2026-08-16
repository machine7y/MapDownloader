package machine7y.mapdownloader.presentation.screen.country

import machine7y.mapdownloader.presentation.base.mvvm.BaseEvent

sealed interface CountryEvent : BaseEvent {

    data object OnBackClicked : CountryEvent

    data class OnRegionClicked(
        val localRegionId: Int,
        val hasChildren: Boolean,
    ) : CountryEvent
}
