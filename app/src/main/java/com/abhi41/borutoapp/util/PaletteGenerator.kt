

package com.abhi41.borutoapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {

    suspend fun convertImageToBitmap(
        imageUrl: String,
        context: Context
    ): Bitmap? {
        val loader = ImageLoader(context = context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val imageResult = loader.execute(request)

        if (imageResult is SuccessResult) {
            return (imageResult.drawable as BitmapDrawable).bitmap
        } else {
            return null
        }

    }

    fun extractColorsFromBitmap(bitmap: Bitmap): Map<String, String>{
        return mapOf(
            "vibrant" to parseColorSwatch(color = Palette.from(bitmap).generate().vibrantSwatch),
            "darkVibrant" to parseColorSwatch(
                color = Palette.from(bitmap).generate().darkVibrantSwatch
            ),
            "onDarkVibrant" to parseBodyColor(
                color = Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor
            )
        )

    }

    private fun parseColorSwatch(color: Palette.Swatch?): String{
        if (color != null){
            val parsedColor = Integer.toHexString(color.rgb)
            return "#$parsedColor"
        }else{
            return "#000000"  //in case of null we return white color
        }
    }

    private fun parseBodyColor(color: Int?): String{
        if (color != null){
            val parsedColor = Integer.toHexString(color)
            return "#$parsedColor"
        }else{
            //why we return white color because on black bg color white color is suitable
            return "#FFFFFF"
        }
    }

}