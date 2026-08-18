package machine7y.mapdownloader.data.remote.download

import javax.inject.Inject

class DownloadCleaner @Inject constructor(
    private val downloadFileEngine: DownloadFileEngine,
) {

    fun cleanParts() {
        downloadFileEngine.cleanParts()
    }
}
