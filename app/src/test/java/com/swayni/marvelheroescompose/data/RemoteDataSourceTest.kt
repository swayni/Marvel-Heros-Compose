package com.swayni.marvelheroescompose.data

import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.model.CharacterDataWrapper
import com.swayni.marvelheroescompose.data.model.ComicDataWrapper
import com.swayni.marvelheroescompose.data.remote.RemoteDataSource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    private val api: Api = mock()
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        remoteDataSource = RemoteDataSource(api)
    }

    @Test
    fun `getCharacters should return success when API call succeeds`() = runTest {
        val wrapper = CharacterDataWrapper(code = 200, status = "Ok", data = mock(), attributionText = "Attribution text", attributionHTML = "Attribution HTML", eTag = "etag", copyright = "copyright")
        whenever(api.getCharacters(any(), any(), any(), any(), any())).thenReturn(wrapper)

        val result = remoteDataSource.getCharacters(0, 10)

        assertTrue(result is ResultData.Success)
        assertEquals(wrapper, (result as ResultData.Success).data)
    }

    @Test
    fun `getCharacters should return error when API call throws exception`() = runTest {
        val exception = RuntimeException("Network error")
        whenever(api.getCharacters(any(), any(), any(), any(), any())).thenThrow(exception)

        val result = remoteDataSource.getCharacters(0, 10)

        assertTrue(result is ResultData.Error)
        assertEquals(exception, (result as ResultData.Error).exception)
    }

    @Test
    fun `getComicsByCharacterId should return success when API call succeeds`() = runTest {
        val wrapper = ComicDataWrapper(code = 200, status = "Ok", data = mock(), attributionText = "Attribution text", attributionHTML = "Attribution HTML", eTag = "etag", copyright = "copyright")
        whenever(api.getComics(any(), any(), any(), any(), any(), any(), any())).thenReturn(wrapper)

        val result = remoteDataSource.getComicsByCharacterId(0, 10, 1, "onsaleDate")

        assertTrue(result is ResultData.Success)
        assertEquals(wrapper, (result as ResultData.Success).data)
    }

    @Test
    fun `getComicsByCharacterId should return error when API call throws exception`() = runTest {
        val exception = RuntimeException("Network error")
        whenever(
            api.getComics(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        ).thenThrow(exception)

        val result = remoteDataSource.getComicsByCharacterId(0, 10, 1, "onsaleDate")

        assertTrue(result is ResultData.Error)
        assertEquals(exception, (result as ResultData.Error).exception)
    }
}