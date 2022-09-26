package com.EcommerceApplication.data.models.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class convertToBit {



        fun getBitmapFromURL(src: String?) {
            var image: Bitmap? = null
           CoroutineScope(Job() + Dispatchers.IO).launch {
               try {
                   val url = URL(src)
                   val bitMap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                   image = Bitmap.createScaledBitmap(bitMap, 100, 100, true)
               } catch (e: IOException) {
                   // Log exception
               }
           }

        }

    fun getBitmap(url : String?) : Bitmap? {
        var bmp : Bitmap ? = null
        Picasso.get().load(url).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bmp =  bitmap
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
        })
        return bmp
    }
}