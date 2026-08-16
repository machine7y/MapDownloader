package machine7y.mapdownloader.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import machine7y.mapdownloader.data.remote.download.DownloadInitializer
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var downloadInitializer: DownloadInitializer

    override fun onCreate() {
        super.onCreate()
        downloadInitializer.cleanParts()
    }
}
