package com.example.sapiii

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.sapiii.base.BaseActivity
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ScanActivity : BaseActivity() {
    private val TAG = ScanActivity::class.java.simpleName
    private val CAMERA_REQUEST_CODE = 101
    private var savedInstanceState: Bundle? = null
    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            handleResult(result)
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {}
    }

    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var capture: CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        this.savedInstanceState = savedInstanceState
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        barcodeScannerView.setStatusText("")
        barcodeScannerView.decodeContinuous(callback)

        startOrRequestCameraGranted()
    }

    private fun startOrRequestCameraGranted(): Boolean {
        // check permission for camera
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
            false
        } else {
            startCamera()
            true
        }
    }

    private fun handleResult(result: BarcodeResult?) {
        barcodeScannerView.pause()
        if (result == null) {
            doOnErrorScanner()
            return
        }

        val uri = Uri.parse(result.text)
        Log.d("anu", uri.host.toString())
        if (uri.host != "sapiii.app") {
            doOnErrorScanner()
            return
        }

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun doOnErrorScanner() {
        barcodeScannerView.resume()
        showToast("QR Code tidak diketahui")
        lifecycleScope.launch {
            delay(500)
            barcodeScannerView.resume()
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
                showToast("Camera permission denied")
            }
        }
    }

    private fun startCamera() {
        capture = CaptureManager(this, barcodeScannerView)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode()
    }

    override fun onResume() {
        super.onResume()
        if (startOrRequestCameraGranted()) {
            barcodeScannerView.resume()
            if (savedInstanceState != null) {
                capture.initializeFromIntent(intent, savedInstanceState)
            } else {
                capture.initializeFromIntent(intent, null)
            }
            barcodeScannerView.decodeContinuous(callback)
        }
    }

    override fun onPause() {
        super.onPause()
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
}