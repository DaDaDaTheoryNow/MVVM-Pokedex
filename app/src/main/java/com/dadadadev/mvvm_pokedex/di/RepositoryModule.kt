package com.dadadadev.mvvm_pokedex.di

import com.dadadadev.mvvm_pokedex.data.repository.PokemonRepositoryImpl
import com.dadadadev.mvvm_pokedex.domain.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun providePokemonRepository(pokemonRepository: PokemonRepositoryImpl) : PokemonRepository
}