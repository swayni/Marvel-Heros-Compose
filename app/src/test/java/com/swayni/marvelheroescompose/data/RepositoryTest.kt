package com.swayni.marvelheroescompose.data

import app.cash.turbine.test
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.local.ILocalDataSource
import com.swayni.marvelheroescompose.data.model.CharacterDataContainer
import com.swayni.marvelheroescompose.data.model.CharacterDataWrapper
import com.swayni.marvelheroescompose.data.model.ComicDataContainer
import com.swayni.marvelheroescompose.data.model.ComicDataWrapper
import com.swayni.marvelheroescompose.data.remote.IRemoteDataSource
import com.swayni.marvelheroescompose.data.repository.Repository
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class RepositoryTest {

    private lateinit var remoteDataSource: IRemoteDataSource
    private lateinit var localDataSource: ILocalDataSource
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        remoteDataSource = mock()
        localDataSource = mock()
        repository = Repository(remoteDataSource, localDataSource)
    }

    @Test
    fun `getCharacters returns data from remoteDataSource`() = runTest {
        val expected = ResultData.Success(CharacterDataWrapper(
            code = 200,
            status = "Ok",
            copyright = "© 2023 MARVEL",
            attributionText = "Data provided by Marvel. © 2023 MARVEL",
            attributionHTML = "<a href='http://marvel.com'>Data provided by Marvel. © 2023 MARVEL</a>",
            eTag = "",
            data = CharacterDataContainer(
                offset = 0,
                limit = 20,
                total = 1562,
                count = 20,
                results = listOf()
            )
        ))
        whenever(remoteDataSource.getCharacters(0, 20)).thenReturn(expected)

        println(expected)

        repository.getCharacters(0, 20).test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getComicsByCharacterId returns data from remoteDataSource`() = runTest {
        val expected = ResultData.Success(ComicDataWrapper(
            code = 200,
            status = "Ok",
            copyright = "© 2023 MARVEL",
            attributionText = "Data provided by Marvel. © 2023 MARVEL",
            attributionHTML = "<a href='http://marvel.com'>Data provided by Marvel. © 2023 MARVEL</a>",
            eTag = "",
            data = ComicDataContainer(
                offset = 0,
                limit = 20,
                total = 1562,
                count = 20,
                results = listOf()
            )
        ))
        whenever(remoteDataSource.getComicsByCharacterId(1, 2020, 10, "name")).thenReturn(expected)

        repository.getComicsByCharacterId(1, 2020, 10, "name").test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getFavoriteCharacters returns data from localDataSource`() = runTest {
        val character = CharacterModel()
        character.id = 1
        character.name = "Spider-Man"
        character.description = "Hero with spider-like abilities"
        character.imageUrl = "https://example.com/spiderman.jpg"
        val listOf = listOf(character)
        val expected = ResultData.Success(listOf)
        whenever(localDataSource.getFavoriteCharacters()).thenReturn(expected)

        repository.getFavoriteCharacters().test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `addFavoriteCharacter returns result from localDataSource`() = runTest {
        val character = CharacterModel()
        character.id = 1
        character.name = "Spider-Man"
        character.description = "Hero with spider-like abilities"
        character.imageUrl = "https://example.com/spiderman.jpg"

        val expected = ResultData.Success(true)
        whenever(localDataSource.addFavoriteCharacter(character)).thenReturn(expected)

        repository.addFavoriteCharacter(character).test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `removeFavoriteCharacter returns result from localDataSource`() = runTest {
        val character = CharacterModel()
        character.id = 1
        character.name = "Spider-Man"
        character.description = "Hero with spider-like abilities"
        character.imageUrl = "https://example.com/spiderman.jpg"

        val expected = ResultData.Success(true)
        whenever(localDataSource.removeFavoriteCharacter(character)).thenReturn(expected)

        repository.removeFavoriteCharacter(character).test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getFavoriteCharacterWithId returns result from localDataSource`() = runTest {
        val expected = ResultData.Success(true)
        whenever(localDataSource.getFavoriteCharacterWithId(1)).thenReturn(expected)

        repository.getFavoriteCharacterWithId(1).test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
    }
}