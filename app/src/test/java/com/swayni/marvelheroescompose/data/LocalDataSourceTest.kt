package com.swayni.marvelheroescompose.data

import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.local.LocalDataSource
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class LocalDataSourceTest {

    private val realm: Realm = mock()
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        localDataSource = LocalDataSource(realm)
    }

    @Test
    fun `getFavoriteCharacters returns success with list`() = runTest {
        val mockCharacter = CharacterModel()
        mockCharacter.id = 1
        mockCharacter.name = "Test Character"
        mockCharacter.description = "Test Description"
        mockCharacter.imageUrl = "http://example.com/thumbnail.jpg"


        val mockResults: RealmResults<CharacterModel> = mock()
        whenever(mockResults.iterator()).thenReturn(listOf(mockCharacter).iterator()) // forEach çağrısı için gerekli

        val query: RealmQuery<CharacterModel> = mock()
        whenever(query.find()).thenReturn(mockResults)

        whenever(realm.query(CharacterModel::class)).thenReturn(query)

        val result = localDataSource.getFavoriteCharacters()

        assertTrue(result is ResultData.Success)
        assertEquals(listOf(mockCharacter), (result as ResultData.Success).data)
    }

    @Test
    fun `getFavoriteCharacters returns error when exception thrown`() = runTest {
        whenever(realm.query(CharacterModel::class)).thenThrow(RuntimeException("Realm error"))

        val result = localDataSource.getFavoriteCharacters()

        assertTrue(result is ResultData.Error)
        assertEquals("Realm error", (result as ResultData.Error).exception.message)
    }

    @Test
    fun `addFavoriteCharacter returns success when copyToRealm succeeds`() = runTest {
        val character = CharacterModel()
        character.id = 1
        character.name = "Spider-Man"
        character.description = "No description"
        character.imageUrl = "http://example.com/thumbnail.jpg"

        val blockCaptor = argumentCaptor<MutableRealm.() -> Unit>()

        whenever(realm.writeBlocking(blockCaptor.capture())).thenReturn(Unit)

        val result = localDataSource.addFavoriteCharacter(character)

        blockCaptor.firstValue.invoke(mock())

        assertTrue(result is ResultData.Success)
        assertTrue((result as ResultData.Success).data)
    }

    @Test
    fun `addFavoriteCharacter returns false when exception is thrown`() = runTest {
        val character = CharacterModel()
        character.id = 2
        character.name = "Hulk"
        character.description = "No description"
        character.imageUrl = "http://example.com/thumbnail.jpg"

        val blockCaptor = argumentCaptor<MutableRealm.() -> Unit>()

        whenever(realm.writeBlocking(blockCaptor.capture())).thenThrow(RuntimeException("Write error"))

        val result = localDataSource.addFavoriteCharacter(character)

        assertTrue(result is ResultData.Success)
        assertFalse((result as ResultData.Success).data)
    }

    @Test
    fun `removeFavoriteCharacter returns success when character is deleted`() = runTest {
        val character = CharacterModel()
        character.id = 1
        character.name = "Spider-Man"
        character.description = "No description"
        character.imageUrl = "http://example.com/thumbnail.jpg"
        val mockResult = mock<CharacterModel>()

        val blockCaptor = argumentCaptor<MutableRealm.() -> Unit>()
        val mockResults: RealmResults<CharacterModel> = mock()
        whenever(mockResults.iterator()).thenReturn(listOf(mockResult).iterator())

        val mockFirstQuery = mock<RealmQuery<CharacterModel>> {
            on { find() } doReturn mockResults
        }

        val mutableRealm = mock<MutableRealm> {
            on { query(CharacterModel::class, "id == $0", character.id) } doReturn mockFirstQuery
        }

        whenever(realm.writeBlocking(blockCaptor.capture())).thenAnswer {
            blockCaptor.firstValue.invoke(mutableRealm)
        }
        localDataSource.addFavoriteCharacter(character)
        val result = localDataSource.removeFavoriteCharacter(character)

        assertTrue(result is ResultData.Success)
        assertTrue((result as ResultData.Success).data)
    }

    @Test
    fun `removeFavoriteCharacter returns false when exception is thrown`() = runTest {
        val character = CharacterModel()
        character.id = 2
        character.name = "Hulk"
        character.description = "No description"
        character.imageUrl = "http://example.com/thumbnail.jpg"

        val blockCaptor = argumentCaptor<MutableRealm.() -> Unit>()

        whenever(realm.writeBlocking(blockCaptor.capture())).thenThrow(RuntimeException("Realm error"))

        val result = localDataSource.removeFavoriteCharacter(character)

        assertTrue(result is ResultData.Success)
        assertFalse((result as ResultData.Success).data)
    }
}