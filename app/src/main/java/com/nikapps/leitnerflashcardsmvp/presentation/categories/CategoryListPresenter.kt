package com.nikapps.leitnerflashcardsmvp.presentation.categories

import android.os.Bundle
import com.nikapps.leitnerflashcardsmvp.data.model.Resource
import com.nikapps.leitnerflashcardsmvp.domain.model.Category
import com.nikapps.leitnerflashcardsmvp.presentation.base.Presenter
import com.nikapps.leitnerflashcardsmvp.usecase.category.GetCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CategoryListPresenter @Inject constructor(
    private val getCategories: GetCategories,
    override val coroutineContext: CoroutineContext
) : Presenter<CategoryContract.View>(), CategoryContract.Presenter, CoroutineScope {

    val categories = getCategories()

    override fun onCategoryClicked(position: Int, category: Category) {
        view?.navigateToWordList(category)
    }

    override fun onCreated(bundle: Bundle?) {

        launch {
            categories.collect {
                when (it) {
                    is Resource.Loading -> {
                        view?.showProgressbar(true)
                    }
                    is Resource.Success -> {
                        view?.showProgressbar(false)

                        it.data?.let {
                            view?.submitCategoryList(it)
                        }

                    }
                    is Resource.Error -> {
                        view?.showProgressbar(false)
                    }
                }
            }
        }

    }
}