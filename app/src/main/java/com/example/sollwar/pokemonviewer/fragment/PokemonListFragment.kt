package com.example.sollwar.pokemonviewer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sollwar.pokemonviewer.fragment.viewmodel.PokemonListViewModel
import com.example.sollwar.pokemonviewer.R
import com.example.sollwar.pokemonviewer.model.PrePok
import com.example.sollwar.pokemonviewer.navigator
import java.lang.StringBuilder

class PokemonListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PokemonListViewModel
    private lateinit var adapter: PokemonAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_pokemon, container, false)

        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]

        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.pokemon_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PokemonAdapter(emptyList())

        viewModel.getPokemonList().observe(
            viewLifecycleOwner,
            Observer { pok ->
                pok?.let {
                    progressBar.visibility = View.VISIBLE
                    adapter = PokemonAdapter(pok)
                    recyclerView.adapter = adapter
                    progressBar.visibility = View.INVISIBLE
                }
            }
        )

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    inner class PokemonAdapter(private var listResult: List<PrePok>) : RecyclerView.Adapter<PokemonAdapter.PokemonHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
            return PokemonHolder(view)
        }

        override fun onBindViewHolder(holder: PokemonHolder, position: Int) {
            val resultPok = listResult[position]
            holder.apply {
                holder.bind(resultPok)
            }
        }

        override fun getItemCount() = listResult.size

        inner class PokemonHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

            private lateinit var resultPok: PrePok

            private val pokemonNameTextView: TextView = itemView.findViewById(R.id.pokemon_name)
            private val pokemonUrlTextView: TextView = itemView.findViewById(R.id.pokemon_url)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(resultPok: PrePok) {
                this.resultPok = resultPok
                val name: StringBuilder = StringBuilder(this.resultPok.name)
                name[0] = name[0].uppercaseChar()
                pokemonNameTextView.text = name.toString()
                pokemonUrlTextView.text = this.resultPok.url
            }

            override fun onClick(p0: View?) {
                navigator().pokemonSelected(resultPok.name)
            }

        }
    }

    companion object {
        fun newInstance(): PokemonListFragment {
            return PokemonListFragment()
        }
    }
}