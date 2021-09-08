package com.nikapps.leitnerflashcardsmvp.presentation.box

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nikapps.leitnerflashcardsmvp.databinding.RowInfoBinding
import javax.inject.Inject

class InfoBoxAdapter @Inject constructor() : RecyclerView.Adapter<InfoBoxAdapter.ViewHolder>() {

    private var data: List<InfoBox> = emptyList()
    public fun setData(data: List<InfoBox> ){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val d = data[position]
        holder.bind(d)
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    inner class ViewHolder(
        private val binding: RowInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: InfoBox) {
            binding?.apply {

                val ctx = root.context

                tvTitle.text = ctx.getString(data.titleRes)
                tvCount.text = data.count.toString()

                tvCount.setTextColor(ContextCompat.getColor(ctx, data.colorRes))
                tvTitle.setTextColor(ContextCompat.getColor(ctx, data.colorRes))
            }
        }
    }

}

data class InfoBox(
    val titleRes: Int,
    var count: Int,
    val colorRes: Int
)