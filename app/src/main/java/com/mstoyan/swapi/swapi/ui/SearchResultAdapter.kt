package com.mstoyan.swapi.swapi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mstoyan.swapi.swapi.R
import com.mstoyan.swapi.swapi.network.services.Man
import com.mstoyan.swapi.swapi.network.services.SearchAnswer

class SearchResultAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    object Statics{
        const val HOLDER_MAN = 0
        const val HOLDER_ERROR = 1
        const val HOLDER_EMPTY = 2
    }

    var data = SearchAnswer<Man>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return when {
            data.error -> Statics.HOLDER_ERROR
            data.result.size == 0 -> Statics.HOLDER_EMPTY
            else -> Statics.HOLDER_MAN
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            Statics.HOLDER_EMPTY -> StaticHolder.Factory.createView(parent, R.layout.layout_search_empty)
            Statics.HOLDER_ERROR -> StaticHolder.Factory.createView(parent, R.layout.layout_search_error)
            Statics.HOLDER_MAN -> ManHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_man, parent, false))
            else -> StaticHolder.Factory.createView(parent, R.layout.layout_search_empty)
        }
    }

    override fun getItemCount(): Int {
        return data.count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ManHolder -> {
                holder.bind(data.result[position])
            }
            is StaticHolder -> {}
        }
    }
}

class ManHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(man: Man){

    }
}

class StaticHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    object Factory{
        fun createView(viewGroup: ViewGroup, layout: Int): RecyclerView.ViewHolder{
            val inflater = LayoutInflater.from(viewGroup.context)
            return StaticHolder(inflater.inflate(layout, viewGroup, false))
        }
    }
}