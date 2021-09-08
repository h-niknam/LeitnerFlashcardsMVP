package com.nikapps.leitnerflashcardsmvp.presentation.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikapps.leitnerflashcardsmvp.R
import com.nikapps.leitnerflashcardsmvp.databinding.RowCategoryBinding
import com.nikapps.leitnerflashcardsmvp.domain.model.Category
import com.nikapps.leitnerflashcardsmvp.domain.model.CategoryLevel

class CategoryAdapter(private val onItemClicked: (Int, Category) -> Unit) :
    ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffUtilsCallback()) {

    class DiffUtilsCallback() : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(
        private val binding: RowCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Category) {
            binding?.apply {
                root.setOnClickListener {
                    onItemClicked(adapterPosition, currentList[adapterPosition])
                }
                Glide.with(root.context).load(data.thumb).into(imgThunb)
                tvTitle.text = data.title


                val backgroundRes = when (data.level) {
                    CategoryLevel.basic -> R.drawable.bg_label_basic
                    CategoryLevel.intermediate -> R.drawable.bg_label_intermediate
                    CategoryLevel.advanced -> R.drawable.bg_label_advanced
                }
                val levelNameRes = when (data.level) {
                    CategoryLevel.basic -> R.string.level_basic
                    CategoryLevel.intermediate -> R.string.level_intermediate
                    CategoryLevel.advanced -> R.string.level_advanced
                }

                tvLevel.text = tvLevel.context.getString(levelNameRes)
                tvLevel.background = tvLevel.context.getDrawable(backgroundRes)
            }

        }

    }
}