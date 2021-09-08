package com.nikapps.leitnerflashcardsmvp.presentation.words

import android.os.Bundle
import com.nikapps.leitnerflashcardsmvp.data.model.Resource
import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.presentation.base.Presenter
import com.nikapps.leitnerflashcardsmvp.usecase.category.GetCategoryWords
import com.nikapps.leitnerflashcardsmvp.usecase.flashcard.AddFlashCard
import com.nikapps.leitnerflashcardsmvp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class WordListPresenter @Inject constructor(
    private val getCategoryWords: GetCategoryWords,
    private val addFlashCard: AddFlashCard,
    override val coroutineContext: CoroutineContext
) : Presenter<WordListContract.View>(), WordListContract.Presenter, CoroutineScope {

    override fun addFlashCard(position: Int, flashCard: FlashCard) {

        launch {
            addFlashCard(flashCard)
            withContext(Dispatchers.Main) {
                view?.showSuccessAddMessage()
            }
        }

    }

    override fun onPlayClicked(word: String) {
        view?.speak(word)
    }

    override fun onCreated(bundle: Bundle?) {
        val catId = bundle?.getString(Constants.KEY_CATEGORY_ID) ?: ""

        val words = getCategoryWords(catId)

        launch {
            words.collect {
                when (it) {
                    is Resource.Loading -> {
                        view?.showProgress(true)
                    }
                    is Resource.Success -> {
                        view?.showProgress(false)
                        it.data?.let {
                            view?.submitList(it)
                        }
                    }
                    is Resource.Error -> {
                        view?.showProgress(false)
                    }
                }
            }
        }


    }
}