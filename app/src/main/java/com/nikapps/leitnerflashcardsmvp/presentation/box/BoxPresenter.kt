package com.nikapps.leitnerflashcardsmvp.presentation.box

import android.os.Bundle
import com.nikapps.leitnerflashcardsmvp.presentation.base.Presenter
import com.nikapps.leitnerflashcardsmvp.usecase.box.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class BoxPresenter @Inject constructor(
    private val updateFlashCardsFromServer: UpdateFlashCardsFromServer,
    private val boxFlashCount: BoxFlashCount,
    private val infoCount: BoxInfoCount,
    private val insertDeletedFlashCards: InsertDeletedFlashCards,
    private val updateUpdatedFlashCards: UpdateUpdatedFlashCards,
    private val syncDeletedFlashCards: SyncDeletedFlashCards,
    override val coroutineContext: CoroutineContext
) : Presenter<BoxContract.View>(), BoxContract.Presenter, CoroutineScope {

    val boxCountList = boxFlashCount()
    val infoCountList = infoCount()

    override fun setView(view: BoxContract.View) {
        this.view = view
    }

    override fun onCreated(bundle: Bundle?) {

        view?.apply {

            launch {
                updateFlashCardsFromServer()
            }

            launch {
                boxCountList.collect {
                    updateCardsCount(counts = it)
                }
            }

            launch {
                infoCountList.collect {
                    it?.let {
                        view?.updateInfoBoxes(it)
                    }
                }
            }

            launch {
                withContext(Dispatchers.Main) {
                    view?.showUpdateProgress(true)
                    view?.setUpdateProgress(0)
                }
                async {
                    insertDeletedFlashCards()
                }.await()
                withContext(Dispatchers.Main) {
                    view?.setUpdateProgress(33)
                }
                async {
                    updateUpdatedFlashCards()
                }.await()
                withContext(Dispatchers.Main) {
                    view?.setUpdateProgress(66)
                }
                async {
                    syncDeletedFlashCards()
                }.await()
                withContext(Dispatchers.Main) {
                    view?.setUpdateProgress(100)
                    view?.showUpdateProgress(false)
                }
            }
        }

    }

    override fun onBoxSelected(boxNumber: Int) {
        view?.navigateToFlashCards(boxNumber)
    }

    override fun onStudyClicked() {
        view?.navigateToStudy()
    }

    override fun onButtonAddClicked() {
        view?.navigateToAddFlashCard()
    }
}