package com.example.sollwar.pokemonviewer.model

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<PrePok>
)