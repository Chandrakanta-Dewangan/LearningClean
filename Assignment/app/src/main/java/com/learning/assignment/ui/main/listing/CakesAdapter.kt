package com.learning.assignment.ui.main.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.learning.domain.model.Cakes
import com.learning.assignment.R
import com.learning.assignment.databinding.ItemAdBinding


class OnClickListener(val clickListener: (cakes: Cakes, imageView: ImageView) -> Unit) {
    fun onClick(cakes: Cakes, imageView: ImageView) = clickListener(cakes, imageView)
}

class CakesAdapter(
    private val context: Context,
    private val clickListener: OnClickListener
) : RecyclerView.Adapter<CakesAdapter.ViewHolder>() {

    private var cakes = mutableListOf<Cakes>()

    fun setCakes(cakesList: List<Cakes>) {
        cakes.clear()
        cakes.addAll(cakesList)
        notifyDataSetChanged()
    }

    fun getCakesAtPosition(position: Int): Cakes = cakes[position]

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAdBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val ad = cakes[position]
        holder.bind(cakes[position])
    }

    override fun getItemCount(): Int {
        return cakes.size
    }

    inner class ViewHolder(private val binding: ItemAdBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cakes: Cakes) {
            binding.imageView.transitionName = cakes.title
            binding.cardView.setOnClickListener {
                clickListener.onClick(cakes, binding.imageView)
            }
            binding.titleTv.text = cakes.title
            binding.detailsTv.text = cakes.desc

            val drawable = CircularProgressDrawable(context).apply {
                setColorSchemeColors(
                    R.color.purple_200,
                    R.color.purple_700,
                    R.color.purple_500
                )
                centerRadius = 30f
                strokeWidth = 5f
                start()
            }

            Glide
                .with(context).asBitmap()
                .load(cakes.image.toUri().buildUpon().scheme("https").build())
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .placeholder(drawable)
                .error(R.drawable.ic_default_ad)
                .into(binding.imageView)
        }
    }
}