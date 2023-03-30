package ahmed.elabdullah.catto.ui.activites

import ahmed.elabdullah.catto.R
import ahmed.elabdullah.catto.adapters.CatAdapter
import ahmed.elabdullah.catto.catapi.data.CatApiService
import ahmed.elabdullah.catto.databinding.ActivityMainBinding
import ahmed.elabdullah.catto.ui.utils.LastItemMarginDecoration
import ahmed.elabdullah.catto.viewmodels.CatViewModel
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity(), CatAdapter.OnItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    // Declare variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CatViewModel
    private lateinit var adapter: CatAdapter
    private lateinit var layoutManager: StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up the UI views
        setupViews()
        // Set up the view model
        setupViewModel()
        // Set up the recycler view
        setupRecyclerView()
    }

    // Set up the UI views
    private fun setupViews() {
        // Inflate the layout using DataBindingUtil
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Set up the SwipeRefreshLayout and set it to refreshing state
        binding.swipeRefreshLayout.isRefreshing = true
        binding.swipeRefreshLayout.setOnRefreshListener(this)
    }

    // Set up the view model
    private fun setupViewModel() {
        // Create the view model using ViewModelProvider
        viewModel = ViewModelProvider(this)[CatViewModel::class.java]
        // Call the searchCats function to fetch cat data
        viewModel.searchCats()
        // Observe changes to the cat data and update the adapter accordingly
        viewModel.cats.observe(this) { cats ->
            adapter.setData(cats)
            // Set the SwipeRefreshLayout to not refreshing state
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    // Set up the recycler view
    private fun setupRecyclerView() {
        // Create the adapter with an empty list
        adapter = CatAdapter(emptyList())
        // Create a StaggeredGridLayoutManager with 2 columns and vertical orientation
        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        // Set the layout manager and adapter on the recycler view
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        // Add an item decoration to add a margin to the last item
        binding.recyclerView.addItemDecoration(LastItemMarginDecoration(resources.getDimensionPixelSize(R.dimen.margin_normal)))
        // Set the item click listener on the adapter
        adapter.setOnItemClickListener(this)
    }

    // Callback function for when the SwipeRefreshLayout is refreshed
    override fun onRefresh() {
        // Call the searchCats function to fetch new cat data
        viewModel.searchCats()
    }

    // Callback function for when an item in the recycler view is clicked
    override fun onItemClick(cat: CatApiService.Cat) {
        // Show a dialog with a preview of the clicked cat
        showCatPreviewDialog(cat)
    }

    private fun showCatPreviewDialog(cat: CatApiService.Cat) {
        // Create a new Dialog instance
        val dialog = Dialog(this)

        // Set the content view of the dialog to the cat preview layout
        dialog.setContentView(R.layout.dialog_cat_preview)

        // Find the ImageView in the layout and load the cat image using Glide
        val catImage: ShapeableImageView = dialog.findViewById(R.id.cat_image)
        Glide.with(this).load(cat.url).into(catImage)

        // Set the width and height of the ImageView to the minimum of the cat's actual size or a predetermined maximum size
        val layoutParams = catImage.layoutParams
        layoutParams.width = cat.width.coerceAtMost(700)
        layoutParams.height = cat.height.coerceAtMost(900)
        catImage.layoutParams = layoutParams

        // Set the background of the dialog window to be transparent and add an animation to the dialog
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        // Show the dialog to the user
        dialog.show()
    }

}