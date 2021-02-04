package com.zee.chatApp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.zee.chatApp.view.profile.TAKE_IMAGE
import java.text.SimpleDateFormat
import java.util.*


object MyUtil {
    fun hideKeyBoard(activity: FragmentActivity) {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        }
    }

    fun getDate(): String {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = Date()
        return format.format(date)
    }

    fun getTimeStamp(): String {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("h:mm a")
        val timeStamp = simpleDateFormat.format(calendar.time)
        return timeStamp

    }

    fun isConnectedToInternet(context: Context?):Boolean{
        if (context == null)
            return false
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun checkCameraPermission(activity: FragmentActivity?): Boolean {
        return if (activity!=null){
            ActivityCompat.checkSelfPermission(
                activity.baseContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        }else false
    }

    fun requestCameraPermission(activity: FragmentActivity?){
        if (activity!=null)
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CAMERA), TAKE_IMAGE
        )
    }


}