package ahmed.elabdullah.catto.catapi.data

import retrofit2.http.GET
import retrofit2.http.Query
/**
 * API service for retrieving cat information from the Cat API.
 */
interface CatApiService {

    /**
     * Searches for cats with the given breed IDs, up to the specified limit.
     *
     * @param breedIds The IDs of the cat breeds to search for.
     * @param limit The maximum number of cats to retrieve.
     * @return A list of [Cat] objects matching the search criteria.
     */
    @GET("/v1/images/search")
    suspend fun searchCats(
        @Query("breed_ids") breedIds: String?,
        @Query("limit") limit: Int
    ): List<Cat>

    /**
     * Represents a cat image retrieved from the Cat API.
     */
    data class Cat(
        val id: String,
        val url: String,
        val width: Int,
        val height: Int,
        val breeds: List<CatBreed>?,
    )

    /**
     * Represents a breed of cat.
     */
    data class CatBreed(
        val id: String,
        val name: String,
        val weight: Weight
    )

    /**
     * Represents the weight of a cat, in both imperial and metric units.
     */
    data class Weight(
        val imperial: String?,
        val metric: String?
    )
}
