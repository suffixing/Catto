package ahmed.elabdullah.catto.viewmodels

import ahmed.elabdullah.catto.catapi.data.CatApiService.Cat
import ahmed.elabdullah.catto.catapi.data.CatRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * The ViewModel for the Cat API.
 */
class CatViewModel : ViewModel() {

    // Internal mutable LiveData for storing the list of cats.
    private val _cats = MutableLiveData<List<Cat>>()

    // Public LiveData for observing the list of cats.
    val cats: LiveData<List<Cat>> = _cats

    /**
     * Search for cats using the Cat API.
     */
    fun searchCats() {
        // Use viewModelScope to launch a coroutine in the context of the ViewModel.
        viewModelScope.launch {
            try {
                // Create a CatRepository instance.
                val catApi = CatRepository()

                // Search for cats with the CatRepository instance and update the internal LiveData.
                _cats.value = catApi.searchCats(null, CAT_LIMIT).map { cat -> cat.toCatModel() }
            } catch (e: Exception) {
                // Log any errors.
                Log.e(TAG, "Error searching cats: ${e.message}")
            }
        }
    }

    /**
     * Extension function for converting a [CatApiService.Cat] to a [Cat] model.
     */
    private fun Cat.toCatModel(): Cat {
        return Cat(id, url, width, height, breeds)
    }

    companion object {
        private const val TAG = "CatViewModel"
        private const val CAT_LIMIT = 10
    }
}
