package com.nikapps.leitnerflashcardsmvp.presentation.home

import android.os.Bundle
import com.nikapps.leitnerflashcardsmvp.presentation.base.Presenter
import com.nikapps.leitnerflashcardsmvp.util.Constants
import javax.inject.Inject

class HomePresenter @Inject constructor(): Presenter<HomeContract.View>(), HomeContract.Presenter {

    override fun onResult(result: Int) {
        when(result){
            Constants.RESULT_ADD_FLASH_CARD -> view?.showSuccessAddMessage()
            Constants.RESULT_EDIT_FLASH_CARD -> view?.showSuccessEditMessage()
        }
    }

    override fun onCreated(bundle: Bundle?) {

    }

}