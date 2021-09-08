package com.nikapps.leitnerflashcardsmvp.presentation.box

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nikapps.leitnerflashcardsmvp.R
import com.nikapps.leitnerflashcardsmvp.databinding.RowBoxBinding
import com.nikapps.leitnerflashcardsmvp.util.Constants
import java.lang.Exception
import kotlin.collections.ArrayList

class BoxListAdapter(
    private val itemClickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<BoxListAdapter.BoxViewHolder>() {

    private var counts = ArrayList<Int>()

    fun setCounts(counts: List<Int>) {
        this.counts.clear()
        this.counts.addAll(counts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxViewHolder {
        return BoxViewHolder(
            RowBoxBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BoxViewHolder, position: Int) {

        holder.bind()
    }

    override fun getItemCount(): Int {
        return 5
    }


    inner class BoxViewHolder(val binding: RowBoxBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding?.apply {

                tvTitle.text =
                    "${root.context.getString(R.string.literal_box)} ${adapterPosition + 1}"

                val colorRes = when (adapterPosition) {
                    0 -> R.color.color_box_1
                    1 -> R.color.color_box_2
                    2 -> R.color.color_box_3
                    3 -> R.color.color_box_4
                    4 -> R.color.color_box_5

                    else -> R.color.color_box_1
                }
                val color = ContextCompat.getColor(root.context, colorRes)
                progressbar.setIndicatorColor(color)
                line.setBackgroundColor(color)

                val capacity = when (adapterPosition) {
                    0 -> Constants.BOX1_CAPACITY
                    1 -> Constants.BOX2_CAPACITY
                    2 -> Constants.BOX3_CAPACITY
                    3 -> Constants.BOX4_CAPACITY
                    4 -> Constants.BOX5_CAPACITY
                    else -> Constants.BOX1_CAPACITY
                }
                val count = try {
                    counts[adapterPosition]
                } catch (e: Exception) {
                    0
                }
                tvCount.text = "${count} / ${capacity}"

                val progress = Math.max(((count.toFloat() / capacity.toFloat()) * 100).toInt(), 2)
                progressbar.setProgress(progress.toInt())

                layout.setOnClickListener {
                    itemClickListener(adapterPosition)
                }
            }
        }
    }
}