package com.vivospice.flickrbrowser

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

private val TAG = "BaseActivity"
internal const val FLICKR_QUERY = "FLICK_QUERY"
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

//Now the function will be used to show the toolbar, and it'll also allow an activity to choose whether the
//toolbar should have the home button or not.
//Now main activity won't need a home button because it is the home screen, but the other activities will.
//Chose androidx ToolBar??
    internal fun activateToolbar(enableHome: Boolean){
        Log.d(TAG, ".activationToolbar")

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)

    }
}