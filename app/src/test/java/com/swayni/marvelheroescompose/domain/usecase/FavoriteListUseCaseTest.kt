package com.swayni.marvelheroescompose.domain.usecase

import app.cash.turbine.test
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.repository.Repository
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
class FavoriteListUseCaseTest {

    private lateinit var repository: Repository
    private lateinit var useCase: FavoriteListUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = FavoriteListUseCase(repository)
    }

    @Test
    fun `execute emits Success when repository returns favorite characters`() = runTest {
        val characters = listOf(
            CharacterModel().apply {
                id = 1
                name = "Spider-Man"
                description = "No description"
                imageUrl = "http://example.com/thumbnail.jpg"
            },
            CharacterModel().apply {
                id = 2
                name = "Hulk"
                description = "No description"
                imageUrl = "http://example.com/thumbnail.jpg"
            }
        )
        val expected = ResultData.Success(characters)

        whenever(repository.getFavoriteCharacters()).thenReturn(flow {
            emit(expected)
        })

        useCase.execute().test {
            val result = awaitItem()
            assertTrue(result is ResultData.Success)
            assertEquals(characters, (result as ResultData.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `execute emits Error when repository throws exception`() = runTest {
        val exception = ResultData.Error(Exception())

        whenever(repository.getFavoriteCharacters()).thenReturn(flow {
            emit(exception)
        })

        useCase.execute().test {
            val result = awaitItem()
            assertTrue(result is ResultData.Error)
            assertEquals(exception, (result as ResultData.Error))
            awaitComplete()
        }
    }
}