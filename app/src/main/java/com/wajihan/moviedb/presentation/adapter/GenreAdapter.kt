package com.wajihan.moviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wajihan.moviedb.R
import com.wajihan.moviedb.domain.movie.model.Genre
import com.wajihan.moviedb.utils.onClick
import kotlinx.android.synthetic.main.item_genre.view.cardGenre
import kotlinx.android.synthetic.main.item_genre.view.tvName

class GenreAdapter(
    val data: MutableList<Genre> = mutableListOf(),
    val listener: ((genre: Genre, position: Int) -> Unit)? = null
) :
    RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    fun setData(datas: List<Genre>) {
        if (data.size > 0) {
            data.clear()
        }
        data.addAll(datas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_genre,
            viewGroup, false
        )
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Genre = data[position]
        val viewHolder = holder as GenreViewHolder
        viewHolder.bindGenreItem(item, position)
    }

    open inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class GenreViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bindGenreItem(data: Genre, position: Int) {
            with(itemView) {

                if (data.isSelected) {
                    cardGenre.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPaleAquaTwo))
                    cardGenre.strokeColor = ContextCompat.getColor(context, R.color.colorTael)
                } else {
                    cardGenre.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
                    cardGenre.strokeColor = ContextCompat.getColor(context, R.color.colorWarmGrey)
                }

                tvName.text = data.name

                onClick {
                    data.isSelected = !data.isSelected
                    notifyDataSetChanged()
                    listener?.invoke(data, position)
                }
            }
        }
    }
}
