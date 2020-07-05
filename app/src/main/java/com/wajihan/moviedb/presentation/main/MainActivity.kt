package com.wajihan.moviedb.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.wajihan.moviedb.R.layout
import com.wajihan.moviedb.R.string
import com.wajihan.moviedb.domain.movie.model.Genre
import com.wajihan.moviedb.presentation.adapter.GenreAdapter
import com.wajihan.moviedb.presentation.movie.DiscoverMoviesActivity
import com.wajihan.moviedb.presentation.movie.MovieViewModel
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Empty
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Failure
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Loading
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Success
import com.wajihan.moviedb.utils.onClick
import com.wajihan.moviedb.utils.orMinus
import com.wajihan.moviedb.utils.setupToolbar
import com.wajihan.moviedb.utils.showDefaultState
import com.wajihan.moviedb.utils.showEmptyState
import com.wajihan.moviedb.utils.showErrorState
import com.wajihan.moviedb.utils.showLoadingState
import com.wajihan.moviedb.utils.showWhiteAlertDialog
import kotlinx.android.synthetic.main.activity_main.btnContinue
import kotlinx.android.synthetic.main.activity_main.msvGenre
import kotlinx.android.synthetic.main.activity_main.rvGenre
import kotlinx.android.synthetic.main.activity_main.tvSelectedGenre
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context?.startActivity(intent)
        }
    }

    private val movieViewModel by viewModel<MovieViewModel>()

    private val genreAdapter by lazy {
        GenreAdapter(listener = { genre, position ->
            selectedGenre[position] = genre
            setSelectedGenreText()
        })
    }

    private var selectedGenre = mutableListOf<Genre>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        setupToolbar(toolbar, getString(string.label_choose_movie_genre))

        rvGenre.apply {
            layoutManager =
                GridLayoutManager(
                    context,
                    2
                )
            setHasFixedSize(true)
            adapter = genreAdapter
        }

        btnContinue.onClick {
            if (selectedGenre.any { it.isSelected }) {
                DiscoverMoviesActivity.start(this, selectedGenre)
            }
        }

        initObserver()

        movieViewModel.getGenres()
    }

    override fun onBackPressed() {
        showWhiteAlertDialog(
            title = getString(string.title_exit),
            message = getString(string.message_exit),
            negativeButton = Pair(getString(android.R.string.no), {}),
            positiveButton = Pair(getString(android.R.string.yes), {
                finish()
            })
        )
    }

    private fun initObserver() {
        movieViewModel.genres.observe(this, Observer {
            when (it) {
                is Loading -> {
                    msvGenre.showLoadingState()
                }
                is Failure -> {
                    msvGenre.showErrorState(
                        errorMessage = it.message,
                        errorAction = {
                            movieViewModel.getGenres()
                        }
                    )
                }
                is Empty -> {
                    msvGenre.showEmptyState()
                }
                is Success -> {
                    msvGenre.showDefaultState()

                    selectedGenre = it.data.toMutableList()

                    genreAdapter.setData(selectedGenre)
                }
            }
        })
    }

    private fun setSelectedGenreText() {
        tvSelectedGenre.text = selectedGenre.filter { it.isSelected }.joinToString { it.name }.orMinus()
    }
}