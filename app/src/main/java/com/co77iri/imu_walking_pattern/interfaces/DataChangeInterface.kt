package com.co77iri.imu_walking_pattern.interfaces

import com.xsens.dot.android.sdk.events.XsensDotData

interface DataChangeInterface {
    /**
     * This function will be triggered when data is changed.
     *
     * @param address The mac address of device
     * @param data    The XsensDotData packet
     */
    fun onDataChanged(address: String?, data: XsensDotData?)
}