package com.wajihan.moviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.wajihan.moviedb.R
import com.wajihan.moviedb.domain.movie.model.movie.ProductionCompany
import com.wajihan.moviedb.utils.loadImageUrl
import com.wajihan.moviedb.utils.toImageUrl
import kotlinx.android.synthetic.main.item_production_company.view.imgPoster
import kotlinx.android.synthetic.main.item_production_company.view.tvName

class ProductionCompanyAdapter(
    val data: MutableList<ProductionCompany> = mutableListOf()
) :
    RecyclerView.Adapter<ProductionCompanyAdapter.ViewHolder>() {

    fun setData(datas: List<ProductionCompany>) {
        if (data.size > 0) {
            data.clear()
        }
        data.addAll(datas)
        notifyDataSetChanged()
    }

    fun addOrUpdate(item: ProductionCompany) {
        val i: Int = data.indexOf(item)
        if (i >= 0) {
            data[i] = item
            notifyDataSetChanged()
        } else {
            data.add(item)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_production_company,
            viewGroup, false
        )
        return ProductionCompanyViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ProductionCompany = data[position]
        val viewHolder = holder as ProductionCompanyViewHolder
        viewHolder.bindProductionCompanyItem(item)
    }

    open inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ProductionCompanyViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bindProductionCompanyItem(data: ProductionCompany) {
            with(itemView) {
                imgPoster.loadImageUrl(context, data.logoPath.toImageUrl())
                tvName.text = data.name
            }
        }
    }
}
