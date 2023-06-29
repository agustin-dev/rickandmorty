package com.example.rickandmorty.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.model.Constants.CHARACTER_TABLE

@Dao
interface CharacterDao {
    @Query("SELECT * FROM $CHARACTER_TABLE ORDER BY id ASC LIMIT 20 OFFSET :offset")
    suspend fun getAllCharacters(offset: Int): List<CharacterSchema>

    @Query("SELECT * FROM $CHARACTER_TABLE WHERE id = :characterId")
    suspend fun getCharacter(characterId: Int): CharacterSchema

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCharacter(character: CharacterSchema)

    @Update
    suspend fun updateCharacter(character: CharacterSchema)

    @Delete
    suspend fun deleteCharacter(character: CharacterSchema)
}
