package com.nikapps.leitnerflashcardsmvp.presentation.flashcard.add

import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.presentation.base.BaseContract

interface AddContract {

    interface View : BaseContract.BaseView {

        fun showFieldError(message: String)
        fun showToast()
        fun finish(result: Int)
    }

    interface Presenter : BaseContract.BasePresenter {
        fun onAddButtonClicked(data: FlashCard?, front: String, back: String, description: String)
    }
}