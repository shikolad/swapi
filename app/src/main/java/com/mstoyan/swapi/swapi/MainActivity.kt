package com.mstoyan.swapi.swapi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mstoyan.swapi.swapi.db.DBManager
import com.mstoyan.swapi.swapi.network.NetworkManager
import com.mstoyan.swapi.swapi.network.SwObserver
import com.mstoyan.swapi.swapi.network.services.Person
import com.mstoyan.swapi.swapi.network.services.SearchAnswer
import com.mstoyan.swapi.swapi.ui.SearchResultAdapter
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    private val dbManager = DBManager()
    private val peopleSearchObserver = object: SwObserver<SearchAnswer<Person>>() {
        override fun onCachedDataLoaded(
            data: SearchAnswer<Person>,
            call: Call<SearchAnswer<Person>>
        ) {
            onDataLoaded(data, call)
        }

        override fun onDataLoaded(data: SearchAnswer<Person>, call: Call<SearchAnswer<Person>>) {
            searchAdapter.data = data
            dbManager.insertPersons(data.result)
            nextAvailable = data.nextLink != null
            prevAvailable = data.prevLink != null
            (result.layoutManager as LinearLayoutManager).scrollToPosition(0)
        }

        override fun onFailedDataLoading(e: Throwable, call: Call<SearchAnswer<Person>>) {
            val result = SearchAnswer<Person>()
            result.error = true
            searchAdapter.data = result
            nextAvailable = false
            prevAvailable = false
        }

    }

    val searchAdapter = SearchResultAdapter()
    var nextAvailable = false
        set(value){
            field = value
            next.isEnabled = field
        }
    var prevAvailable = false
        set(value) {
            field = value
            prev.isEnabled = field
        }
    var lastPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                //do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                input.postDelayed({
                    lastPage = 1
                    searchInfo()
                }, 1000)
            }
        })

        next.setOnClickListener {
            lastPage++
            searchInfo()
        }

        prev.setOnClickListener{
            lastPage--
            searchInfo()
        }

        result.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        result.adapter = searchAdapter
    }

    private fun searchInfo() {
        val searchString = input.text.toString()
        if (searchString.isEmpty()){
            searchAdapter.setPersons(dbManager.getAllPersons())
        } else {
            NetworkManager.ApiCalls.search(searchString, lastPage)
        }
    }

    override fun onResume() {
        super.onResume()
        NetworkManager.registerPeopleSearchObserver(peopleSearchObserver)
        dbManager.open(this)
        searchInfo()
    }

    override fun onPause() {
        super.onPause()
        NetworkManager.unregisterPeopleSearchObserver(peopleSearchObserver)
        dbManager.close()
    }
}
