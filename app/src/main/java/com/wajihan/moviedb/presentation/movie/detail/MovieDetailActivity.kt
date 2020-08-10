package com.wajihan.moviedb.presentation.movie.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.wajihan.moviedb.R
import com.wajihan.moviedb.R.string
import com.wajihan.moviedb.domain.movie.model.movie.Movie
import com.wajihan.moviedb.presentation.adapter.EndlessReviewAdapter
import com.wajihan.moviedb.presentation.adapter.ProductionCompanyAdapter
import com.wajihan.moviedb.presentation.movie.MovieViewModel
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Empty
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Failure
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Loading
import com.wajihan.moviedb.utils.base.viewmodel.BaseResult.Success
import com.wajihan.moviedb.utils.convertDateFormat
import com.wajihan.moviedb.utils.emptyString
import com.wajihan.moviedb.utils.getYoutubeVideoThumbnailUrl
import com.wajihan.moviedb.utils.key.BundleKeys
import com.wajihan.moviedb.utils.loadImageUrl
import com.wajihan.moviedb.utils.loadmore.BaseEndlessRecyclerAdapter.OnLoadMoreListener
import com.wajihan.moviedb.utils.onClick
import com.wajihan.moviedb.utils.openWebPage
import com.wajihan.moviedb.utils.setTranslucentActivity
import com.wajihan.moviedb.utils.setupToolbar
import com.wajihan.moviedb.utils.showDefaultState
import com.wajihan.moviedb.utils.showEmptyState
import com.wajihan.moviedb.utils.showErrorState
import com.wajihan.moviedb.utils.showLoadingState
import com.wajihan.moviedb.utils.toImageUrl
import kotlinx.android.synthetic.main.activity_movie_detail.appBarMovieDetail
import kotlinx.android.synthetic.main.activity_movie_detail.btnPlayYoutubeTrailer
import kotlinx.android.synthetic.main.activity_movie_detail.cardYoutubeTrailer
import kotlinx.android.synthetic.main.activity_movie_detail.imgBackdrop
import kotlinx.android.synthetic.main.activity_movie_detail.imgPoster
import kotlinx.android.synthetic.main.activity_movie_detail.imgYoutubeThumbnail
import kotlinx.android.synthetic.main.activity_movie_detail.msvMovieDetail
import kotlinx.android.synthetic.main.activity_movie_detail.msvProductionCompany
import kotlinx.android.synthetic.main.activity_movie_detail.msvReview
import kotlinx.android.synthetic.main.activity_movie_detail.msvYoutubeTrailer
import kotlinx.android.synthetic.main.activity_movie_detail.rvProductionCompany
import kotlinx.android.synthetic.main.activity_movie_detail.rvReview
import kotlinx.android.synthetic.main.activity_movie_detail.toolbarDetail
import kotlinx.android.synthetic.main.activity_movie_detail.tvMovieRating
import kotlinx.android.synthetic.main.activity_movie_detail.tvMovieTitle
import kotlinx.android.synthetic.main.activity_movie_detail.tvOverview
import kotlinx.android.synthetic.main.activity_movie_detail.tvSubTitle
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity(), OnLoadMoreListener {

    companion object {
        fun start(context: Context?, movieId: Int) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(BundleKeys.MOVIE_ID, movieId)
            context?.startActivity(intent)
        }
    }

    private var movieId = 0

    private val movieViewModel by viewModel<MovieViewModel>()

    private val productionCompanyAdapter by lazy {
        ProductionCompanyAdapter()
    }

    private val endlessReviewAdapter by lazy {
        EndlessReviewAdapter(this) { review ->
            openWebPage(this, review.url)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieId = intent.getIntExtra(BundleKeys.MOVIE_ID, 0)

        setTranslucentActivity(window)

        initRecyclerView()

        initObserver()

        movieViewModel.getMovieDetail(movieId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLoadMoreRetryButtonClicked(page: Int?) {
        endlessReviewAdapter.showLoadMoreError()
        page?.let {
            val offset = it + 1
            afterLoadReviews(offset)
        }
    }

    override fun onLoadMore(page: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        page?.let {
            val offset = it + 1
            afterLoadReviews(offset)
        }
    }

    private fun initRecyclerView() {
        rvProductionCompany.apply {
            layoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                RecyclerView.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            adapter = productionCompanyAdapter
        }

        rvReview.apply {
            layoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                RecyclerView.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = endlessReviewAdapter
        }

        endlessReviewAdapter.apply {
            setEndlessScroll(rvReview)
            onLoadMoreListener = this@MovieDetailActivity
        }
    }

    private fun initObserver() {
        movieViewModel.movieDetail.observe(this, Observer {
            when (it) {
                is Loading -> {
                    msvMovieDetail.showLoadingState()
                }
                is Failure -> {
                    msvMovieDetail.showErrorState(
                        errorMessage = it.message,
                        errorAction = {
                            movieViewModel.getMovieDetail(movieId)
                        }
                    )
                }
                is Empty -> {
                    msvMovieDetail.showEmptyState()
                }
                is Success -> {
                    msvMovieDetail.showDefaultState()

                    setMovieDetailUI(it.data)

                    if (it.data.productionCompanies.isNotEmpty()) productionCompanyAdapter.setData(it.data.productionCompanies)
                    else msvProductionCompany.showEmptyState(
                        errorMessage = getString(string.label_production_company_empty),
                        title = emptyString(),
                        drawable = Drawable.createFromPath(emptyString())
                    )

                    movieViewModel.getMovieVideos(movieId)

                    initialLoadDiscoverReviews()
                }
            }
        })

        movieViewModel.movieVideos.observe(this, Observer {
            when (it) {
                is Loading -> {
                    msvYoutubeTrailer.showLoadingState()
                }
                is Failure -> {
                    msvYoutubeTrailer.showErrorState(
                        drawable = ContextCompat.getDrawable(this, 0),
                        title = emptyString(),
                        errorMessage = it.message,
                        errorAction = {
                            movieViewModel.getMovieVideos(movieId)
                        }
                    )
                }
                is Empty -> {
                    msvYoutubeTrailer.showEmptyState(
                        errorMessage = getString(string.label_youtube_trailer_empty),
                        title = emptyString(),
                        drawable = Drawable.createFromPath(emptyString())
                    )
                }
                is Success -> {
                    if (it.data.any { video -> video.type == "Trailer" }) {
                        msvYoutubeTrailer.showDefaultState()

                        val videoData = it.data.first { video -> video.type == "Trailer" }
                        imgYoutubeThumbnail.loadImageUrl(
                            this,
                            getYoutubeVideoThumbnailUrl(videoData.key)
                        )

                        cardYoutubeTrailer.onClick {
                            openWebPage(this, getString(string.label_youtube_url) + videoData.key)
                        }

                        btnPlayYoutubeTrailer.onClick {
                            openWebPage(this, getString(string.label_youtube_url) + videoData.key)
                        }
                    } else msvYoutubeTrailer.showEmptyState(
                        errorMessage = getString(string.label_youtube_trailer_empty),
                        title = emptyString(),
                        drawable = Drawable.createFromPath(emptyString())
                    )
                }
            }
        })
    }

    private fun setMovieDetailUI(data: Movie) {
        data.apply {

            //Toolbar
            setupToolbar(toolbarDetail, title, true)

            appBarMovieDetail.addOnOffsetChangedListener(object :
                AppBarLayout.OnOffsetChangedListener {
                var isShow = true
                var scrollRange = -1

                override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.totalScrollRange
                    }

                    if (scrollRange + verticalOffset == 0) {

                        toolbarDetail.title = title

                        isShow = true
                    } else if (isShow) {
                        toolbarDetail.title = emptyString()

                        isShow = false
                    }
                }
            })

            //Movie Detail Data
            imgBackdrop.loadImageUrl(this@MovieDetailActivity, backdropPath.toImageUrl())
            tvMovieTitle.text = title

            val releasedDate = releaseDate.convertDateFormat(getString(string.format_api_date), getString(string.format_detail_date))
            val genres = genres.joinToString { it.name }
            val languages = spokenLanguages.filter { it.name.isNotEmpty() }.joinToString { it.name }
            tvSubTitle.text = listOf(releasedDate, genres, languages).joinToString(getString(string.separator_sub_title))

            tvMovieRating.text = String.format(getString(string.format_rating), voteAverage)

            imgPoster.loadImageUrl(this@MovieDetailActivity, posterPath.toImageUrl())
            tvOverview.text = overview
        }
    }

    private fun initialLoadDiscoverReviews() {
        endlessReviewAdapter.clear()
        endlessReviewAdapter.resetEndlessScroll()

        movieViewModel.getMovieReviews(movieId, 1).observe(this, Observer {
            when (it) {
                is Loading -> {
                    msvReview.showLoadingState()
                }
                is Failure -> {
                    msvReview.showErrorState(
                        errorMessage = it.message,
                        errorAction = {
                            initialLoadDiscoverReviews()
                        }
                    )
                }
                is Empty -> {
                    msvReview.showEmptyState(
                        errorMessage = getString(string.label_user_review_empty),
                        title = emptyString(),
                        drawable = Drawable.createFromPath(emptyString())
                    )
                }
                is Success -> {
                    msvReview.showDefaultState()

                    endlessReviewAdapter.addOrUpdate(it.data)
                }
            }
        })
    }

    private fun afterLoadReviews(page: Int) {
        movieViewModel.getMovieReviews(movieId, page)
            .observe(this, Observer {
                when (it) {
                    is Loading -> endlessReviewAdapter.showLoadMoreLoading()
                    is Empty -> endlessReviewAdapter.finishLoadMore()
                    is Failure -> endlessReviewAdapter.showLoadMoreError()
                    is Success -> {
                        endlessReviewAdapter.hideLoadMoreLoading()

                        endlessReviewAdapter.addOrUpdate(it.data)

                        if (endlessReviewAdapter.datas.isNotEmpty()) {
                            endlessReviewAdapter.notifyItemChanged(0)
                        }
                    }
                }
            })
    }
}