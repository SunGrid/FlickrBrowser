package com.vivospice.flickrbrowser

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo_details.*


class PhotoDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)

        activateToolbar(true)

     //   val photo = intent.getSerializableExtra(PHOTO_TRANSFER) as Photo
        val photo = intent.getParcelableExtra(PHOTO_TRANSFER) as Photo

//        photo_title.text = photo.title
        photo_title.text = resources.getString(R.string.photo_title_text, photo.title)
//        photo_tags.text = photo.tags
        photo_tags.text = resources.getString(R.string.photo_tags_text, photo.tags)
        photo_author.text = photo.author
//        photo_author.text = resources.getString(R.string.photo_author_text,"Text of 1st place holder", "2nd place holder", "3rd place holder")

        Picasso.get()
            .load(photo.link)
            .error(R.drawable.placeholder_48dp)
            .placeholder(R.drawable.placeholder_48dp)
            .into(photo_image)


        var isImageFitToScreen: Boolean = false
        photo_image.setOnClickListener{

            if(isImageFitToScreen) {
                fullScreen()
                photo_image.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                photo_image.scaleType = ImageView.ScaleType.FIT_CENTER
                photo_image.adjustViewBounds = true
                photo_image.maxHeight = 500
                Toast.makeText(this,"FIT_CENTER, maxHeight = 500", Toast.LENGTH_SHORT).show()
                isImageFitToScreen = false
            } else {
                fullScreen()
                photo_image.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                photo_image.scaleType = ImageView.ScaleType.FIT_XY
                photo_image.adjustViewBounds = false
                Toast.makeText(this,"FIT_XY and FullScreen", Toast.LENGTH_SHORT).show()
                isImageFitToScreen = true
            }
        }
    }

//https://developer.android.com/training/system-ui/immersive.html
    private fun fullScreen(){
// BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        // BEGIN_INCLUDE (get_current_ui_flags)
// The UI options currently enabled are represented by a bitfield.
// getSystemUiVisibility() gives us that bitfield.
        val uiOptions = window.decorView.systemUiVisibility
        var newUiOptions = uiOptions
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        // END_INCLUDE (get_current_ui_flags)
// BEGIN_INCLUDE (toggle_ui_flags)
        val isImmersiveModeEnabled =
            uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
        if (isImmersiveModeEnabled) {
            Log.d("fullScreen", "Turning immersive mode mode off. ")
        } else {
            Log.d("fullScreen", "Turning immersive mode mode on.")
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

        // Status bar hiding: Backwards compatible to Jellybean
        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        // Immersive mode: Backward compatible to KitKat.
// Note that this flag doesn't do anything by itself, it only augments the behavior
// of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
// all three flags are being toggled together.
// Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
// Sticky immersive mode differs in that it makes the navigation and status bars
// semi-transparent, and the UI flag does not get cleared when the user interacts with
// the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }

        window.decorView.systemUiVisibility = newUiOptions
        //END_INCLUDE (set_ui_flags)
    }

}
