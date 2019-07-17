package com.app.simpleplantquiz.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class helperfun {

    companion object {

        val PLANPLACES_COM = "http://www.plantplaces.com"

        fun checkForInternetConnection(context: Context): Boolean {

            val connectivityManager: ConnectivityManager = context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val isDeviceConnectedToInternet = connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected

            return isDeviceConnectedToInternet
        }

        fun downloadJSONDataFromLink(link: String): String {
            val stringBuilder: StringBuilder = StringBuilder()
            val url: URL = URL(link)
            val urlConnection = url.openConnection() as HttpURLConnection

            try {
                val bufferedInputStream: BufferedInputStream = BufferedInputStream(urlConnection.inputStream)
                val bufferedReader: BufferedReader = BufferedReader(InputStreamReader(bufferedInputStream))
                // temporary string to hold each line read from the bufferedReader
                var inputLineString: String? = bufferedReader.readLine()
                while (inputLineString != null) {
                    stringBuilder.append(inputLineString)
                    inputLineString = bufferedReader.readLine()
                }
            } catch (e:Exception){
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
            return stringBuilder.toString()
        }

        fun downloadPlanPicture(pictureName: String?): Bitmap? {

            var bitmap: Bitmap? = null
            val pictureLink: String = PLANPLACES_COM + "/photos/$pictureName"

            val pictureURL = URL(pictureLink)
            val inputStream = pictureURL.openConnection().getInputStream()
            if(inputStream != null) {
                bitmap = BitmapFactory.decodeStream(inputStream)
            }

            return bitmap
        }
    }
}