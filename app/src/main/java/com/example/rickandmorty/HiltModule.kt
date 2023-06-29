package com.example.rickandmorty

import com.example.rickandmorty.data.api.CharactersApi
import com.example.rickandmorty.data.datasource.ApiDataSource
import com.example.rickandmorty.data.datasource.ApiPagingSource
import com.example.rickandmorty.data.repository.CharactersRepository
import com.example.rickandmorty.domain.GetApiCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
        apiPagingSource: ApiPagingSource
    ): CharactersRepository =
        CharactersRepository(apiPagingSource)

    @Provides
    fun providesGetAllCharactersUseCase(
        charactersRepository: CharactersRepository
    ): GetApiCharactersUseCase =
        GetApiCharactersUseCase(charactersRepository)
}
