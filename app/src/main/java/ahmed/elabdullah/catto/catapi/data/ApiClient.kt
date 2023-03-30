package ahmed.elabdullah.catto.catapi.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * A client for communicating with the Cat API.
 */
class ApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * The [CatApiService] instance used for making API requests.
     */
    val catApi = retrofit.create(CatApiService::class.java)

    companion object {
        private const val BASE_URL = "https://api.thecatapi.com"
    }
}