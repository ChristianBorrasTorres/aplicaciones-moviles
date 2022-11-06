package com.example.vinilosgrupo3

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.vinilosgrupo3.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class Test {
    @Rule
    var mActivityTestRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun mainActivityTest2() {
        Espresso.onView(withId(R.id.card)).perform(click())

    }
}