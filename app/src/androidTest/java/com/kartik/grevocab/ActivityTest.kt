package com.kartik.grevocab

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityTest {
    @get:Rule
    val activityRule = activityScenarioRule<ActivityRootMaterialTestsActivity>(/* optional intent */)

//    lateinit var scenario: ActivityScenario<ActivityRootMaterialTestsActivity>

    @Test
    fun testActivityStart() {

//        val intent = Intent(ApplicationProvider.getApplicationContext(), ActivityRootMaterialTestsActivity::class.java)
//            .putExtra("title", "Testing rules!")
//        scenario = launchActivity(intent)
        activityRule.scenario
        Espresso.onView(withId(R.id.text)).check(matches(ViewMatchers.withText("akjsndjkasnd")))
    }
}
