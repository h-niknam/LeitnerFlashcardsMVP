package com.nikapps.leitnerflashcardsmvp.presentation.base

abstract class Presenter<T : BaseContract.BaseView>() : BaseContract.BasePresenter {

    var view: T? = null

    override fun onDestroy() {
        view = null
    }
}