package machine7y.mapdownloader.data.remote.source

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import machine7y.mapdownloader.data.remote.api.OsmandApi
import machine7y.mapdownloader.domain.source.MapDownloadSource
import java.io.File
import javax.inject.Inject

private const val MAPS_DIR_NAME = "maps"
private const val FILE_NAME_SUFFIX = "_2.obf.zip"

class MapDownloadSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val osmandApi: OsmandApi,
) : MapDownloadSource {

    override suspend fun downloadMap(downloadName: String): Unit = withContext(Dispatchers.IO) {
        val serverFileName = buildServerFileName(downloadName)
        val responseBody = osmandApi.downloadMap(serverFileName)

        val mapsDir = File(context.filesDir, MAPS_DIR_NAME).apply { mkdirs() }
        val targetFile = File(mapsDir, serverFileName)

        responseBody.byteStream().use { input ->
            targetFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }

    private fun buildServerFileName(downloadName: String): String =
        downloadName.replaceFirstChar { it.uppercase() } + FILE_NAME_SUFFIX
}
