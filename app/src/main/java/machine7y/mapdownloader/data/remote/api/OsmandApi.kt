package machine7y.mapdownloader.data.remote.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming

interface OsmandApi {

    @Streaming
    @GET("download.php?standard=yes")
    suspend fun downloadMap(@Query("file") file: String): ResponseBody
}
