package com.nikapps.leitnerflashcardsmvp.presentation.main

import javax.inject.Inject

class MainPresenter @Inject constructor(

) : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun onDestroy() {
        view = null
    }
}