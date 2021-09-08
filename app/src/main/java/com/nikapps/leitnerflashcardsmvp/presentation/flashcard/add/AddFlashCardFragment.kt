package com.nikapps.leitnerflashcardsmvp.presentation.flashcard.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nikapps.leitnerflashcardsmvp.databinding.FragmentAddFlashCardBinding
import com.nikapps.leitnerflashcardsmvp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddFlashCardFragment : Fragment(), AddContract.View {

    private lateinit var binding: FragmentAddFlashCardBinding
    val args: AddFlashCardFragmentArgs by navArgs()

    @Inject
    lateinit var presenter: AddPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFlashCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        presenter.view = this

        val data = args.flashCard
        data?.let {
            binding?.etFront.setText(it.front)
            binding?.etBack.setText(it.back)
            binding?.etDescription.setText(it.description)
        }

        binding?.let {
            it.btnSave.setOnClickListener {
                presenter.onAddButtonClicked(
                    data,
                    binding.etFront.text.toString(),
                    binding.etBack.text.toString(),
                    binding.etDescription.text.toString()
                )
            }
        }
    }

    override fun showFieldError(message: String) {
        binding?.let {
            it.etBack.setError(message)
            it.etFront.setError(message)
        }
    }

    override fun showToast() {
        Toast.makeText(context, "Unknown Error", Toast.LENGTH_SHORT).show()
    }

    override fun finish(result: Int) {

        val bundle = Bundle()
        bundle.putInt(Constants.KEY_RESULT_ADD_EDIT, result)
        setFragmentResult(Constants.KEY_REQUEST_ADD_EDIT, bundle)

        findNavController().popBackStack()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}