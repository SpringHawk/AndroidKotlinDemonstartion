package com.circularfashion

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.circularfashion.Utils.CustomAdapter
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView


class ReturnGuideActivity: AppCompatActivity() {

    private var mapView: MapView? = null
    private var locationManager: LocationManager? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.access_token))
        setContentView(R.layout.activity_return_map)

        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager

        viewPager.adapter = CustomAdapter(this, supportFragmentManager)
        val tabLayout = findViewById<View>(R.id.sliding_tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                this@ReturnGuideActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@ReturnGuideActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this@ReturnGuideActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    122
                )
            }
        }
    }
}