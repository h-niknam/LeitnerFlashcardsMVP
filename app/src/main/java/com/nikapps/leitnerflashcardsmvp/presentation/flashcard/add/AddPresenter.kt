package com.nikapps.leitnerflashcardsmvp.presentation.flashcard.add

import android.os.Bundle
import android.text.TextUtils
import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.presentation.base.Presenter
import com.nikapps.leitnerflashcardsmvp.usecase.flashcard.AddFlashCard
import com.nikapps.leitnerflashcardsmvp.usecase.flashcard.UpdateFlashCard
import com.nikapps.leitnerflashcardsmvp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AddPresenter @Inject constructor(
    private val addFlashCard: AddFlashCard,
    private val updateFlashCard: UpdateFlashCard,
    override val coroutineContext: CoroutineContext
) : Presenter<AddContract.View>(), AddContract.Presenter, CoroutineScope {

    override fun onAddButtonClicked(
        data: FlashCard?,
        front: String,
        back: String,
        description: String
    ) {
        view?.let {
            if (TextUtils.isEmpty(front) || TextUtils.isEmpty(back)) {
                it.showFieldError("Fields can not be empty")
            } else {
                if (data != null) {
                    updateFlashCard(data, front, back, description)
                } else {
                    addFlashCard(front, back, description)
                }
            }
        }

    }

    override fun onCreated(bundle: Bundle?) {}

    private fun addFlashCard(front: String, back: String, description: String) {

        val data =
            FlashCard(front = front, back = back, description = description, thumbnail = null)
        launch {
            addFlashCard(data)
            withContext(Dispatchers.Main) {
                view?.finish(Constants.RESULT_ADD_FLASH_CARD)
            }
        }
    }

    private fun updateFlashCard(data: FlashCard, front: String, back: String, description: String) {
        data.front = front
        data.back = back
        data.description = description
        launch {
            updateFlashCard(data)
            withContext(Dispatchers.Main) {
                view?.finish(Constants.RESULT_EDIT_FLASH_CARD)
            }
        }
    }

}