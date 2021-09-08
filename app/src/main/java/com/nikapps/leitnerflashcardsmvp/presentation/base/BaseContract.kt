package com.nikapps.leitnerflashcardsmvp.presentation.base

import android.os.Bundle

interface BaseContract {

    interface BasePresenter {
        fun onCreated(bundle: Bundle? = null)
        fun onDestroy()
    }

    interface BaseView

}