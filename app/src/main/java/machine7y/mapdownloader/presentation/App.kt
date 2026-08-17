package machine7y.mapdownloader.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import machine7y.mapdownloader.data.remote.download.DownloadCleaner
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var downloadCleaner: DownloadCleaner

    override fun onCreate() {
        super.onCreate()
        downloadCleaner.cleanParts()
    }
}
