package com.co77iri.imu_walking_pattern.viewmodels

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xsens.dot.android.sdk.utils.XsensDotScanner
import com.xsens.dot.android.sdk.interfaces.XsensDotScannerCallback
import com.xsens.dot.android.sdk.models.XsensDotDevice

@SuppressLint("MutableCollectionMutableState")
class BluetoothViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context: Context = getApplication<Application>().applicationContext
    private val isBluetoothEnabled = MutableLiveData<Boolean>()
    var isScanning: MutableState<Boolean> = mutableStateOf(false)
    val scannedSensorList by mutableStateOf(mutableStateListOf<HashMap<String, Any>>())

    private val scannerCallback = object : XsensDotScannerCallback {
        override fun onXsensDotScanned(device: BluetoothDevice, rssi: Int) {
            // 스캔 결과 처리
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("Permission Error", "Permission Error! - BluetoothViewModel")
                return
            }
//            Log.i("BLE", "onXsensDotScanned() - Name: " + device.name + ", Address: " + device.address)

            var isExist = false
            for (savedDeviceMap in scannedSensorList) {
                val savedDevice = savedDeviceMap["device"] as? BluetoothDevice
                if( savedDevice != null && savedDevice.address == device.address ) {
                    isExist = true
                    break
                }
            }

            if ( !isExist ) {
                val map = HashMap<String, Any>()
                map["device"] = device                                  // KEY_DEVICE = "device"
                map["state"] = XsensDotDevice.CONN_STATE_DISCONNECTED   // KEY_CONNECTION_STATE = "state"
                map["tag"] = ""                                         // KEY_TAG = "tag"
                map["battery_state"] = -1                               // KEY_BATTERY_STATE = "battery_state"
                map["battery_percentage"] = -1                          // KEY_BATTERY_PERCENTAGE = "battery_percentage"
                scannedSensorList.add(map)
                Log.d("Bluetooth", "Sensor " + device.address + " added.")
            }
        }
    }
    private val scanner: XsensDotScanner = XsensDotScanner(context, scannerCallback)



    init {
        initXsDotScanner()
    }

    fun startScan() {
        isScanning.value = true
        scanner.startScan()
        Log.d("Bluetooth", "scan started!")
    }

    fun stopScan() {
        isScanning.value = false
        scanner.stopScan()
        Log.d("Bluetooth", "scan stopped!")
    }

    fun updateBluetoothEnableState(enabled: Boolean) {
       isBluetoothEnabled.postValue(enabled)
    }

    private fun initXsDotScanner() {
        scanner.setScanMode(ScanSettings.SCAN_MODE_BALANCED)
    }

}