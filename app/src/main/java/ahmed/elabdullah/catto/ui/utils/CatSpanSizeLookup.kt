package ahmed.elabdullah.catto.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * ItemDecoration class used to add a margin to the last item in a staggered grid layout.
 *
 * @property spacing The spacing to be added to the right of the last item.
 */
class LastItemMarginDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    /**
     * Adds a right margin to the last item in the staggered grid layout.
     *
     * @param outRect The [Rect] representing the margins of the current item.
     * @param view The current item's [View].
     * @param parent The [RecyclerView] containing the items.
     * @param state The current state of the [RecyclerView].
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        // Get the layout params of the current item
        val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams

        // Get the absolute position of the current item
        val position = layoutParams.absoluteAdapterPosition

        // Check if the current item is the last item in the RecyclerView
        if (position == parent.adapter?.itemCount?.minus(1)) {
            outRect.right = spacing
        }
    }
}
