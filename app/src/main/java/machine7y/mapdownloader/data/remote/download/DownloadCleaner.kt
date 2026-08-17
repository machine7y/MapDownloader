package machine7y.mapdownloader.data.remote.download

import javax.inject.Inject

class DownloadCleaner @Inject constructor(
    private val engine: DownloadEngine,
) {

    fun cleanParts() {
        engine.cleanParts()
    }
}
