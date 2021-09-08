package com.nikapps.leitnerflashcardsmvp.presentation.flashcard.list

import android.os.Bundle
import com.nikapps.leitnerflashcardsmvp.data.model.Resource
import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.presentation.base.Presenter
import com.nikapps.leitnerflashcardsmvp.usecase.common.DeleteFlashCard
import com.nikapps.leitnerflashcardsmvp.usecase.list.GetFlashCards
import com.nikapps.leitnerflashcardsmvp.util.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FlashCardPresenter @Inject constructor(
    private val getFlashCards: GetFlashCards,
    private val deleteFlashCard: DeleteFlashCard,
    override val coroutineContext: CoroutineContext
) : Presenter<FlashCardContract.View>(), FlashCardContract.Presenter, CoroutineScope {

    var data: Flow<Resource<List<FlashCard>>> = emptyFlow()

    override fun onCreated(bundle: Bundle?) {
        val boxNumber: Int = bundle?.getInt(Constants.KEY_BOX_NUMBER) ?: 0
        data = getFlashCards(boxNumber)

        view?.showProgressbar()
        view?.let {
            launch {
                data.collect {
                    if (it.data?.size == 0) {
                        view?.showEmptyView(true)
                    } else {
                        view?.showEmptyView(false)
                    }
                    when (it) {
                        is Resource.Loading -> {
                            view?.showProgressbar()
                            it.data?.let {
                                view?.updateList(it)
                            }
                        }
                        is Resource.Error -> {
                            view?.showToast(it.message ?: "")
                            view?.hideProgressbar()
                        }
                        is Resource.Success -> {
                            view?.hideProgressbar()
                            it.data?.let {
                                view?.updateList(it)
                            }
                        }
                    }

                }
            }
        }
    }

    override fun onItemClicked(data: FlashCard) {
        view?.navigateToEdit(data)
    }

    override fun editFlashCard(position: Int, flashCard: FlashCard) {
        view?.navigateToEdit(flashCard)
    }

    override fun deleteFlashCard(position: Int, flashCard: FlashCard) {
        launch {
            deleteFlashCard(flashCard)
        }
    }

    override fun onResult(result: Int) {
        when(result){
            Constants.RESULT_EDIT_FLASH_CARD -> view?.showSuccessEditMessage()
        }
    }

    override fun onPlayClicked(what: String) {
        view?.speak(what)
    }

}