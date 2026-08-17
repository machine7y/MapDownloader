package machine7y.mapdownloader.presentation.screen.country

import machine7y.mapdownloader.domain.entity.DownloadState
import machine7y.mapdownloader.presentation.base.mvvm.BaseState
import machine7y.mapdownloader.presentation.entity.RegionUiItem

data class CountryState(
    val name: String = "",
    val regionList: List<RegionUiItem.CountryUiItem> = emptyList(),
    val downloadStates: Map<String, DownloadState> = emptyMap(),
) : BaseState {

    fun isDownloadCompleted(downloadName: String): Boolean = downloadStates[downloadName] == DownloadState.Completed
}
