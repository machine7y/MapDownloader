package machine7y.mapdownloader.presentation.screen.countrylist

import machine7y.mapdownloader.presentation.base.mvvm.BaseLabel

sealed interface CountryListLabel : BaseLabel {

    data object ShowNoNestedRegionsMessage : CountryListLabel
}
