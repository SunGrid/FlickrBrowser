package com.vivospice.flickrbrowser

import android.os.Bundle
import android.util.Log

private const val TAG = "SearchActivity"

class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activateToolbar(true)
        Log.d(TAG, ".onCreate: ends")
    }

}
