package com.swayni.marvelheroescompose.component

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.swayni.marvelheroescompose.ui.component.LoadingPopup
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingPopupTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingPopup_displaysCorrectly() {
        composeTestRule.setContent {
            LoadingPopup()
        }

        composeTestRule.onNodeWithTag("LoadingPopupBox").assertExists()
        composeTestRule.onNodeWithTag("LoadingPopupCard").assertExists()
        composeTestRule.onNodeWithTag("LoadingText").assertExists()
        composeTestRule.onNodeWithTag("LoadingProgress").assertExists()
    }
}