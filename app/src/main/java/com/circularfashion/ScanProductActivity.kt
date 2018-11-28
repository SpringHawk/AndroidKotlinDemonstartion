package com.circularfashion

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.circularfashion.qr_scanner.BarcodeCaptureActivity
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode


class ScanProductActivity : AppCompatActivity() {

    private val REQUEST_ACCESS_CAMERA = 214
    private val BARCODE_READER_REQUEST_CODE = 215



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_product2)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_ACCESS_CAMERA
                )

                // MY_PERMISSIONS_REQUEST_CAMERA is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            checkCameraHardware(this)
        }
    }

    private fun checkCameraHardware(context: Context): Boolean {
        return if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            startQRScanner()
            true
        } else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun startQRScanner() {
        val intent = Intent(applicationContext, BarcodeCaptureActivity::class.java)
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_ACCESS_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    checkCameraHardware(this)
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    val barcode = data.getParcelableExtra<Barcode>(BarcodeCaptureActivity.BarcodeObject)
                    val p = barcode.cornerPoints
                    Log.e("Value", barcode.displayValue)
                } else
                    Log.e("Value", "no barcode captured")
            } else
                Log.e("Value", "Wrong format")
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }
}
