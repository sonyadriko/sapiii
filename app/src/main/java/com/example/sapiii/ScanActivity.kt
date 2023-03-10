package com.example.sapiii

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class ScanActivity : AppCompatActivity(), DecoratedBarcodeView.TorchListener {
    private val TAG = ScanActivity::class.java.simpleName
    private val CAMERA_REQUEST_CODE = 101
    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var capture: CaptureManager
    private var savedInstanceState: Bundle? = null
    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            // handle barcode result here
            result?.let {
                Log.d(TAG, "Barcode Result: ${it.text}")
            }
            // restart scanning
            barcodeScannerView.decodeSingle(this)
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        this.savedInstanceState = savedInstanceState
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        barcodeScannerView.setStatusText("")
        barcodeScannerView.setTorchListener(this)
        barcodeScannerView.decodeContinuous(callback)

        // check permission for camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        } else {
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Log.e(TAG, "Camera permission denied")
            }
        }
    }

    private fun startCamera() {
        capture = CaptureManager(this, barcodeScannerView)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode()
    }

    override fun onTorchOn() {
    }

    override fun onTorchOff() {
    }

    fun onTorchStateChanged(state: Boolean) {}

    override fun onResume() {
        super.onResume()
        capture.onResume()
        barcodeScannerView.resume()
        if (savedInstanceState != null) {
            capture.initializeFromIntent(intent, savedInstanceState)
        } else {
            capture.initializeFromIntent(intent, null)
        }
        barcodeScannerView.decodeContinuous(callback)
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
        barcodeScannerView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
//
//    fun handleResult(p0: Result?) {
//        // handle barcode result here
//        Log.d(TAG, "Result: ${p0?.text}")
//        // restart scanning
//        barcodeScannerView.decodeSingle(callback)
//    }
//
//    fun handleResult(result: Result) {
//        // do something with the result
//        Log.d(TAG, result.text)
//        onBackPressed()
//    }
}