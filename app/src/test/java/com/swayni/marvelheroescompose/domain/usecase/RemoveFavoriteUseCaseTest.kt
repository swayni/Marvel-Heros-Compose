package com.swayni.marvelheroescompose.domain.usecase

import app.cash.turbine.test
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.repository.IRepository
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveFavoriteUseCaseTest {

    private lateinit var repository: IRepository
    private lateinit var useCase: RemoveFavoriteUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = RemoveFavoriteUseCase(repository)
    }

    @Test
    fun `execute emits Success when character is removed`() = runTest {
        val character = CharacterModel()
        character.id = 1
        character.name = "Spider-Man"
        character.description = "No description"
        character.imageUrl = "http://example.com/thumbnail.jpg"
        val expected = ResultData.Success(true)

        whenever(repository.removeFavoriteCharacter(character)).thenReturn(flow {
            emit(expected)
        })

        useCase.execute(character).test {
            val result = awaitItem()
            assertTrue(result is ResultData.Success)
            assertEquals(true, (result as ResultData.Success).data)
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
        val exception = ResultData.Error(Exception())

        whenever(repository.removeFavoriteCharacter(character)).thenReturn(flow {
            emit(exception)
        })

        useCase.execute(character).test {
            val result = awaitItem()
            assertTrue(result is ResultData.Error)
            assertEquals(exception, (result as ResultData.Error))
            awaitComplete()
        }
    }
}