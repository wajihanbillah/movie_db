package com.wajihan.moviedb.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.wajihan.moviedb.R
import com.wajihan.moviedb.domain.movie.model.Review
import com.wajihan.moviedb.utils.loadmore.BaseEndlessItemViewHolder
import com.wajihan.moviedb.utils.loadmore.BaseEndlessRecyclerAdapter
import com.wajihan.moviedb.utils.loadmore.LoadMoreViewHolder
import com.wajihan.moviedb.utils.onClick
import kotlinx.android.synthetic.main.item_review.view.tvAuthor
import kotlinx.android.synthetic.main.item_review.view.tvContent

class EndlessReviewAdapter(
    context: Context?,
    datas: List<Review> = mutableListOf(),
    val listener: ((review: Review) -> Unit)? = null
) : BaseEndlessRecyclerAdapter<Review, BaseEndlessItemViewHolder<Review>>(context, datas) {

    override fun getItemViewType(position: Int): Int {
        return if (datas[position] != null) {
            CONTENT
        } else {
            LOAD_MORE
        }
    }

    override fun getItemResourceLayout(viewType: Int): Int {
        return when (viewType) {
            CONTENT -> R.layout.item_review
            else -> R.layout.item_load_more
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseEndlessItemViewHolder<Review> {
        return when (viewType) {
            CONTENT -> ReviewViewHolder(
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

    inner class ReviewViewHolder(
        context: Context,
        itemView: View,
        itemClickListener: OnItemClickListener?,
        longItemClickListener: OnLongItemClickListener?
    ) : BaseEndlessItemViewHolder<Review>(
        context,
        itemView,
        itemClickListener,
        longItemClickListener
    ) {

        override fun bind(data: Review?) {
            with(itemView) {
                data?.let { data ->

                    tvAuthor.text = data.author
                    tvContent.text = data.content

                    onClick {
                        listener?.invoke(data)
                    }
                }
            }
        }
    }
}