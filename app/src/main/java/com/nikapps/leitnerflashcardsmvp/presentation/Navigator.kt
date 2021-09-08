package com.nikapps.leitnerflashcardsmvp.presentation

import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard

interface Navigator {
    fun navigateToAddFlashCard()
    fun navigateToEditFlashCard(data: FlashCard)
    fun navigateToStudy()
    fun navigateToFlashCardList(boxNumber : Int)
    fun navigateToCategoryWords(CategoryId: String)
}