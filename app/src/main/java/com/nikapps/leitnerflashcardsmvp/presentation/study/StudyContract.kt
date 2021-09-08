package com.nikapps.leitnerflashcardsmvp.presentation.study

import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.presentation.base.BaseContract

interface StudyContract {

    interface View : BaseContract.BaseView {
        fun moveToNext(flashCard: FlashCard, position:Int, size: Int)
        fun flipFlashCard(flashCard: FlashCard, isFront: Boolean)
        fun updateCountTextViews(counter: Int, all: Int)
        fun finishStudy()
        fun speak(what : String)
    }

    interface Presenter : BaseContract.BasePresenter {
        fun onKnowClicked()
        fun onRetryClicked()
        fun onFlipClicked()
        fun onPlayClicked(word: String)
        fun onDeleteClicked()
    }

}