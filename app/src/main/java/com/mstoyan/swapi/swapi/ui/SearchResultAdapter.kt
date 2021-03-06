package com.mstoyan.swapi.swapi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.mstoyan.swapi.swapi.R
import com.mstoyan.swapi.swapi.network.services.Person
import com.mstoyan.swapi.swapi.network.services.SearchAnswer

class SearchResultAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    object Statics{
        const val HOLDER_MAN = 0
        const val HOLDER_ERROR = 1
        const val HOLDER_EMPTY = 2
    }

    var data = SearchAnswer<Person>()
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
        return when {
            data.error -> 1
            data.result.size == 0 -> 1
            else -> data.result.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ManHolder -> {
                holder.bind(data.result[position])
            }
            is StaticHolder -> {}
        }
    }

    fun setPersons(people: List<Person>){
        val answer = SearchAnswer<Person>()
        answer.count = people.size
        answer.nextLink = null
        answer.prevLink = null
        answer.error = false
        answer.result.addAll(people)
        data = answer
    }
}

class ManHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name = itemView.findViewById<AppCompatTextView>(R.id.name)!!
    private val height = itemView.findViewById<AppCompatTextView>(R.id.height)!!
    private val mass = itemView.findViewById<AppCompatTextView>(R.id.mass)!!
    private val hairColor = itemView.findViewById<AppCompatTextView>(R.id.hair_color)!!
    private val skinColor = itemView.findViewById<AppCompatTextView>(R.id.skin_color)!!
    private val eyeColor = itemView.findViewById<AppCompatTextView>(R.id.eye_color)!!
    private val birthYear = itemView.findViewById<AppCompatTextView>(R.id.birth_year)!!
    private val gender = itemView.findViewById<AppCompatTextView>(R.id.gender)!!
    fun bind(person: Person){
        name.text = person.name
        height.text = person.height
        mass.text = person.mass
        hairColor.text = person.hair_color
        skinColor.text = person.skin_color
        eyeColor.text = person.eye_color
        birthYear.text = person.birth_year
        gender.text = person.gender
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