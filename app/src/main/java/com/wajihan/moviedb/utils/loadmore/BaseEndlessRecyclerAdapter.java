package com.wajihan.moviedb.utils.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public abstract class BaseEndlessRecyclerAdapter<Data, Holder extends BaseEndlessItemViewHolder> extends
        RecyclerView.Adapter<Holder> {

    /*
        What You Need
        To implement this class, you need some support class you can find in package util > loadmore
        1. BaseEndlessItemViewHolder (Optional : you can edit or use your own view holder, IT MUST ABLE TO USE nullable object)
        2. BaseEndlessRecyclerAdapter (Optional : you can edit or use your own adapter)
        3. EndlessScrollListener
        4. LoadMoreViewHolder with progress bar and TextView error

        Manual
        1. Extend this class on your RecyclerViewAdapter
        2. Add selection in viewType if (datas[position] != null) CONTENT else LOAD_MORE
        3. Define your load more view holder and res on viewType LOAD_MORE and other if viewType  is CONTENT
        4. WE CAN SKIP THIS PART IF WE CREATE TEMPLATE
        5. On your activity or fragment, implement OnLoadMoreListener
        6. create two function for load data, first you can name it initialLoad and other is afterLoad (inspired by paging library)
        7. setEndlessScroll
        8. Put commented function in the bottom of this class as it description and purpose on your viewModel observe
     */

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

    public interface OnLongItemClickListener {

        void onLongItemClick(View view, int position);
    }

    public interface OnLoadMoreListener {

        void onLoadMore(Integer page, Integer totalItemsCount, RecyclerView view);

        void onLoadMoreRetryButtonClicked(Integer page);
    }

    public static int CONTENT = 985213;

    public static int LOAD_MORE = 981923;

    protected Context mContext;

    protected OnItemClickListener mItemClickListener;

    protected OnLongItemClickListener mLongItemClickListener;

    protected OnLoadMoreListener mOnLoadMoreListener;

    private Boolean isLoading = true;

    private Integer loadMoreLimit = 0;

    private Integer loadMoreSkip = 0;

    private List<Data> mDatas;

    private EndlessScrollListener mEndlessScrollListener;

    private RecyclerView mRecyclerView;

    public BaseEndlessRecyclerAdapter(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    public BaseEndlessRecyclerAdapter(Context context, List<Data> data) {
        this.mContext = context;
        this.mDatas = data;
    }

    public void add(Data item) {
        mDatas.add(item);
        notifyItemInserted(mDatas.size() - 1);
    }

    public void add(Data item, int position) {
        mDatas.add(position, item);
        notifyItemInserted(position);
    }

    public void add(final List<Data> items) {
        final int size = items.size();
        for (int i = 0; i < size; i++) {
            mDatas.add(items.get(i));
        }
        notifyDataSetChanged();
    }

    public void addAll(List<Data> items) {
        add(items);
    }

    public void addEndlessScrollListener() {
        mRecyclerView.addOnScrollListener(mEndlessScrollListener);
    }

    public void addOrUpdate(Data item) {
        int i = mDatas.indexOf(item);
        if (i >= 0) {
            mDatas.set(i, item);
            notifyItemChanged(i);
        } else {
            add(item);
        }
    }

    public void addOrUpdate(final List<Data> items) {
        final int size = items.size();
        for (int i = 0; i < size; i++) {
            Data item = items.get(i);
            int x = mDatas.indexOf(item);
            if (x >= 0) {
                mDatas.set(x, item);
            } else {
                add(item);
            }
        }
        notifyDataSetChanged();
    }

    public void addOrUpdateToFirst(final List<Data> items) {
        final int size = items.size();
        for (int i = 0; i < size; i++) {
            Data item = items.get(i);
            int x = mDatas.indexOf(item);
            if (x >= 0) {
                mDatas.set(x, item);
            } else {
                add(item, 0);
            }
        }
        notifyDataSetChanged();
    }

    public void addToFirst(Data item) {
        mDatas.add(0, item);
        notifyDataSetChanged();
    }

    public void addToFirst(final List<Data> items) {
        mDatas.addAll(0, items);
        notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    //put this function when Empty on your loadMore observer to finish load more function
    public void finishLoadMore() {
        if (mDatas.get(mDatas.size() - 1) == null) {

            mDatas.remove(null);
            notifyItemRemoved(mDatas.size() - 1);
            removeEndlessScrollListener();
        }
    }

    public List<Data> getDatas() {
        return mDatas;
    }

    @Override
    public int getItemCount() {
        try {
            return mDatas.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Integer getLoadMoreLimit() {
        return loadMoreLimit;
    }

    public Integer getLoadMoreSkip() {
        return loadMoreSkip;
    }

    public void setLoadMoreSkip(final Integer loadMoreSkip) {
        this.loadMoreSkip = loadMoreSkip;
    }

    public Boolean getLoading() {
        return isLoading;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public void setOnLoadMoreListener(final OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    //put this function when Success on your loadMore observer
    public void hideLoadMoreLoading() {
        if (mDatas.get(mDatas.size() - 1) == null) {
            mDatas.remove(mDatas.size() - 1);
            notifyItemRemoved(mDatas.size() - 1);
            addEndlessScrollListener();
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(mDatas.get(position));
    }

    @Override
    public abstract Holder onCreateViewHolder(ViewGroup parent, int viewType);

    public void remove(int position) {
        if (position >= 0 && position < mDatas.size()) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void remove(Data item) {
        int position = mDatas.indexOf(item);
        remove(position);
    }

    // put this function on initial load
    public void resetEndlessScroll() {
        mEndlessScrollListener.resetState();
        addEndlessScrollListener();
    }

    // NOTE : you must set this function first
    // set this function in initUI or at the first before you use
    public void setEndlessScroll(final RecyclerView recyclerView) {
        mRecyclerView = recyclerView;

        mEndlessScrollListener = new EndlessScrollListener() {
            @Override
            public void onLoadMore(final int page, final int totalItemsCount, @NotNull final RecyclerView view) {
                removeEndlessScrollListener();
                getOnLoadMoreListener().onLoadMore(page, totalItemsCount, view);
                setLoadMoreSkip(page);
            }
        };

        mEndlessScrollListener.setLayoutManager(recyclerView.getLayoutManager());
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener longItemClickListener) {
        this.mLongItemClickListener = longItemClickListener;
    }

    //put this function when Error in loadMore observer
    public void showLoadMoreError() {
        this.isLoading = false;
        notifyItemChanged(mDatas.size() - 1);
    }

    public void showLoadMoreLoading() {
        this.isLoading = true;
        mDatas.add(null);
        notifyDataSetChanged();
    }

    protected abstract int getItemResourceLayout(int viewType);

    protected View getView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(mContext).inflate(getItemResourceLayout(viewType), parent, false);
    }

    private void removeEndlessScrollListener() {
        mRecyclerView.removeOnScrollListener(mEndlessScrollListener);
    }

}