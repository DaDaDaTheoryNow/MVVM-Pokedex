package com.dadadadev.mvvm_pokedex.domain

import com.dadadadev.mvvm_pokedex.data.remote.responses.Pokemon
import com.dadadadev.mvvm_pokedex.domain.models.PokedexListEntry
import com.dadadadev.mvvm_pokedex.util.Resource

interface PokemonRepository {
    suspend fun getPokedexListEntries(limit: Int, offset: Int) : Resource<List<PokedexListEntry>>
    suspend fun getPokemonInfo(pokemonName: String) : Resource<Pokemon>
}