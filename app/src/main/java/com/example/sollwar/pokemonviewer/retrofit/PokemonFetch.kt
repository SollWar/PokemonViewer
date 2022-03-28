package com.example.sollwar.pokemonviewer.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sollwar.pokemonviewer.model.PokemonList
import com.example.sollwar.pokemonviewer.model.PrePok
import com.example.sollwar.pokemonviewer.model.pokemon.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonFetch {
    private val pokemonApi: PokemonApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pokemonApi = retrofit.create(PokemonApi::class.java)
    }

    fun fetchPokemon(name: String) : LiveData<Pokemon> {
        val responseLiveData: MutableLiveData<Pokemon> = MutableLiveData()
        val pokemonRequest: Call<Pokemon> = pokemonApi.fetchPokemon(name)
        pokemonRequest.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                responseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                //
            }

        })
        return responseLiveData
    }

    fun fetchPokemonList(): LiveData<List<PrePok>> {
        val responseLiveData: MutableLiveData<List<PrePok>> = MutableLiveData()
        val limitRequest: Call<PokemonList> = pokemonApi.fetchPokemonList(1)
        limitRequest.enqueue(object : Callback<PokemonList> {
            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                val pokemonRequest: Call<PokemonList> = pokemonApi.fetchPokemonList(response.body()!!.count)
                pokemonRequest.enqueue(object : Callback<PokemonList> {
                    override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                        val pokemonList: PokemonList? = response.body()
                        responseLiveData.value = pokemonList?.results
                    }

                    override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                        //
                    }

                })
            }
            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                //
            }
        })
        return responseLiveData
    }

    /*
    fun fetchPokemon(offset: Int, limit: Int): List<PrePok> {
        var responseList: List<PrePok> = listOf()
        val pokemonRequest: Call<PokemonList> = pokemonApi.fetchPokemonList(offset, limit)
        pokemonRequest.enqueue(object : Callback<PokemonList> {
            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                val pokemonList: PokemonList? = response.body()
                responseList = pokemonList!!.results
            }

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                //
            }

        })
        return responseList
    }
     */
}