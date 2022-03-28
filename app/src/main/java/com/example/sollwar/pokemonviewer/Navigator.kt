package com.example.sollwar.pokemonviewer

import androidx.fragment.app.Fragment

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun pokemonSelected(name: String)
}