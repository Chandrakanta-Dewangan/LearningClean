package com.learning.assignment.presentation.ui.main.details

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.learning.assignment.R
import com.learning.assignment.launchFragmentInHiltContainer
import com.learning.assignment.ui.main.details.CakeDetailsJavaFragment
import com.learning.assignment.utils.FakeDataGenerator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito


@HiltAndroidTest
class CakesDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_title_and_detail_view() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<CakeDetailsJavaFragment>(Bundle().apply {
            putSerializable("cake", FakeDataGenerator.getFakeCakes()[0])
        }) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.titleTv)).check(matches(withText("Lemon cheesecake 1")))
        onView(withId(R.id.detailsTv)).check(matches(withText("A cheesecake made of lemon")))
    }
}