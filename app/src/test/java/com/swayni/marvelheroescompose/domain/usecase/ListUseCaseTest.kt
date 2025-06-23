package com.swayni.marvelheroescompose.domain.usecase

import app.cash.turbine.test
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.model.CharacterDataContainer
import com.swayni.marvelheroescompose.data.model.CharacterDataWrapper
import com.swayni.marvelheroescompose.data.repository.IRepository
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
class ListUseCaseTest {

    private lateinit var repository: IRepository
    private lateinit var useCase: ListUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = ListUseCase(repository)
    }

    @Test
    fun `execute emits Success when repository returns data`() = runTest {
        val offset = 0
        val limit = 20
        val expectedData = CharacterDataWrapper(
            code = 200,
            status = "OK",
            copyright = "© 2023 MARVEL",
            attributionText = "Data provided by Marvel. © 2023 MARVEL",
            attributionHTML = "<a href='http://marvel.com'>Data provided by Marvel. © 2023 MARVEL</a>",
            eTag = "1234567890",
            data = CharacterDataContainer(
                offset = 0,
                limit = 20,
                total = 1562,
                count = 20,
                results = listOf()
            )
        )
        val expected = ResultData.Success(expectedData)

        whenever(repository.getCharacters(offset, limit)).thenReturn(flow {
            emit(expected)
        })

        useCase.execute(offset, limit).test {
            val result = awaitItem()
            assertTrue(result is ResultData.Success)
            assertEquals(expectedData, (result as ResultData.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `execute emits Error when repository throws exception`() = runTest {
        val offset = 0
        val limit = 20
        val exception = ResultData.Error(Exception())

        whenever(repository.getCharacters(offset, limit)).thenReturn(flow {
            emit(exception)
        })

        useCase.execute(offset, limit).test {
            val result = awaitItem()
            assertTrue(result is ResultData.Error)
            assertEquals(exception, (result as ResultData.Error))
            awaitComplete()
        }
    }
}