package com.example.rickandmorty.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.model.CharacterSchema
import com.example.rickandmorty.model.Converters

@Database(entities = [CharacterSchema::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CharacterDb : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}