package com.nikapps.leitnerflashcardsmvp.presentation.flashcard.list

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikapps.leitnerflashcardsmvp.R
import com.nikapps.leitnerflashcardsmvp.databinding.FragmentFlashCardListBinding
import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FlashCardListFragment : Fragment(), FlashCardContract.View {

    lateinit var binding: FragmentFlashCardListBinding
    private val args: FlashCardListFragmentArgs by navArgs()

    @Inject
    lateinit var presenter: FlashCardPresenter

    @Inject
    lateinit var textToSpeech: TextToSpeech

    val adapter: FlashCardListAdapter by lazy {
        FlashCardListAdapter(
            menuId = R.menu.flashcard_items,
            onMenuItemClicked = { item, position, flashcard ->
                onWordMenuItemClicked(item, position, flashcard)
            },
            onPlayClicked = {
                presenter.onPlayClicked(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFlashCardListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this
        val bundle = Bundle()
        bundle.putInt(Constants.KEY_BOX_NUMBER, args.boxNumber)
        presenter.onCreated(bundle)
        binding?.let {

            it.recyclerView.apply {
                adapter = this@FlashCardListFragment.adapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        setFragmentResultListener(Constants.KEY_REQUEST_ADD_EDIT) { str, bundle ->
            val result = bundle.getInt(Constants.KEY_RESULT_ADD_EDIT)
            presenter.onResult(result)
        }
    }

    private fun onWordMenuItemClicked(item: MenuItem, position: Int, flashCard: FlashCard) {
        when (item.itemId) {
            R.id.items_delete_flashcard -> {
                presenter.deleteFlashCard(position, flashCard)
            }
            R.id.items_edit_flashcard -> {
                presenter.editFlashCard(position, flashCard)
            }
        }
    }

    override fun showProgressbar() {

        binding?.apply {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressbar() {
        binding?.apply {
            progressBar.visibility = View.GONE
        }
    }

    override fun updateList(data: List<FlashCard>) {
        binding?.let {
            adapter.submitList(data)
        }
    }

    override fun navigateToEdit(data: FlashCard) {
        val action =
            FlashCardListFragmentDirections.actionFlashCardListFragmentToAddFlashCardFragment(data)
        findNavController().navigate(action)
    }


    override fun showEmptyView(show: Boolean) {

        binding?.apply {
            if (show) {
                imgEmpty.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                imgEmpty.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun speak(what: String) {
        textToSpeech.speak(what, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun showSuccessEditMessage() {
        Toast.makeText(context, context?.getString(R.string.msg_edit_description), Toast.LENGTH_LONG).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}