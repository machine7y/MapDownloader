package machine7y.mapdownloader.presentation.screen.country

import machine7y.mapdownloader.presentation.base.mvvm.BaseEvent

sealed interface CountryEvent : BaseEvent {

    data object OnBackClicked : CountryEvent

    data class OnItemClicked(
        val localRegionId: Int,
        val name: String,
        val downloadName: String,
        val isMap: Boolean,
        val hasChildren: Boolean,
    ) : CountryEvent
}
