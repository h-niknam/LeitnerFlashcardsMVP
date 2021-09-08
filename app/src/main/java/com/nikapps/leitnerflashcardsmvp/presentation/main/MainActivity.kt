package com.nikapps.leitnerflashcardsmvp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.nikapps.leitnerflashcardsmvp.R
import com.nikapps.leitnerflashcardsmvp.domain.model.FlashCard
import com.nikapps.leitnerflashcardsmvp.presentation.Navigator
import com.nikapps.leitnerflashcardsmvp.presentation.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator {

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment2)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun navigateToAddFlashCard() {
        val action = HomeFragmentDirections.actionHomeFragmentToAddFlashCardFragment()
        navController.navigate(action)
    }

    override fun navigateToEditFlashCard(data: FlashCard) {
        val action = HomeFragmentDirections.actionHomeFragmentToAddFlashCardFragment(data)
        navController.navigate(action)
    }

    override fun navigateToStudy() {
        val action = HomeFragmentDirections.actionHomeFragmentToStudyFragment()
        navController.navigate(action)
    }

    override fun navigateToFlashCardList(boxNumber: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToFlashCardListFragment(boxNumber)
        navController.navigate(action)
    }

    override fun navigateToCategoryWords(categoryId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToWordListFragment(categoryId = categoryId)
        navController.navigate(action)
    }

}