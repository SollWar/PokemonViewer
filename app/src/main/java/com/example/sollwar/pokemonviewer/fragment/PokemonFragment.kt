package com.example.sollwar.pokemonviewer.fragment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sollwar.pokemonviewer.R
import com.example.sollwar.pokemonviewer.fragment.viewmodel.PokemonViewModel
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

const val POKEMON_NAME = "PokemonName"

class PokemonFragment : Fragment() {
    private lateinit var viewModel: PokemonViewModel

    private lateinit var nameTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var viewInfo: ConstraintLayout
    private lateinit var pokemonAvatar: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var builder: StringBuilder

    private var pokemonName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokemon, container, false)

        viewModel = ViewModelProvider(this)[PokemonViewModel::class.java]

        nameTextView = view.findViewById(R.id.name_text_view)
        heightTextView = view.findViewById(R.id.height_text_view)
        weightTextView = view.findViewById(R.id.weight_text_view)
        pokemonAvatar = view.findViewById(R.id.pokemon_avatar)
        viewInfo = view.findViewById(R.id.view_info)
        progressBar = view.findViewById(R.id.progress_bar)

        pokemonName = arguments?.getSerializable(POKEMON_NAME) as String
        builder = StringBuilder(pokemonName)
        builder[0] = builder[0].uppercaseChar()
        nameTextView.text = builder.toString()

        /*
        val bitmap: Bitmap = (ResourcesCompat.getDrawable(
            resources,
            R.drawable.avatar,
            null
        ) as BitmapDrawable ).bitmap
        pokemonAvatar.setImageBitmap(bitmap)
         */

        viewModel.getPokemon(pokemonName).observe(
            viewLifecycleOwner,
            Observer { pokemon ->
                progressBar.visibility = View.VISIBLE

                Picasso.with(context)
                    .load(pokemon.sprites.front_default)
                    .fit()
                    .into(pokemonAvatar)

                builder = StringBuilder(pokemon.name)
                builder[0] = builder[0].uppercaseChar()
                nameTextView.text = builder.toString()
                heightTextView.text = getString(R.string.height, (pokemon.height / 10.0).toString())
                weightTextView.text = getString(R.string.weight, (pokemon.weight / 10.0).toString())

                viewInfo.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        )

        return view
    }

    companion object {
        fun newInstance(name: String): PokemonFragment {
            return PokemonFragment().apply {
                arguments = Bundle().apply {
                    putString(POKEMON_NAME, name)
                }
            }
        }
    }
}