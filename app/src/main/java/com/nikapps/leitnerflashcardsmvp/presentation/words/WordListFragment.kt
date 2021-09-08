package com.nikapps.leitnerflashcardsmvp.presentation.words

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikapps.leitnerflashcardsmvp.R
import com.nikapps.leitnerflashcardsmvp.databinding.FragmentWordListBinding
import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.presentation.flashcard.list.FlashCardListAdapter
import com.nikapps.leitnerflashcardsmvp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WordListFragment : Fragment(), WordListContract.View {

    lateinit var binding: FragmentWordListBinding
    val args: WordListFragmentArgs by navArgs()

    lateinit var adapter: FlashCardListAdapter

    @Inject
    lateinit var presenter: WordListPresenter

    @Inject
    lateinit var textToSpeech: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this
        val bundle = Bundle()
        bundle.putString(Constants.KEY_CATEGORY_ID, args.categoryId)
        presenter.onCreated(bundle = bundle)

        adapter = FlashCardListAdapter(
            menuId = R.menu.words_items,
            onMenuItemClicked = { item, position, flashcard ->
                onWordMenuItemClicked(item, position, flashcard)
            },
            onPlayClicked = {
                presenter.onPlayClicked(it)
            }
        )

        binding?.apply {
            rvWords.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvWords.adapter = this@WordListFragment.adapter
        }
    }

    private fun onWordMenuItemClicked(item: MenuItem, position: Int, flashCard: FlashCard) {
        when (item.itemId) {
            R.id.word_item_add -> {
                presenter.addFlashCard(position, flashCard)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showProgress(show: Boolean) {
        binding?.progressbar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun submitList(data: List<FlashCard>) {
        adapter.submitList(data)
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showSuccessAddMessage() {
        Toast.makeText(context, context?.getString(R.string.msg_add_description), Toast.LENGTH_LONG).show()
    }

    override fun speak(what: String) {
        textToSpeech.speak(what, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}