package com.mstoyan.swapi.swapi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mstoyan.swapi.swapi.R.id.result
import com.mstoyan.swapi.swapi.network.NetworkManager
import com.mstoyan.swapi.swapi.network.SwObserver
import com.mstoyan.swapi.swapi.network.services.Man
import com.mstoyan.swapi.swapi.network.services.SearchAnswer
import com.mstoyan.swapi.swapi.ui.SearchResultAdapter
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    private val peopleSearchObserver = object: SwObserver<SearchAnswer<Man>>() {
        override fun onCachedDataLoaded(
            data: SearchAnswer<Man>,
            call: Call<SearchAnswer<Man>>
        ) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onDataLoaded(data: SearchAnswer<Man>, call: Call<SearchAnswer<Man>>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onFailedDataLoading(e: Throwable, call: Call<SearchAnswer<Man>>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    val searchAdapter = SearchResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        result.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        result.adapter = searchAdapter
    }

    override fun onResume() {
        super.onResume()
        NetworkManager.registerPeopleSearchObserver(peopleSearchObserver)
    }

    override fun onPause() {
        super.onPause()
        NetworkManager.unregisterPeopleSearchObserver(peopleSearchObserver)
    }
}
