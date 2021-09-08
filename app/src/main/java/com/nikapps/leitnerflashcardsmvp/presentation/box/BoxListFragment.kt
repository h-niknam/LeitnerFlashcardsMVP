package com.nikapps.leitnerflashcardsmvp.presentation.box

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikapps.leitnerflashcardsmvp.R
import com.nikapps.leitnerflashcardsmvp.databinding.FragmentBoxListBinding
import com.nikapps.leitnerflashcardsmvp.presentation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BoxListFragment : Fragment(), BoxContract.View {

    lateinit var binding: FragmentBoxListBinding

    lateinit var adapter: BoxListAdapter

    @Inject
    lateinit var infoAdapter: InfoBoxAdapter

    @Inject
    lateinit var presenter: BoxPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoxListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BoxListAdapter {
            presenter.onBoxSelected(it + 1)
        }

        binding?.let {

            it.rvBox.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = this@BoxListFragment.adapter
            }

            it.rvInfo.apply {
                adapter = this@BoxListFragment.infoAdapter
            }

            it.fabAddFlashCard.setOnClickListener {
                presenter.onButtonAddClicked()
            }
            it.btnStudy.setOnClickListener {
                presenter.onStudyClicked()
            }
        }

        presenter.setView(this)
        presenter.onCreated()

    }

    override fun updateInfoBoxes(counts: List<Int>) {
        val list = listOf(
            InfoBox(R.string.info_toKnow, counts[0], R.color.color_to_know),
            InfoBox(R.string.info_known, counts[1], R.color.color_known),
            InfoBox(R.string.info_learned, counts[2], R.color.color_learned)
        )
        infoAdapter.setData(list)
    }

    override fun updateCardsCount(counts: List<Int>) {
        adapter.setCounts(counts)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun navigateToFlashCards(boxNumber: Int) {
//        val action =
//            BoxListFragmentDirections.actionBoxListFragmentToAddFlashCardFragment2(boxNumber)
//        findNavController().navigate(action)
        (activity as Navigator).let {
            it.navigateToFlashCardList(boxNumber)
        }

    }

    override fun navigateToStudy() {
        (activity as Navigator).let {
            it.navigateToStudy()
        }
    }

    override fun navigateToAddFlashCard() {
        (activity as Navigator).let {
            it.navigateToAddFlashCard()
        }
    }

    override fun showUpdateProgress(show: Boolean) {
        binding.updateProgress.visibility = if (show) View.VISIBLE else View.GONE
    }


    override fun setUpdateProgress(progress: Int) {
        binding.updateProgress.setProgress(progress)
    }
}