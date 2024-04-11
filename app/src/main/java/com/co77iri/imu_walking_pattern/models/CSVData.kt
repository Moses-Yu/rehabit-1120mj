package com.co77iri.imu_walking_pattern.models

import android.util.Log

data class CSVData(
    val FreeAccX: ArrayList<Double> = ArrayList(),
    val FreeAccY: ArrayList<Double> = ArrayList(),
    val FreeAccZ: ArrayList<Double> = ArrayList()
) {
    fun getDataLength(): Int {
        return FreeAccX.size
    }

    fun getFreeAccSum(): ArrayList<Double> {
        val sumList = ArrayList<Double>(FreeAccX.size)

        for( i in FreeAccX.indices ) {
            sumList.add(FreeAccX[i] + FreeAccY[i] + FreeAccZ[i])
        }

        return sumList
    }

//    fun myFindPeaks(sample: List<Double>, minPeakHeight: Double, minPeakDistance: Int): Pair<List<Double>, List<Int>> {
    fun myFindPeaks(): Pair<List<Double>, List<Int>> {
        val minPeakHeight:Double = 10.0
        val minPeakDistance:Int = 40

        val sample = this.getFreeAccSum()
        val sdSample = sample.zipWithNext{a, b -> b - a }
        val locs = mutableListOf<Int>()
        val pks = mutableListOf<Double>()

        var distance = 0

        for( i in 1 until sample.size - 1 ) {
            if( distance > 0 ) {
                if( i - distance + 1 >= minPeakDistance) {
                    distance = 0
                } else {
                    continue
                }
            }

            if( sdSample[i-1] >= 0 && sdSample[i] < 0 && sample[i] >= minPeakHeight) {
                locs.add(i)
                pks.add(sample[i])
                distance = i
            }
        }

        return Pair(pks, locs)
    }

    fun squaredSumBetweenPeaks(startIdx: Int, endIdx: Int): Double {
        var sum = 0.0
        for (i in startIdx until endIdx) {
            sum += FreeAccX[i] * FreeAccX[i]
            sum += FreeAccY[i] * FreeAccY[i]
            sum += FreeAccZ[i] * FreeAccZ[i]
        }
        return sum
    }
}