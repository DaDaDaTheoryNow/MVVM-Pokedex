package com.dadadadev.mvvm_pokedex.data.mappers

import com.dadadadev.mvvm_pokedex.data.remote.responses.PokemonList
import com.dadadadev.mvvm_pokedex.domain.models.PokedexListEntry
import java.util.Locale

fun PokemonList.toPokedexListEntries() : List<PokedexListEntry> {
    return results.mapIndexed { _, entry ->
        val number = if (entry.url.endsWith("/")) {
            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            entry.url.takeLastWhile { it.isDigit() }
        }

        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
        PokedexListEntry(
            entry.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            url,
            number.toInt()
        )
    }
}