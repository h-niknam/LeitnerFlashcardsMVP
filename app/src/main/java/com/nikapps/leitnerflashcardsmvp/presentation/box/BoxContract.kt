package com.nikapps.leitnerflashcardsmvp.presentation.box

import com.nikapps.leitnerflashcardsmvp.presentation.base.BaseContract

interface BoxContract {

    interface View : BaseContract.BaseView {

        fun updateInfoBoxes(counts: List<Int>)
        fun updateCardsCount(counts: List<Int>)
        fun navigateToFlashCards(boxNumber: Int)
        fun navigateToStudy()
        fun navigateToAddFlashCard()
        fun showUpdateProgress(show: Boolean)
        fun setUpdateProgress(progress: Int)
    }


    interface Presenter : BaseContract.BasePresenter {
        fun setView(view: View)
        fun onBoxSelected(boxNumber: Int)
        fun onStudyClicked()
        fun onButtonAddClicked()
    }

}