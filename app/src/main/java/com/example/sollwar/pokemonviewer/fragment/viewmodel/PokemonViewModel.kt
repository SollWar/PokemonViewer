package com.example.sollwar.pokemonviewer.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sollwar.pokemonviewer.model.pokemon.Pokemon
import com.example.sollwar.pokemonviewer.retrofit.PokemonFetch

class PokemonViewModel : ViewModel() {

    private lateinit var pokemon: LiveData<Pokemon>

    fun getPokemon(name: String): LiveData<Pokemon> {
        pokemon = PokemonFetch().fetchPokemon(name)
        return pokemon
    }
}