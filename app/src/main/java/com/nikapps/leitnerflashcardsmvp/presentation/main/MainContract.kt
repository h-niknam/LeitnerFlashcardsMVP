package com.nikapps.leitnerflashcardsmvp.presentation.main

interface MainContract {

    interface View{
        fun navigateToAddFlashCard()
    }
    interface Presenter{
        fun onDestroy()
    }

}