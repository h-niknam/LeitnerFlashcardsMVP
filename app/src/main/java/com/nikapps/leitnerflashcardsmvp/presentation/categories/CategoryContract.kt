package com.nikapps.leitnerflashcardsmvp.presentation.categories

import com.nikapps.leitnerflashcardsmvp.domain.model.Category
import com.nikapps.leitnerflashcardsmvp.presentation.base.BaseContract

interface CategoryContract {

    interface View : BaseContract.BaseView{
        fun showProgressbar(show: Boolean)
        fun submitCategoryList(list: List<Category>)
        fun navigateToWordList(category: Category)
    }

    interface Presenter : BaseContract.BasePresenter{
        fun onCategoryClicked(position: Int, category: Category)
    }
}