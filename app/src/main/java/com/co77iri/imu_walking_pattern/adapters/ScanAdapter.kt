////  Copyright (c) 2003-2020 Movella Technologies B.V. or subsidiaries worldwide.
////  All rights reserved.
////
////  Redistribution and use in source and binary forms, with or without modification,
////  are permitted provided that the following conditions are met:
////
////  1.      Redistributions of source code must retain the above copyright notice,
////           this list of conditions, and the following disclaimer.
////
////  2.      Redistributions in binary form must reproduce the above copyright notice,
////           this list of conditions, and the following disclaimer in the documentation
////           and/or other materials provided with the distribution.
////
////  3.      Neither the names of the copyright holders nor the names of their contributors
////           may be used to endorse or promote products derived from this software without
////           specific prior written permission.
////
////  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
////  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
////  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
////  THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
////  SPECIAL, EXEMPLARY OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
////  OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
////  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY OR
////  TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
////  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.THE LAWS OF THE NETHERLANDS
////  SHALL BE EXCLUSIVELY APPLICABLE AND ANY DISPUTES SHALL BE FINALLY SETTLED UNDER THE RULES
////  OF ARBITRATION OF THE INTERNATIONAL CHAMBER OF COMMERCE IN THE HAGUE BY ONE OR MORE
////  ARBITRATORS APPOINTED IN ACCORDANCE WITH SAID RULES.
////
//package com.co77iri.imu_walking_pattern.adapters
//
//import android.Manifest
////import com.xsens.dot.android.example.interfaces.SensorClickInterface
//import androidx.recyclerview.widget.RecyclerView
////import com.xsens.dot.android.example.adapters.ScanAdapter.ScanViewHolder
//import android.view.ViewGroup
//import android.view.LayoutInflater
////import com.xsens.dot.android.example.R
//import android.bluetooth.BluetoothDevice
//import android.content.Context
////import com.xsens.dot.android.example.adapters.ScanAdapter
//import androidx.core.app.ActivityCompat
//import android.content.pm.PackageManager
//import android.view.View
//import com.xsens.dot.android.sdk.models.XsensDotDevice
//import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import android.widget.TextView
//import com.co77iri.imu_walking_pattern.R
//import com.co77iri.imu_walking_pattern.interfaces.SensorClickInterface
//import java.util.ArrayList
//import java.util.HashMap
//
///**
// * A view adapter for item view of scanned BLE device.
// */
//class ScanAdapter
///**
// * Default constructor.
// *
// * @param context           The application context
// * @param scannedSensorList The scanned devices list
// */(// The application context
//    private val mContext: Context, // Put all scanned devices into one list
//    private val mSensorList: ArrayList<HashMap<String, Any>>?
//) : RecyclerView.Adapter<ScanAdapter.ScanViewHolder>() {
//    // Send the click event to fragment
//    private var mListener: SensorClickInterface? = null
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBindViewHolder(holder: ScanViewHolder, index: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        return mSensorList?.size ?: 0
//    }
//
//    /**
//     * Get the Bluetooth device.
//     *
//     * @param position The position of item view
//     * @return The scanned Bluetooth device
//     */
//    fun getDevice(position: Int): BluetoothDevice? {
//        return if (mSensorList != null) {
//            mSensorList.get(position).get(KEY_DEVICE) as BluetoothDevice?
//        } else null
//    }
//
//    /**
//     * Get the connection state of device.
//     *
//     * @param position The position of item view
//     * @return The connection state
//     */
//    fun getConnectionState(position: Int): Int {
//        return if (mSensorList != null) {
//            mSensorList.get(position).get(KEY_CONNECTION_STATE) as Int
//        } else XsensDotDevice.CONN_STATE_DISCONNECTED
//    }
//
//    /**
//     * Update the connection state to list.
//     *
//     * @param position The position of item view
//     * @param state    The connection state
//     */
//    fun updateConnectionState(position: Int, state: Int) {
//        if (mSensorList != null) {
//            mSensorList.get(position)[KEY_CONNECTION_STATE] = state
//        }
//    }
//
//    /**
//     * Update tag name to the list.
//     *
//     * @param address The mac address of device
//     * @param tag     The device tag
//     */
//    fun updateTag(address: String, tag: String) {
//        if (mSensorList != null) {
//            for (map: HashMap<String, Any> in mSensorList) {
//                val device = map[KEY_DEVICE] as BluetoothDevice?
//                if (device != null) {
//                    val _address = device.address
//                    if ((_address == address)) {
//                        map[KEY_TAG] = tag
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Update battery information to the list.
//     *
//     * @param address    The mac address of device
//     * @param state      This state can be one of BATT_STATE_NOT_CHARGING or BATT_STATE_CHARGING
//     * @param percentage The range of battery level is 0 to 100
//     */
//    fun updateBattery(address: String, state: Int, percentage: Int) {
//        if (mSensorList != null) {
//            for (map: HashMap<String, Any> in mSensorList) {
//                val device = map[KEY_DEVICE] as BluetoothDevice?
//                if (device != null) {
//                    val _address = device.address
//                    if ((_address == address)) {
//                        map[KEY_BATTERY_STATE] = state
//                        map[KEY_BATTERY_PERCENTAGE] = percentage
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Initialize click listener of item view.
//     *
//     * @param listener The fragment which implemented SensorClickInterface
//     */
//    fun setSensorClickListener(listener: SensorClickInterface?) {
//        mListener = listener
//    }
//
//    companion object {
//        private val TAG = ScanAdapter::class.java.simpleName
//
//        // The keys of HashMap
//        @JvmField
//        val KEY_DEVICE = "device"
//        @JvmField
//        val KEY_CONNECTION_STATE = "state"
//        @JvmField
//        val KEY_TAG = "tag"
//        @JvmField
//        val KEY_BATTERY_STATE = "battery_state"
//        @JvmField
//        val KEY_BATTERY_PERCENTAGE = "battery_percentage"
//    }
//}