package com.nikapps.leitnerflashcardsmvp.presentation.flashcard.list

import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.presentation.base.BaseContract

interface FlashCardContract {

    interface View : BaseContract.BaseView {
        fun showProgressbar()
        fun hideProgressbar()
        fun updateList(data: List<FlashCard>)
        fun navigateToEdit(data: FlashCard)
        fun showToast(message: String)
        fun showEmptyView(show: Boolean)
        fun speak(what : String)
        fun showSuccessEditMessage()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun onItemClicked(data: FlashCard)
        fun editFlashCard(position: Int, flashCard: FlashCard)
        fun deleteFlashCard(position: Int, flashCard: FlashCard)
        fun onResult(result: Int)
        fun onPlayClicked(what: String)
    }
}