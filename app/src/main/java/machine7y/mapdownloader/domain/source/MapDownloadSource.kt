package machine7y.mapdownloader.domain.source

interface MapDownloadSource {

    suspend fun downloadMap(downloadName: String)
}
