package com.swayni.marvelheroescompose.component

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.swayni.marvelheroescompose.ui.component.ButtonIconFavorite
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ButtonIconFavoriteTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun favoriteIcon_showsFilledHeart_whenFavoriteIsTrue() {
        composeTestRule.setContent {
            ButtonIconFavorite(
                isFavorite = true,
                onFavoriteClick = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Favorite")
            .assertExists()
    }

    @Test
    fun favoriteIcon_showsOutlinedHeart_whenFavoriteIsFalse() {
        composeTestRule.setContent {
            ButtonIconFavorite(
                isFavorite = false,
                onFavoriteClick = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Favorite")
            .assertExists()
    }

    @Test
    fun favoriteIcon_triggersClick_whenClicked() {
        var clicked = false
        composeTestRule.setContent {
            ButtonIconFavorite(
                isFavorite = true,
                onFavoriteClick = { clicked = true }
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Favorite")
            .performClick()

        assert(clicked)
    }
}