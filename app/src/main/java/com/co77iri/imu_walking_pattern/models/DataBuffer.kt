package com.co77iri.imu_walking_pattern.models

class DataBuffer(private val address: String) {
    private val MAX_BUFFER_SIZE = 60 * 1
    private var idx = 0

    val totalFreeAccX = ArrayList<Float>()
    val totalFreeAccY = ArrayList<Float>()
    val totalFreeAccZ = ArrayList<Float>()

    private val bufferFreeAccX = FloatArray(MAX_BUFFER_SIZE)
    private val bufferFreeAccY = FloatArray(MAX_BUFFER_SIZE)
    private val bufferFreeAccZ = FloatArray(MAX_BUFFER_SIZE)

    fun addData(x: Float, y: Float, z: Float) {
        bufferFreeAccX[idx] = x
        bufferFreeAccY[idx] = y
        bufferFreeAccZ[idx] = z
        idx++

        if (idx >= MAX_BUFFER_SIZE) {
            saveData()
        }
    }

    private fun saveData() {
        totalFreeAccX.addAll(bufferFreeAccX.toList())
        totalFreeAccY.addAll(bufferFreeAccY.toList())
        totalFreeAccZ.addAll(bufferFreeAccZ.toList())

        // 버퍼 초기화 (선택적)
        bufferFreeAccX.fill(0f)
        bufferFreeAccY.fill(0f)
        bufferFreeAccZ.fill(0f)
        idx = 0
    }
}