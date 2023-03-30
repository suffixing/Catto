/**

Adapter class for the RecyclerView that shows the list of cats.
@param cats The list of cats to display in the RecyclerView.
 */
package ahmed.elabdullah.catto.adapters

import ahmed.elabdullah.catto.R
import ahmed.elabdullah.catto.catapi.data.CatApiService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CatAdapter(private var cats: List<CatApiService.Cat>) :
    RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_item, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]
        holder.bind(cat)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(cat)
        }
    }

    override fun getItemCount(): Int {
        return cats.size
    }

    fun setData(catsData: List<CatApiService.Cat>) {
        cats = catsData
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(cat: CatApiService.Cat)
    }

    inner class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val catImage: ImageView = itemView.findViewById(R.id.cat_image)

        fun bind(cat: CatApiService.Cat) {
            Glide.with(itemView)
                .load(cat.url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(catImage)

            val layoutParams = catImage.layoutParams
            layoutParams.height = cat.height.coerceAtMost(600)
            catImage.layoutParams = layoutParams
        }
    }
}