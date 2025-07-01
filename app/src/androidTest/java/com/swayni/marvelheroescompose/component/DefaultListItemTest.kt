package com.swayni.marvelheroescompose.component

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.swayni.marvelheroescompose.ui.component.DefaultListItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DefaultListItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun defaultListItem_displaysNameAndImage() {
        val testName = "Iron Man"
        val testImageUrl = "https://test.image.url/ironman.jpg"

        composeTestRule.setContent {
            DefaultListItem(
                name = testName,
                imageUrl = testImageUrl,
                onClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(testName)
            .assertExists()

        composeTestRule
            .onNodeWithContentDescription("Character Image")
            .assertExists()
    }

    @Test
    fun defaultListItem_triggersOnClick() {
        var wasClicked = false

        composeTestRule.setContent {
            DefaultListItem(
                name = "Test",
                imageUrl = "https://test.url/image.jpg",
                onClick = { wasClicked = true }
            )
        }

        composeTestRule
            .onNodeWithText("Test")
            .performClick()

        assert(wasClicked)
    }
}