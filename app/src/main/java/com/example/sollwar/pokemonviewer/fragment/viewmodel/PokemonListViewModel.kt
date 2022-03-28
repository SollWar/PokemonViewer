package com.example.sollwar.pokemonviewer.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sollwar.pokemonviewer.model.PrePok
import com.example.sollwar.pokemonviewer.retrofit.PokemonFetch

class PokemonListViewModel : ViewModel() {

    private var pokemonList: LiveData<List<PrePok>> = PokemonFetch().fetchPokemonList()

    fun getPokemonList(): LiveData<List<PrePok>> {
        return pokemonList
    }

}