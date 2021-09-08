package com.nikapps.leitnerflashcardsmvp.presentation.words

import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.presentation.base.BaseContract

interface WordListContract {

    interface View : BaseContract.BaseView {
        fun showProgress(show: Boolean)
        fun submitList(data: List<FlashCard>)
        fun showToast(message: String)
        fun showSuccessAddMessage()
        fun speak(what: String)
    }

    interface Presenter : BaseContract.BasePresenter {
        fun addFlashCard(position: Int, flashCard: FlashCard)
        fun onPlayClicked(word : String)
    }
}