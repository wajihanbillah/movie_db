package com.wajihan.moviedb.presentation.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wajihan.moviedb.R
import com.wajihan.moviedb.R.string
import com.wajihan.moviedb.domain.movie.model.Genre
import com.wajihan.moviedb.presentation.adapter.EndlessMovieAdapter
import com.wajihan.moviedb.presentation.movie.detail.MovieDetailActivity
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Empty
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Failure
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Loading
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Success
import com.wajihan.moviedb.utils.key.BundleKeys
import com.wajihan.moviedb.utils.loadmore.BaseEndlessRecyclerAdapter
import com.wajihan.moviedb.utils.orMinus
import com.wajihan.moviedb.utils.setupToolbar
import com.wajihan.moviedb.utils.showDefaultState
import com.wajihan.moviedb.utils.showEmptyState
import com.wajihan.moviedb.utils.showErrorState
import com.wajihan.moviedb.utils.showLoadingState
import kotlinx.android.synthetic.main.activity_discover_movies.msvDiscoverMovies
import kotlinx.android.synthetic.main.activity_discover_movies.rvDiscoverMovies
import kotlinx.android.synthetic.main.activity_discover_movies.tvSelectedGenre
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class DiscoverMoviesActivity : AppCompatActivity(), BaseEndlessRecyclerAdapter.OnLoadMoreListener {

    companion object {
        fun start(context: Context?, genres: MutableList<Genre>) {
            val intent = Intent(context, DiscoverMoviesActivity::class.java)
            intent.putParcelableArrayListExtra(BundleKeys.GENRES, ArrayList(genres))
            context?.startActivity(intent)
        }
    }

    private var selectedGenres = mutableListOf<Genre>()

    private val movieViewModel by viewModel<MovieViewModel>()

    private val endlessMovieAdapter by lazy {
        EndlessMovieAdapter(this) {
            MovieDetailActivity.start(this, it.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_movies)

        selectedGenres = intent.getParcelableArrayListExtra<Genre>(BundleKeys.GENRES) ?: mutableListOf()

        setupToolbar(toolbar, getString(string.label_dicover_movies), true)

        tvSelectedGenre.text = selectedGenres.filter { it.isSelected }.joinToString { it.name }.orMinus()

        rvDiscoverMovies.apply {
            layoutManager = LinearLayoutManager(
                this@DiscoverMoviesActivity,
                RecyclerView.VERTICAL,
                false
            )
            adapter = endlessMovieAdapter
            setHasFixedSize(true)
        }

        endlessMovieAdapter.apply {
            setEndlessScroll(rvDiscoverMovies)
            onLoadMoreListener = this@DiscoverMoviesActivity
        }


        initialLoadDiscoverMovies()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLoadMoreRetryButtonClicked(page: Int?) {
        endlessMovieAdapter.showLoadMoreError()
        page?.let {
            val offset = it + 1
            afterLoadMovies(offset)
        }
    }

    override fun onLoadMore(page: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        page?.let {
            val offset = it + 1
            afterLoadMovies(offset)
        }
    }

    private fun initialLoadDiscoverMovies() {
        endlessMovieAdapter.clear()
        endlessMovieAdapter.resetEndlessScroll()

        movieViewModel.getDiscoverMovies(selectedGenres.filter { it.isSelected }.map { it.id }.joinToString(), 1).observe(this, Observer {
            when (it) {
                is Loading -> {
                    msvDiscoverMovies.showLoadingState()
                }
                is Failure -> {
                    msvDiscoverMovies.showErrorState(
                        errorMessage = it.message,
                        errorAction = {
                            movieViewModel.getGenres()
                        }
                    )
                }
                is Empty -> {
                    msvDiscoverMovies.showEmptyState()
                }
                is Success -> {
                    msvDiscoverMovies.showDefaultState()

                    endlessMovieAdapter.addOrUpdate(it.data)
                }
            }
        })
    }

    private fun afterLoadMovies(page: Int) {
        movieViewModel.getDiscoverMovies(selectedGenres.filter { it.isSelected }.map { it.id }.joinToString(), page)
            .observe(this, Observer {
                when (it) {
                    is Loading -> endlessMovieAdapter.showLoadMoreLoading()
                    is Empty -> endlessMovieAdapter.finishLoadMore()
                    is Failure -> endlessMovieAdapter.showLoadMoreError()
                    is Success -> {
                        endlessMovieAdapter.hideLoadMoreLoading()

                        endlessMovieAdapter.addOrUpdate(it.data)

                        if (endlessMovieAdapter.datas.isNotEmpty()) {
                            endlessMovieAdapter.notifyItemChanged(0)
                        }
                    }
                }
            })
    }
}