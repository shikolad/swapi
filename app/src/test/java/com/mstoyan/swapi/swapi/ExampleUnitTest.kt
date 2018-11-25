package com.mstoyan.swapi.swapi

import com.mstoyan.swapi.swapi.network.NetworkManager
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkSearchResult(){
        val call = NetworkManager.swApiService.searchPeopleByName("Darth Vader")
        val response = call.execute()
        assertNotNull(response.body())
        assertEquals(response.body()!!.count, 1)
        assertEquals(response.body()!!.result!!.size, 1)

    }
}
