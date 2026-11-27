package com.apexfission.android.ui.common.sample

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.net.Uri
import android.provider.Settings
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.Matchers.allOf
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SampleSettingsTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test fun settingsHeaderTargetsTheInstalledDemo() {
        Intents.init()
        try {
            val expected = allOf(hasAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS),
                hasData(Uri.fromParts("package", compose.activity.packageName, null)))
            Intents.intending(expected).respondWith(ActivityResult(Activity.RESULT_CANCELED, null))
            compose.onNodeWithText(compose.activity.getString(R.string.app_settings)).assertIsDisplayed().performClick()
            Intents.intended(expected)
        } finally { Intents.release() }
    }

    @Test fun settingsReturnAndRecreationKeepTheDemoListUsable() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val packageName = compose.activity.packageName
        compose.onNodeWithText(compose.activity.getString(R.string.app_settings)).performClick()
        assertTrue(device.wait(Until.hasObject(By.pkg("com.android.settings").depth(0)), 10_000))
        device.pressBack()
        assertTrue(device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 10_000))
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText(compose.activity.getString(R.string.app_settings)).assertIsDisplayed()
        openDemo(ComponentDemo.Verification)
        compose.onNodeWithContentDescription(compose.activity.getString(R.string.code_description)).performScrollTo().assertIsDisplayed()
    }

    @Test fun everyDemoOpensAndReturnsToTheList() {
        for (demo in ComponentDemo.entries) {
            openDemo(demo)
            compose.onNodeWithContentDescription(compose.activity.getString(R.string.back_to_demos)).assertIsDisplayed()
            compose.onNodeWithText(compose.activity.getString(R.string.app_settings)).assertDoesNotExist()
            when (demo) {
                ComponentDemo.Verification, ComponentDemo.CodeInput ->
                    compose.onNodeWithContentDescription(compose.activity.getString(R.string.code_description)).performScrollTo().assertIsDisplayed()
                ComponentDemo.Action -> {
                    compose.onNodeWithText(compose.activity.getString(R.string.open)).performScrollTo().performClick()
                    compose.onNodeWithText(compose.activity.getString(R.string.clicked)).assertIsDisplayed()
                }
                ComponentDemo.Content -> compose.onNodeWithText(compose.activity.getString(R.string.content_title)).performScrollTo().assertIsDisplayed()
                ComponentDemo.Recipient -> compose.onNodeWithText("name@example.invalid").performScrollTo().assertIsDisplayed()
                ComponentDemo.EmptyState -> compose.onNodeWithText(compose.activity.getString(R.string.empty_title)).performScrollTo().assertIsDisplayed()
            }
            compose.onNodeWithContentDescription(compose.activity.getString(R.string.back_to_demos)).performClick()
            compose.onNodeWithContentDescription(compose.activity.getString(R.string.back_to_demos)).assertDoesNotExist()
            compose.onNodeWithContentDescription(compose.activity.getString(R.string.back_to_components)).performClick()
            // The list retains its position, including the final item below the initial viewport.
            compose.onNodeWithText(compose.activity.getString(demo.title)).assertIsDisplayed()
        }
    }

    @Test fun recreationKeepsTheSelectedDemoAndSystemBackStartsTheNextVisitFresh() {
        openDemo(ComponentDemo.Verification)
        val field = compose.onNodeWithContentDescription(compose.activity.getString(R.string.code_description))
        field.performScrollTo().performTextInput("12")
        closeSoftKeyboard()
        compose.onNodeWithText(compose.activity.getString(R.string.next_state)).performClick()
        compose.activityRule.scenario.recreate()
        scrollTo(hasContentDescription(compose.activity.getString(R.string.code_description)))
        field.assertTextEquals("12")
        compose.onNodeWithText(compose.activity.getString(R.string.error)).performScrollTo().assertIsDisplayed()
        field.performScrollTo().performTextInput("3")
        compose.onNodeWithText(compose.activity.getString(R.string.error)).assertDoesNotExist()
        closeSoftKeyboard()
        pressBack()
        compose.onNodeWithContentDescription(compose.activity.getString(R.string.back_to_demos)).assertDoesNotExist()
        compose.onNodeWithText(compose.activity.getString(R.string.open)).performClick()
        field.performScrollTo().assertTextEquals("")
    }

    private fun openDemo(demo: ComponentDemo) {
        val title = compose.activity.getString(demo.title)
        scrollTo(hasText(title))
        compose.onNodeWithText(title).performClick()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithContentDescription(compose.activity.getString(R.string.back_to_components)).assertIsDisplayed()
        compose.onNodeWithText(compose.activity.getString(R.string.open)).performClick()
    }

    private fun scrollTo(matcher: SemanticsMatcher) {
        compose.onNode(hasScrollToIndexAction()).performScrollToNode(matcher)
    }

}
