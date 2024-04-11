package com.co77iri.imu_walking_pattern.interfaces

interface ScanClickInterface {
    /**
     * This function will be triggered when the start/stop scanning button is clicked.
     *
     * @param started The status of scanning
     */
    fun onScanTriggered(started: Boolean)
}