package com.nikapps.leitnerflashcardsmvp.presentation.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.nikapps.leitnerflashcardsmvp.databinding.FragmentCategoriesBinding
import com.nikapps.leitnerflashcardsmvp.domain.model.Category
import com.nikapps.leitnerflashcardsmvp.presentation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment(), CategoryContract.View {


    lateinit var binding: FragmentCategoriesBinding
    lateinit var adapter: CategoryAdapter

    @Inject
    lateinit var presenter: CategoryListPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this
        presenter.onCreated()

        adapter = CategoryAdapter { position, category ->
            presenter.onCategoryClicked(position, category)
        }
        binding.apply {
            rvCategories.adapter = adapter
            rvCategories.layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun showProgressbar(show: Boolean) {
        binding.progressbar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun submitCategoryList(list: List<Category>) {
        adapter.submitList(list)
    }

    override fun navigateToWordList(category: Category) {
        (activity as Navigator).let {
            it.navigateToCategoryWords(category.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}