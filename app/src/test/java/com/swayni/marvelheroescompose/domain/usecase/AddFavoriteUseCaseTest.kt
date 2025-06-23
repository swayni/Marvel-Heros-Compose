package com.swayni.marvelheroescompose.domain.usecase

import app.cash.turbine.test
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.repository.IRepository
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertTrue
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class AddFavoriteUseCaseTest {

    private val repository: IRepository = mock()
    private lateinit var addFavoriteUseCase: AddFavoriteUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        addFavoriteUseCase = AddFavoriteUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `execute emits Success when repository returns success`() = runTest {
        val character = CharacterModel()
        character.id = 1
        character.name = "Spider-Man"
        character.description = "No description"
        character.imageUrl = "http://example.com/thumbnail.jpg"

        val expected = ResultData.Success(true)

        whenever(repository.addFavoriteCharacter(character)).thenReturn(flow {
            emit(expected)
        })

        addFavoriteUseCase.execute(character).test {
            val result = awaitItem()
            assertTrue(result is ResultData.Success)
            assertEquals(true, result.data)
            awaitComplete()
        }
    }

    @Test
    fun `execute emits Error when repository throws exception`() = runTest {
        val character = CharacterModel()
        character.id = 1
        character.name = "Spider-Man"
        character.description = "No description"
        character.imageUrl = "http://example.com/thumbnail.jpg"

        val expected = ResultData.Success(false)

        whenever(repository.addFavoriteCharacter(character)).thenReturn(flow {
            emit(expected)
        })

        addFavoriteUseCase.execute(character).test {
            val result = awaitItem()
            assertTrue(result is ResultData.Success)
            assertEquals(false, result.data)
            awaitComplete()
        }
    }
}