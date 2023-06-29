package com.example.rickandmorty

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty.data.api.CharactersApi
import com.example.rickandmorty.data.datasource.ApiDataSource
import com.example.rickandmorty.data.datasource.ApiPagingSource
import com.example.rickandmorty.data.db.CharacterDao
import com.example.rickandmorty.data.db.CharacterDb
import com.example.rickandmorty.data.db.DbPagingSource
import com.example.rickandmorty.data.repository.CharactersRepository
import com.example.rickandmorty.domain.GetApiCharactersUseCase
import com.example.rickandmorty.domain.GetFavoritesCharactersUseCase
import com.example.rickandmorty.model.Constants.CHARACTER_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun providesRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun providesCharactersApi(retrofit: Retrofit): CharactersApi =
        retrofit.create(CharactersApi::class.java)

    @Provides
    fun providesCharactersDataSource(charactersApi: CharactersApi): ApiDataSource =
        ApiDataSource(charactersApi)

    @Provides
    fun provideCharactersPagingSource(
        apiDataSource: ApiDataSource
    ) = ApiPagingSource(apiDataSource)

    @Provides
    fun providesCharactersRepository(
        apiPagingSource: ApiPagingSource,
        dbPagingSource: DbPagingSource,
        dataSource: ApiDataSource
    ): CharactersRepository =
        CharactersRepository(apiPagingSource, dbPagingSource, dataSource)

    @Provides
    fun providesGetAllCharactersUseCase(
        charactersRepository: CharactersRepository
    ): GetApiCharactersUseCase =
        GetApiCharactersUseCase(charactersRepository)

    @Provides
    fun provideCharacterDb(@ApplicationContext context: Context): CharacterDb =
        Room.databaseBuilder(context, CharacterDb::class.java, CHARACTER_DB).build()

    @Provides
    fun provideCharacterDao(characterDb: CharacterDb): CharacterDao =
        characterDb.characterDao()

    @Provides
    fun provideDbPagingSource(characterDao: CharacterDao) =
        DbPagingSource(characterDao)

    @Provides
    fun providesGetFavoritesCharactersUseCase(
        charactersRepository: CharactersRepository
    ): GetFavoritesCharactersUseCase =
        GetFavoritesCharactersUseCase(charactersRepository)
}