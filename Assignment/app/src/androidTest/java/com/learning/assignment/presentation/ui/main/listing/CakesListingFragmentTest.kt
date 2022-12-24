package com.learning.assignment.presentation.ui.main.listing

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.learning.assignment.R
import com.learning.assignment.databinding.ListingFragmentBinding
import com.learning.assignment.launchFragmentInHiltContainer
import com.learning.assignment.ui.main.listing.CakeListingFragment
import com.learning.assignment.ui.main.listing.CakeListingFragmentDirections
import com.learning.assignment.ui.main.listing.CakesAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class CakesListingFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `scroll_the_cake_list`() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<CakeListingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Thread.sleep(10000L)

        Espresso.onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    10
                )
            )

        Espresso.onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    0
                )
            )
    }


    @Test
    fun `click_on_first_item`(): Unit = runBlocking {
        val navController = Mockito.mock(NavController::class.java)

        lateinit var viewBindingAd: ListingFragmentBinding

        launchFragmentInHiltContainer<CakeListingFragment> {
            Navigation.setViewNavController(requireView(), navController)
            viewBindingAd = viewBinding
        }

        delay(10000L)

        Espresso.onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ClickOnButtonView()
                )
            )

        val cakes = (viewBindingAd.list.adapter as CakesAdapter).getCakesAtPosition(0)

        verify(navController).navigate(
            CakeListingFragmentDirections.actionCakeListingFragmentToCakeDetailsJavaFragment(cakes),
        )
    }

    @Test
    fun `click_on_last_item`(): Unit = runBlocking {
        val navController = Mockito.mock(NavController::class.java)

        lateinit var viewBindingAd: ListingFragmentBinding

        launchFragmentInHiltContainer<CakeListingFragment> {
            Navigation.setViewNavController(requireView(), navController)
            viewBindingAd = viewBinding
        }

        delay(10000L)
        val adapter = (viewBindingAd.list.adapter as CakesAdapter)
        val lastItemIndex = adapter.itemCount - 1

        Espresso.onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    lastItemIndex,
                    ClickOnButtonView()
                )
            )

        val cakes = adapter.getCakesAtPosition(lastItemIndex)

        verify(navController).navigate(
            CakeListingFragmentDirections.actionCakeListingFragmentToCakeDetailsJavaFragment(cakes),
        )
    }

    inner class ClickOnButtonView : ViewAction {
        private var click = ViewActions.click()

        override fun getConstraints(): Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom button view"
        }

        override fun perform(uiController: UiController, view: View) {
            //btnClickMe -> Custom row item view button
            click.perform(uiController, view.findViewById(R.id.cardView))
        }
    }
}