package com.nikapps.leitnerflashcardsmvp.presentation.study

import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.presentation.base.Presenter
import com.nikapps.leitnerflashcardsmvp.usecase.common.DeleteFlashCard
import com.nikapps.leitnerflashcardsmvp.usecase.flashcard.UpdateFlashCard
import com.nikapps.leitnerflashcardsmvp.usecase.study.GetStudyFlashCards
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class StudyPresenter @Inject constructor(
    private val textToSpeech: TextToSpeech,
    private val getStudyFlashCards: GetStudyFlashCards,
    private val updateFlashCard: UpdateFlashCard,
    private val deleteFlashCard: DeleteFlashCard,
    override val coroutineContext: CoroutineContext
) : Presenter<StudyContract.View>(), StudyContract.Presenter, CoroutineScope {

    var flashCards: List<FlashCard> = emptyList()

    private var currentIndex = -1
    private var isFront: Boolean = true

    override fun onKnowClicked() {
        val index = currentIndex
        flashCards[index].boxNumber = flashCards[index].boxNumber + 1
        flashCards[index].lastDate = Date()

        launch {
            updateFlashCard(flashCards[index])
        }

        loadNext()

    }

    override fun onRetryClicked() {

        val index = currentIndex
        flashCards[index].boxNumber = 1
        flashCards[index].lastDate = Date()

        launch {
            updateFlashCard(flashCards[index])
        }
        loadNext()
    }

    private fun loadNext() {
        if (currentIndex < flashCards.size - 1) {
            isFront = true
            view?.moveToNext(flashCards[++currentIndex], currentIndex, flashCards.size)
        } else {
            view?.finishStudy()
        }
    }

    override fun onFlipClicked() {
        isFront = !isFront
        view?.flipFlashCard(flashCards[currentIndex], isFront)
    }

    override fun onPlayClicked(word: String) {

    }

    override fun onDeleteClicked() {
        val flashcardToDelete = flashCards[currentIndex]
        launch {

            deleteFlashCard(flashcardToDelete)

            withContext(Dispatchers.Main) {
                loadNext()
            }
        }
    }

    override fun onCreated(bundle: Bundle?) {

        flashCards = getStudyFlashCards()
        if (currentIndex == -1) {
            loadNext()
        }

    }
}