package com.co77iri.imu_walking_pattern.interfaces

interface BatteryChangedInterface {
    /**
     * This function will be triggered when the battery information of sensor is changed.
     *
     * @param address    The mac address of device
     * @param status     This state can be one of BATT_STATE_NOT_CHARGING or BATT_STATE_CHARGING
     * @param percentage The range of battery level is 0 to 100
     */
    fun onBatteryChanged(address: String?, status: Int, percentage: Int)
}