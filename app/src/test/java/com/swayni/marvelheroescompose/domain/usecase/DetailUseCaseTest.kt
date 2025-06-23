package com.swayni.marvelheroescompose.domain.usecase

import app.cash.turbine.test
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.model.ComicDataContainer
import com.swayni.marvelheroescompose.data.model.ComicDataWrapper
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
class DetailUseCaseTest {

    private lateinit var repository: IRepository
    private lateinit var useCase: DetailUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = DetailUseCase(repository)
    }

    @Test
    fun `execute emits Success when repository returns comics`() = runTest {
        val expectedWrapper = ComicDataWrapper(
            code = 200,
            status = "OK",
            copyright = "Copyright 2023 Marvel",
            attributionText = "Data provided by Marvel. © 2023 Marvel",
            attributionHTML = "<a href='http://marvel.com'>Data provided by Marvel. © 2023 Marvel</a>",
            eTag = "d41d8cd98f00b204e9800998ecf8427e",
            data = ComicDataContainer(
                offset = 0,
                limit = 10,
                total = 100,
                count = 10,
                results = listOf()
            )
        )
        val expected = ResultData.Success(expectedWrapper)
        val characterId = 101
        val startYear = 2020
        val limit = 10
        val orderBy = "title"

        whenever(repository.getComicsByCharacterId(characterId, startYear, limit, orderBy)).thenReturn(
            flow { emit(expected) }
        )

        useCase.execute(characterId, startYear, limit, orderBy).test {
            val result = awaitItem()
            assertTrue(result is ResultData.Success)
            assertEquals(expectedWrapper, (result as ResultData.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `execute emits Error when repository throws exception`() = runTest {
        val characterId = 999
        val startYear = 2022
        val limit = 5
        val orderBy = "issueNumber"
        val exception = ResultData.Error(Exception())

        whenever(repository.getComicsByCharacterId(characterId, startYear, limit, orderBy)).thenReturn(
            flow { emit(exception) }
        )

        useCase.execute(characterId, startYear, limit, orderBy).test {
            val result = awaitItem()
            assertTrue(result is ResultData.Error)
            assertEquals(exception, (result as ResultData.Error))
            awaitComplete()
        }
    }
}