package com.nikapps.leitnerflashcardsmvp.presentation.home

import com.nikapps.leitnerflashcardsmvp.presentation.base.BaseContract

interface HomeContract {
    interface View : BaseContract.BaseView {
        fun showSuccessAddMessage()
        fun showSuccessEditMessage()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun onResult(result: Int)
    }
}