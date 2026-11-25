package com.apexfission.android.ui.common.sample

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.net.Uri
import android.provider.Settings
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
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

    @Test fun settingsReturnAndRecreationKeepTheCodeDemoUsable() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val packageName = compose.activity.packageName
        compose.onNodeWithText(compose.activity.getString(R.string.app_settings)).performClick()
        assertTrue(device.wait(Until.hasObject(By.pkg("com.android.settings").depth(0)), 10_000))
        device.pressBack()
        assertTrue(device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 10_000))
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText(compose.activity.getString(R.string.app_settings)).assertIsDisplayed()
        compose.onNodeWithContentDescription(compose.activity.getString(R.string.code_description)).performScrollTo().assertIsDisplayed()
    }
}
