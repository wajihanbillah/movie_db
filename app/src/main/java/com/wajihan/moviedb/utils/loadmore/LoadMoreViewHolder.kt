package com.wajihan.moviedb.utils.loadmore

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.item_load_more.view.pbLoadMore
import kotlinx.android.synthetic.main.item_load_more.view.tvLoadMoreError

class LoadMoreViewHolder<T>(
    context: Context?,
    itemView: View,
    itemClickListener: BaseEndlessRecyclerAdapter.OnItemClickListener?,
    longItemClickListener: BaseEndlessRecyclerAdapter.OnLongItemClickListener?,
    private val loadMoreListener: BaseEndlessRecyclerAdapter.OnLoadMoreListener?,
    private val loading: Boolean,
    val loadMorePage: Int
) : BaseEndlessItemViewHolder<T>(context!!, itemView, itemClickListener, longItemClickListener) {

    override fun bind(data: T?) {
        with(itemView) {
            pbLoadMore.visibility = if (loading) View.VISIBLE else View.INVISIBLE
            tvLoadMoreError.visibility = if (!loading) View.VISIBLE else View.INVISIBLE
            tvLoadMoreError.setOnClickListener {
                loadMoreListener?.onLoadMoreRetryButtonClicked(loadMorePage)
            }
        }
    }
}