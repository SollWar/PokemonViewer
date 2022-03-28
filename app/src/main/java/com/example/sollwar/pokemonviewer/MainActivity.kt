package com.example.sollwar.pokemonviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.sollwar.pokemonviewer.fragment.PokemonFragment
import com.example.sollwar.pokemonviewer.fragment.PokemonListFragment
import com.example.sollwar.pokemonviewer.model.PokemonList

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            launchFragment(PokemonListFragment.newInstance())
        }
    }

    override fun pokemonSelected(name: String) {
        launchFragment(PokemonFragment.newInstance(name))
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}