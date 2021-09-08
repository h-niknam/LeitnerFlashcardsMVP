package com.nikapps.leitnerflashcardsmvp.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nikapps.leitnerflashcardsmvp.R
import com.nikapps.leitnerflashcardsmvp.databinding.FragmentHomeBinding
import com.nikapps.leitnerflashcardsmvp.util.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContract.View {

    lateinit var binding: FragmentHomeBinding
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
//    lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this

        navHostFragment = childFragmentManager.findFragmentById(R.id.fragment32) as NavHostFragment

        bottomNavView = binding.root.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavView.setupWithNavController(navHostFragment.navController)

        setFragmentResultListener(Constants.KEY_REQUEST_ADD_EDIT) { str, bundle ->
            val result = bundle.getInt(Constants.KEY_RESULT_ADD_EDIT)
            presenter.onResult(result)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showSuccessAddMessage() {
        Toast.makeText(context, context?.getString(R.string.msg_add_description), Toast.LENGTH_LONG).show()

    }

    override fun showSuccessEditMessage() {
        Toast.makeText(context, context?.getString(R.string.msg_edit_description), Toast.LENGTH_LONG).show()
    }

}