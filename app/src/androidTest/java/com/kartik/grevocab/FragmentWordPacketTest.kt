package com.kartik.grevocab

import android.os.Bundle
import android.os.SystemClock
import androidx.databinding.ObservableField
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kartik.grevocab.adapters.display.GroupTableDisplay
import com.kartik.grevocab.adapters.display.WordDisplay
import com.kartik.grevocab.api.apiModule
import com.kartik.grevocab.api.pojos.FeatureFlags
import com.kartik.grevocab.api.pojos.Word
import com.kartik.grevocab.base.Preferences
import com.kartik.grevocab.bootstrap.TestApp
import com.kartik.grevocab.persistence.entities.GroupsTableRoom
import com.kartik.grevocab.persistence.entities.WordsTableRoom
import com.kartik.grevocab.utility.FeatureFlagObj
import com.kartik.grevocab.utility.toRoomObj
import com.kartik.grevocab.vm.FragmentWordPacketViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module

class FragmentWordPacketTest {

    // NOTE you can only run 1 test at a time as viewModel cant be cleared in mocks

    private val viewModel: FragmentWordPacketViewModel = mockk()
    private val preferences: Preferences = mockk()
    private val fragment = FragmentWordPacket()

    private val instrumentedTestModule = module {
        single { viewModel }
        single { preferences }
    }

    val scenario = launchActivity<ActivityRootMaterialTestsActivity>()

    val modules = listOf(apiModule, myModule, instrumentedTestModule)

    private fun getInitialisedFragment(): FragmentWordPacket {
        val bundle = Bundle()
        bundle.putInt("groupId", 2)
        fragment.arguments = bundle
        return fragment
    }

    val masteredWords = ObservableField(0)
    val totalWords = ObservableField(0)
    val percent = ObservableField(0)

    val currentWordObservable = ObservableField<WordDisplay>()
    // all observable fields as they should emit data will go here

    @Before
    fun before() {

        FeatureFlagObj.featureFlags = FeatureFlags()
        val word = Word()
        word.groupId = 2
        word.understoodCount = -1
        word.comment = "comment"
        word.name = "name"
        word.meaning = "meaning"
        word.isStarred = true
        word.sentence = "sentence"
        word.similarMeaningWords = "similarMeaningWords"
        val wd = WordDisplay(word.toRoomObj(), null, null, "name, similarMeaningWords", "similarMeaningWords")
        currentWordObservable.set(wd)

        val wordsTableRoomList = ArrayList<WordsTableRoom>()

        val groupsTableRoom = GroupsTableRoom(2, "High Frequency - I", "Must do list of high frequency words", true)
        // viewModel.stockDisplay = GroupTableDisplay(firstGroup.toRoomObj(), wordsTableRoomList, "Group 1", R.color.colorAccent)
        every { viewModel.stockDisplay } returns GroupTableDisplay(groupsTableRoom, wordsTableRoomList, "Group I", R.color.colorAccent)
        every { viewModel.getGroupName() } returns ("Group I")
        every { viewModel.masteredWords } returns(masteredWords)
        every { viewModel.currentWordObservable } returns(currentWordObservable)
        every { viewModel.totalWords } returns(totalWords)
        every { viewModel.percent } returns(percent)
        every { viewModel.bringNextWord() } returns(Unit)
        every { viewModel.iKnowThisWordClicked() } returns(Unit)
        every { viewModel.starredImageClicked() } returns(Unit)
        every { viewModel.proceedClicked(any()) } returns(Unit)
        every { viewModel.initialiseKnownAndUnknownWords() } returns(Unit)

        every { preferences.getBoolean(any(), any()) } returns(false)

        scenario.onActivity { activity ->
            val app = activity.application as TestApp
            app.injectModules( //            working code
                modules
            )

            // app.injectModules(listOf(myModule, apiModule, instrumentedTestModule)) //doesnt work
            // do some stuff with the Activity
            activity.runOnUiThread {
                val fm = activity.supportFragmentManager
                val transaction = fm.beginTransaction()
                transaction.replace(
                    R.id.fragmentContainer,
                    getInitialisedFragment()
                ).commitAllowingStateLoss()
            }
        }
    }

    @Test
    fun testActivityStart() {
        Espresso.onView(withId(R.id.main_word)).check(matches(ViewMatchers.withText("name, similarMeaningWords")))
    }

    @Test
    fun testFragmentStart() {

//         Note need to uncomment if tests are running on emulator without play services as emulator does not support speaking, if on real device everything works as expected
//         Espresso.onView(withId(R.id.message)).check(ViewAssertions.matches(ViewMatchers.withText("Play button will not work on this device as it is not supported.")))
//         (fragment.activity as ActivityRootMaterialTestsActivity).hideBottomSheet()

        //        //working code
        //        Espresso.pressBack()
        //        SystemClock.sleep(2000)

        SystemClock.sleep(2000)

        Espresso.onView(withId(R.id.main_word)).check(matches(ViewMatchers.withText("name, similarMeaningWords")))
        Espresso.onView(withId(R.id.sentence_word_view)).check(matches(ViewMatchers.withText("sentence")))
    }

    @Test
    fun testFragmentFlip() {

        // Note need to uncomment if tests are running on emulator  without play services as emulator does not support speaking, if on real device everything works as expected
        //        SystemClock.sleep(2000)
        //        // bottom sheet is not up directlty it takes a while that is why we have first SystemClock.sleep
        //        (fragment.activity as ActivityRootMaterialTestsActivity).hideBottomSheet()
        //        SystemClock.sleep(2000)

        Espresso.onView(withId(R.id.main_word)).check(matches(ViewMatchers.withText("name, similarMeaningWords")))
        Espresso.onView(withId(R.id.sentence_word_view)).check(matches(ViewMatchers.withText("sentence")))
        // Espresso.onView(withId(R.id.main_word_word_detail_view)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.main_word_word_detail_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        Espresso.onView(withId(R.id.i_do_not_know_this_word)).perform(ViewActions.click())
        SystemClock.sleep(2000)

        Espresso.onView(withId(R.id.main_word_word_detail_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(withId(R.id.similar_meaning)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(withId(R.id.similar_meaning)).check(matches(ViewMatchers.withText("similarMeaningWords")))
        Espresso.onView(withId(R.id.sentence_word_detail)).check(matches(ViewMatchers.withText("sentence")))
    }
}
