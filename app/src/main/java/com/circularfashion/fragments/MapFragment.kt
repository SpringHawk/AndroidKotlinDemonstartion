package com.circularfashion.fragments


import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.circularfashion.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import kotlinx.android.synthetic.main.view_map.view.*

class MapFragment : Fragment() {

    private var locationManager: LocationManager? = null
    private var mapView: MapView? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(activity!!.application, getString(R.string.access_token))
        val view = inflater.inflate(R.layout.view_map, container, false)
        mapView = view.mapView

        locationManager =
                activity?.application!!.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)

        // Inflate the layout for this fragment
        return view
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        @SuppressLint("MissingPermission")
        override fun onLocationChanged(location: Location) {
            val position = CameraPosition.Builder()
                .target(LatLng(location.latitude, location.longitude)) // Sets the new camera position
                .zoom(15.0) // Sets the zoom to level 10
                .tilt(20.0) // Set the camera tilt to 20 degrees
                .build()

            mapView!!.getMapAsync { mapboxMap ->
                mapboxMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(location.latitude + 0.05, location.longitude + 0.05))
                        .title(getString(R.string.draw_marker_options_title))
                        .snippet(getString(R.string.draw_marker_options_snippet))
                )
                mapboxMap.cameraPosition = position
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}

    }
}
