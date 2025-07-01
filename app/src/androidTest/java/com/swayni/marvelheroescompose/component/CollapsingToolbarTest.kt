package com.swayni.marvelheroescompose.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.swayni.marvelheroescompose.ui.component.CollapsingToolbar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalMaterial3Api::class)
@RunWith(AndroidJUnit4::class)
class CollapsingToolbarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun collapsingToolbar_showsLogoBox() {
        composeTestRule.setContent {
            CollapsingToolbar(showTitle = false)
        }

        composeTestRule.onNodeWithTag("LogoBox").assertExists()
    }

    @Test
    fun collapsingToolbar_showsTitle_whenShowTitleTrue() {
        composeTestRule.setContent {
            CollapsingToolbar(showTitle = true)
        }

        composeTestRule.onNodeWithTag("CollapsedTitle").assertExists()
    }

    @Test
    fun collapsingToolbar_hidesTitle_whenShowTitleFalse() {
        composeTestRule.setContent {
            CollapsingToolbar(showTitle = false)
        }

        composeTestRule.onNodeWithTag("CollapsedTitle").assertDoesNotExist()
    }
}