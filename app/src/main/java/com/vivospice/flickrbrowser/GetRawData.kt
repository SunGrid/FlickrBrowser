package com.vivospice.flickrbrowser

import android.os.AsyncTask
import android.util.Log
import android.view.ViewTreeObserver
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK, IDLE, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

private const val TAG = "GetRawData"

class GetRawData(private val listener: OnDownloadComplete) : AsyncTask<String, Void, String>() {
    private var downloadStatus = DownloadStatus.IDLE

    interface OnDownloadComplete {
        fun onDownloadComplete(data: String, status: DownloadStatus)
    }

//    private var listener: MainActivity? = null
//    fun setDownloadCompleteListener(callbackObject: MainActivity) {
//        listener = callbackObject
//    }

    override fun onPostExecute(result: String) {
        Log.d(TAG, "onPostExecute called")
        listener.onDownloadComplete(result, downloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            downloadStatus= DownloadStatus.NOT_INITIALISED
            return "no URL specified"
        }

        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch ( e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInBackground: Invalid URL ${e.message}"
                }
                is IOException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInBackground: IO exception reading data: ${e.message}"
                }
                is SecurityException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInBackground: Security exception: needs permission? ${e.message}"
                } else -> {
                    downloadStatus = DownloadStatus.ERROR
                    "unknown error: ${e.message}"
                }
            }
            Log.e(TAG, errorMessage)
            return errorMessage
        }

    }
}