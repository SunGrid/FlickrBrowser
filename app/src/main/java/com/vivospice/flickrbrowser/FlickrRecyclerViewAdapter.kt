package com.vivospice.flickrbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FlickrImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)
}

private const val TAG = "FlickrRecyclerViewAdapt" //error on log name if too long

class FlickrRecyclerViewAdapter(private var photoList : List<Photo>) : RecyclerView.Adapter <FlickrImageViewHolder>(){


    override fun getItemCount(): Int {
//        Log.d(TAG, ".getItemCount")
        return if (photoList.isNotEmpty()) photoList.size else 1
    }

    fun loadNewData(newPhotos: List<Photo>) {
        photoList = newPhotos
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int): Photo? {
        return if (photoList.isNotEmpty()) photoList[position] else null
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
         // Called by the layout manager when it wants new data in an existing view

        if (photoList.isEmpty()){
            holder.thumbnail.setImageResource(R.drawable.placeholder_48dp)
            holder.title.setText(R.string.empty_photo)
        } else {
            val photoItem = photoList[position]
            Log.d(TAG, ".onBindViewHolder: ${photoItem.title} -->> $position")
            //  old way: Picasso.with(holder.thumbnail.context).load(photoItem.image)
            Picasso.get()
                .load(photoItem.image)
                .error(R.drawable.placeholder_48dp)
                .placeholder(R.drawable.placeholder_48dp)
                .into(holder.thumbnail)

            holder.title.text = photoItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        //Called by the layout manager when it needs a new view
        Log.d(TAG, ".onCreateViewHolder new view requested")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)  //make sure to pass the  parent view as the parameter.
        return FlickrImageViewHolder(view)
    }
}



/*
 val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
Put false for root in the inflate method, this one here attached to route, that tells the inflater whether to attach, unsurprisingly, the view to it's root
or not. We don't want to do that. The recycler view's going to take care of all that.
In fact that's the reason why the incorrect function's often used. By passing null there's no route to attach to,
so it works, but it's not the correct way to do things. The correct way is to pass the parent, then pass false as the third parameter. So that
tells the inflater not to attach the inflated view to its parent (attaching a view by the way, just means adding it to the parent layout,
pretty much what we do when we drag a widget onto the layout designer).
*/
