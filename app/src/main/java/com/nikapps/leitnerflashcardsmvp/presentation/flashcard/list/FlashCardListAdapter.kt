package com.nikapps.leitnerflashcardsmvp.presentation.flashcard.list

import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikapps.leitnerflashcardsmvp.R
import com.nikapps.leitnerflashcardsmvp.databinding.RowFlashBinding
import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard

class FlashCardListAdapter(
    private val menuId: Int,
    private val onMenuItemClicked: (item: MenuItem, position: Int, flashCard: FlashCard) -> Unit,
    private val onPlayClicked : (what: String) -> Unit
) :
    ListAdapter<FlashCard, FlashCardListAdapter.ViewHolder>(DiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowFlashBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(
       val binding: RowFlashBinding
    ) : RecyclerView.ViewHolder(binding.root) {

         fun bind(data: FlashCard) {
            binding?.apply {
                tvWord.text = data.front
                tvTranslation.text = data.back
                tvDescription.text = data.description

                tvWord.setOnClickListener { onPlayClicked(tvWord.text.toString()) }
                tvDescription.setOnClickListener { onPlayClicked(tvDescription.text.toString()) }

                Glide.with(imgWord.context).load(data.thumbnail)
                    .placeholder(R.mipmap.placeholder_flashcard).into(imgWord)

                imgMore.setOnClickListener {
                    val popup = PopupMenu(root.context, imgMore)
                    popup.inflate(menuId);
                    popup.gravity = Gravity.RIGHT
                    popup.setOnMenuItemClickListener {
                        onMenuItemClicked(it, adapterPosition, currentList[adapterPosition])
                        true
                    }
                    popup.show()
                }
            }
        }

    }

    class DiffUtilsCallback() : DiffUtil.ItemCallback<FlashCard>() {
        override fun areItemsTheSame(oldItem: FlashCard, newItem: FlashCard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FlashCard,
            newItem: FlashCard
        ): Boolean {
            return oldItem == newItem
        }

    }

}