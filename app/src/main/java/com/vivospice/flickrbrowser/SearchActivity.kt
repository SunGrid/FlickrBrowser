package com.vivospice.flickrbrowser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
//import android.preference.PreferenceManager
import androidx.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.widget.SearchView  //https://developer.android.com/reference/androidx/appcompat/widget/SearchView

//import androidx.appcompat.widget.SearchView //

private const val TAG = "SearchActivity"
class SearchActivity : BaseActivity() {

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activateToolbar(true)
        Log.d(TAG, ".onCreate: ends")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {   //https://developer.android.com/guide/topics/search/
        Log.d(TAG, ".onCreateOptionsMenu: starts")
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView  //reference now to the search view widget that's embedded in the search menu item of the toolbar.
        val searchableInfo = searchManager.getSearchableInfo(componentName)  //getting the search manager to retrieve the searchable info from searchable.xml, by calling it's getSearchableInfo function.
        searchView?.setSearchableInfo(searchableInfo)  //need to provide getSearchableInfo with the component name of the activity that we want the information for ...
                                                    // That's search activity here, and you could argue that it would make more sense to pass an activity rather than a component name, ...
                                                    //  but it wants a component name so that's what we're going to give it. Now that searchable info is then set into the search view widget to configure it.
//        Log.d(TAG, ".onCreateOptionsMenu: component name is: $componentName")
//        Log.d(TAG, ".onCreateOptionsMenu: hint is: ${searchView?.queryHint}")
//        Log.d(TAG, ".onCreateOptionsMenu: searchableInfo is: $searchableInfo")

        searchView?.isIconified = false

        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, ".onQueryTextSubmit: called ")

                val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                sharedPref.edit().putString(FLICKR_QUERY, query).apply()
                searchView?.clearFocus() //for external keyboard

                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        searchView?.setOnCloseListener {
            finish()
            false
        }
        Log.d(TAG, ".onCreateOptionsMenu: returning")
        return true
    }
}
