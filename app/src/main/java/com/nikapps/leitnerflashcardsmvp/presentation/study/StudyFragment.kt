package com.nikapps.leitnerflashcardsmvp.presentation.study

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.nikapps.leitnerflashcardsmvp.R
import com.nikapps.leitnerflashcardsmvp.databinding.FragmentStudyBinding
import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StudyFragment : Fragment(), StudyContract.View {

    private lateinit var binding: FragmentStudyBinding
    private val navArgs: StudyFragmentArgs by navArgs()

    @Inject
    lateinit var presenter: StudyPresenter

    @Inject
    lateinit var textToSpeech: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this
        val bundle = Bundle()
        bundle.putInt(Constants.KEY_BOX_NUMBER, navArgs.boxNumber)
        presenter.onCreated(bundle)
        binding?.apply {
            btnNext.setOnClickListener { presenter.onKnowClicked() }
            btnBack.setOnClickListener { presenter.onRetryClicked() }
            imageDelete.setOnClickListener { presenter.onDeleteClicked() }
            cardLayout.setOnClickListener { presenter.onFlipClicked() }
            tvFront.setOnClickListener { presenter.onPlayClicked(tvFront.text.toString()) }
            tvFrontRepeat.setOnClickListener { tvFrontRepeat.text.toString() }
            tvDescription.setOnClickListener { tvDescription.text.toString() }
        }

    }

    override fun moveToNext(flashCard: FlashCard, position: Int, count: Int) {
        YoYo.with(Techniques.SlideInRight)
            .duration(300)
            .playOn(binding.cardLayout);

        setFlashcard(flashCard, position, count)
    }


    private fun setFlashcard(flashCard: FlashCard, position: Int, count: Int) {
        binding?.apply {
            tvFront.text = flashCard.front
            tvFrontRepeat.text = flashCard.front
            tvBack.text = flashCard.back
            tvDescription.text = flashCard.description ?: ""
            tvPhonetics.text = flashCard.phonetics ?: ""

            imgThumb.visibility =
                if (flashCard.thumbnail?.length ?: 0 > 0) View.VISIBLE else View.GONE

            context?.let { Glide.with(it).load(flashCard.thumbnail).into(imgThumb) }

            //header
            tvBoxNumber.text = "${context?.getString(R.string.literal_box)} ${flashCard.boxNumber}"
            tvCounter.text = "${position + 1} / ${count}"
        }
    }

    override fun flipFlashCard(flashCard: FlashCard, isFront: Boolean) {
        binding?.apply {
            flip(isFront)
        }
    }

    private fun flip(frontSide: Boolean) {
        binding?.apply {

            YoYo.with(Techniques.FlipInY)
                .duration(500)
                .playOn(card);

            if (frontSide) {
                back.visibility = View.GONE
                front.visibility = View.VISIBLE
            } else {
                back.visibility = View.VISIBLE
                front.visibility = View.GONE
            }
        }
    }

    override fun updateCountTextViews(count: Int, all: Int) {
        binding?.apply {
            tvCounter.text = "${count} / ${all}"
        }
    }

    override fun finishStudy() {
        Toast.makeText(context, context?.getString(R.string.msg_finish_study), Toast.LENGTH_LONG)
            .show()
        findNavController().popBackStack()
    }

    override fun speak(what: String) {
        textToSpeech.speak(what, TextToSpeech.QUEUE_FLUSH, null, null)
    }


}