package machine7y.mapdownloader.data.local.source

import android.content.Context
import android.os.storage.StorageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import machine7y.mapdownloader.core.utils.atLeastO
import machine7y.mapdownloader.domain.entity.Memory
import machine7y.mapdownloader.domain.source.InternalMemorySource
import java.io.File
import java.io.IOException
import javax.inject.Inject

class InternalMemorySourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : InternalMemorySource {

    private val storageManager: StorageManager? =
        context.getSystemService(StorageManager::class.java)

    override suspend fun getMemory(): Memory = withContext(Dispatchers.IO) {
        val dir =  context.filesDir
        val totalBytes = dir.totalSpace
        val freeBytes = allocatableBytes(dir) ?: dir.usableSpace

        buildMemory(totalBytes = totalBytes, freeBytes = freeBytes)
    }

    override suspend fun clearCache(): Unit = withContext(Dispatchers.IO) {
        val sm = storageManager
        if (sm == null || !atLeastO()) {
            context.cacheDir?.listFiles()?.forEach { it.deleteRecursively() }
            return@withContext
        }
        try {
            val uuid = sm.getUuidForPath(context.filesDir)
            val allocatableBytes = sm.getAllocatableBytes(uuid)
            if (allocatableBytes > 0) {
                sm.allocateBytes(uuid, allocatableBytes)
            }
        } catch (_: IOException) {
        }
    }

    private fun allocatableBytes(dir: File): Long? = if (atLeastO()) {
        try {
            storageManager?.getAllocatableBytes(storageManager.getUuidForPath(dir))
        } catch (_: IOException) {
            null
        }
    } else {
        null
    }

    private fun buildMemory(totalBytes: Long, freeBytes: Long): Memory {
        val usedFraction = if (totalBytes <= 0L) {
            0f
        } else {
            ((totalBytes - freeBytes).toFloat() / totalBytes).coerceIn(0f, 1f)
        }
        return Memory(
            freeBytes = freeBytes,
            totalBytes = totalBytes,
            usedFraction = usedFraction,
        )
    }
}
