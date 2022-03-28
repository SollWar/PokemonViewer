package com.example.sollwar.pokemonviewer.retrofit

import com.example.sollwar.pokemonviewer.model.PokemonList
import com.example.sollwar.pokemonviewer.model.pokemon.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("api/v2/pokemon/")
    fun fetchPokemonList(@Query("limit") limit: Int): Call<PokemonList>

    @GET("api/v2/pokemon/{name}")
    fun fetchPokemon(@Path("name") name: String): Call<Pokemon>
}