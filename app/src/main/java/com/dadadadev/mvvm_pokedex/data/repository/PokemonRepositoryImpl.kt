package com.dadadadev.mvvm_pokedex.data.repository

import com.dadadadev.mvvm_pokedex.data.mappers.toPokedexListEntries
import com.dadadadev.mvvm_pokedex.data.remote.PokeApi
import com.dadadadev.mvvm_pokedex.data.remote.responses.Pokemon
import com.dadadadev.mvvm_pokedex.domain.PokemonRepository
import com.dadadadev.mvvm_pokedex.domain.models.PokedexListEntry
import com.dadadadev.mvvm_pokedex.util.Resource
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi
) : PokemonRepository {

    override suspend fun getPokedexListEntries(
        limit: Int,
        offset: Int
    ) : Resource<List<PokedexListEntry>> {
        val response = try {
            api.getPokemonList(limit, offset).toPokedexListEntries()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }

        return Resource.Success(response)
    }

    override suspend fun getPokemonInfo(pokemonName: String) : Resource<Pokemon> {
        val response  = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }

        return Resource.Success(response)
    }
}