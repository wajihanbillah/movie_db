package com.wajihan.moviedb.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.wajihan.moviedb.R
import com.wajihan.moviedb.R.string
import com.wajihan.moviedb.domain.movie.model.movie.Movie
import com.wajihan.moviedb.utils.convertDateFormat
import com.wajihan.moviedb.utils.loadImageUrl
import com.wajihan.moviedb.utils.loadmore.BaseEndlessItemViewHolder
import com.wajihan.moviedb.utils.loadmore.BaseEndlessRecyclerAdapter
import com.wajihan.moviedb.utils.loadmore.LoadMoreViewHolder
import com.wajihan.moviedb.utils.onClick
import com.wajihan.moviedb.utils.toImageUrl
import kotlinx.android.synthetic.main.item_movie.view.imgPosterMovie
import kotlinx.android.synthetic.main.item_movie.view.tvOverview
import kotlinx.android.synthetic.main.item_movie.view.tvReleaseDate
import kotlinx.android.synthetic.main.item_movie.view.tvTitle

class EndlessMovieAdapter(
    context: Context?,
    datas: List<Movie> = mutableListOf(),
    val listener: ((movie: Movie) -> Unit)? = null
) : BaseEndlessRecyclerAdapter<Movie, BaseEndlessItemViewHolder<Movie>>(context, datas) {

    override fun getItemViewType(position: Int): Int {
        return if (datas[position] != null) {
            CONTENT
        } else {
            LOAD_MORE
        }
    }

    override fun getItemResourceLayout(viewType: Int): Int {
        return when (viewType) {
            CONTENT -> R.layout.item_movie
            else -> R.layout.item_load_more
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseEndlessItemViewHolder<Movie> {
        return when (viewType) {
            CONTENT -> MovieViewHolder(
                mContext,
                getView(parent, viewType),
                mItemClickListener,
                mLongItemClickListener
            )
            else -> LoadMoreViewHolder(
                mContext,
                getView(parent, viewType),
                mItemClickListener,
                mLongItemClickListener,
                mOnLoadMoreListener,
                loading,
                loadMoreSkip
            )
        }
    }

    inner class MovieViewHolder(
        context: Context,
        itemView: View,
        itemClickListener: OnItemClickListener?,
        longItemClickListener: OnLongItemClickListener?
    ) : BaseEndlessItemViewHolder<Movie>(
        context,
        itemView,
        itemClickListener,
        longItemClickListener
    ) {

        override fun bind(data: Movie?) {
            with(itemView) {
                data?.let { data ->
                    imgPosterMovie.loadImageUrl(context, data.posterPath.toImageUrl(), true)

                    tvTitle.text = data.title
                    tvOverview.text = data.overview
                    tvReleaseDate.text = data.releaseDate.convertDateFormat(
                        context.getString(string.format_api_date), context.getString(
                            string.format_discover_movie_date
                        )
                    )

                    onClick {
                        listener?.invoke(data)
                    }
                }
            }
        }
    }
}