package ahmed.elabdullah.catto.catapi.data

/**
 * A repository class for interacting with the Cat API.
 */
class CatRepository {

    // Create an instance of the Cat API client
    private val apiClient = ApiClient().catApi

    /**
     * Searches for cats on the Cat API.
     *
     * @param breedIds a comma-separated list of breed IDs to filter the results by, or null to return all breeds
     * @param limit the maximum number of results to return
     * @return a list of [CatApiService.Cat] objects representing the search results
     */
    suspend fun searchCats(breedIds: String?, limit: Int): List<CatApiService.Cat> {
        return apiClient.searchCats(breedIds, limit)
    }

}


