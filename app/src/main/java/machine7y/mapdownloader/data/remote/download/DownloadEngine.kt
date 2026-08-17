package machine7y.mapdownloader.data.remote.download

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import machine7y.mapdownloader.data.remote.api.OsmandApi
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val DOWNLOADS_DIR_NAME = "downloads"
private const val PART_SUFFIX = ".part"
private const val FILE_NAME_SUFFIX = "_2.obf.zip"
private const val PROGRESS_THROTTLE_MS = 250L
private const val READ_BUFFER_SIZE = 8 * 1024

enum class EngineResult { Success, Failed, Retriable }

@Singleton
class DownloadEngine @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val osmandApi: OsmandApi,
) {
    private val dir = File(context.filesDir, DOWNLOADS_DIR_NAME).apply { mkdirs() }

    fun getTargetFile(fileId: String): File = File(dir, getTargetFileName(fileId))

    fun deleteTargetFile(fileId: String) {
        getTargetFile(fileId).delete()
    }

    fun cleanParts() {
        dir.listFiles { file -> file.name.endsWith(PART_SUFFIX) }?.forEach { it.delete() }
    }

    suspend fun download(
        fileId: String,
        onProgress: (bytes: Long, total: Long) -> Unit,
    ): EngineResult = withContext(Dispatchers.IO) {
        val partFile = getPartFile(fileId)
        val targetFile = getTargetFile(fileId)

        partFile.delete()

        try {
            val responseBody = osmandApi.downloadMap(getTargetFileName(fileId))
            val total = responseBody.contentLength()

            responseBody.byteStream().use { input ->
                partFile.outputStream().use { output ->
                    val buffer = ByteArray(READ_BUFFER_SIZE)
                    var written = 0L
                    var lastEmitAt = 0L
                    while (true) {
                        ensureActive()
                        val read = input.read(buffer)
                        if (read == -1) break
                        output.write(buffer, 0, read)
                        written += read

                        val now = System.currentTimeMillis()
                        if (now - lastEmitAt >= PROGRESS_THROTTLE_MS) {
                            lastEmitAt = now
                            onProgress(written, total)
                        }
                    }
                }
            }

            if (!partFile.renameTo(targetFile)){
                EngineResult.Failed
            } else {
                EngineResult.Success
            }
        } catch (e: CancellationException) {
            partFile.delete()
            throw e
        } catch (_: HttpException) {
            partFile.delete()
            EngineResult.Failed
        } catch (_: IOException) {
            partFile.delete()
            EngineResult.Retriable
        } catch (_: Exception) {
            partFile.delete()
            EngineResult.Failed
        }
    }

    private fun getPartFile(fileId: String): File = File(dir, getTargetFileName(fileId) + PART_SUFFIX)

    private fun getTargetFileName(fileId: String): String =
        sanitize(fileId).replaceFirstChar { it.uppercase() } + FILE_NAME_SUFFIX

    private fun sanitize(fileId: String): String = fileId
        .filterNot { it.isISOControl() }
        .replace("/", "")
        .replace("\\", "")
        .replace("..", "")
}
