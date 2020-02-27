package com.vivospice.flickrbrowser

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "recyclerItemClickListen"  // 23 characters limit in TAG

class RecyclerItemClickListener(context: Context, recyclerView: RecyclerView, private val listener: OnRecyclerClickListener) : RecyclerView.SimpleOnItemTouchListener(){

    interface OnRecyclerClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    // Similar to adding an onClickListener to a button.
    //We're creating an anonymous class that extends a simple on Gesture Listener, then overriding the methods we're interested in.
    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener(){

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d(TAG, ".onSingleTapUp: starts")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            return if (childView != null) {
                Log.d(TAG, ".onSingleTapUp called listener.onItemClick")
                listener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView))
                true
            } else {
                false
            }
        }

        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG, ".onLongPress: starts")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            if (childView != null) {
                Log.d(TAG, ".onLongPress calling listener.onItemLongClick")
                listener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))
            }

        }

//        We may consider using onSingleTapConfirmed rather than on SingleTapUp,
//        and that's because a single tap may be followed by another one to make a double tap.
//        If we'd already responded to the first tap we'd miss the second one.
//        If we were going to allow some other action or a double tap, then onSingleTapConfirmed would definitely
//        be the one to use.
//        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
//            return super.onSingleTapConfirmed(e)
//        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG, ".onInterceptTouchEvent: starts $e")
        val result = gestureDetector.onTouchEvent(e)
        Log.d(TAG, ".onInterceptTouchEvent() returning $result")
     //   return super.onInterceptTouchEvent(rv, e)
        return result
    }
}