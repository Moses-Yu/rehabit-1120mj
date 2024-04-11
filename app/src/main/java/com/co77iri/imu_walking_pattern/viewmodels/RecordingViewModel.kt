package com.co77iri.imu_walking_pattern.viewmodels

import android.app.Application
import android.content.Context
import android.location.Address
import android.nfc.Tag
import android.os.SystemClock
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.work.ListenableWorker.Result.Success
import com.co77iri.imu_walking_pattern.models.XsRecordingFileInfo
import com.xsens.dot.android.sdk.interfaces.XsensDotSyncCallback
import com.xsens.dot.android.sdk.models.XsensDotDevice
import com.xsens.dot.android.sdk.models.XsensDotPayload
import com.xsens.dot.android.sdk.models.XsensDotSyncManager
import com.xsens.dot.android.sdk.recording.XsensDotRecordingManager

class RecordingViewModel(application: Application) : AndroidViewModel(application) {
    private val context: Context = getApplication<Application>()

    private var isRecording: Boolean = false
    private var recordingManagerMap: HashMap<String, RecordingData> = HashMap()

    private fun startRecording() {
        for((address, data) in recordingManagerMap) {
            data?.let {
                SystemClock.sleep(30)
                var success = data.recordingManager.startRecording()
                if( !success ) {
                    SystemClock.sleep(40)
                    data.recordingManager.startRecording()
                }
            }
        }
        isRecording = true
    }

    private fun stopRecording() {
        for((address, data) in recordingManagerMap) {
            data?.let{
                data.recordingManager.stopRecording()
            }
        }
        isRecording = false
    }

    private fun clearRecordingManagers() {
        for ((_, manager) in recordingManagerMap) {
            manager.recordingManager.clear()
        }
        recordingManagerMap.clear()
    }

    private fun checkIfCanStartRecording() {
        var canStart = true
        for ((address, data) in recordingManagerMap) {
            data?.let {
                if (!it.canRecord) {
                    canStart = false
                }
            }
        }
        if (canStart) {
            startRecording()
        }
    }

    companion object {
        private val TAG = RecordingViewModel::class.java.simpleName
        private val LOCKER = Any()
        private const val SYNCING_REQUEST_CODE = 1001

    }
}

data class RecordingData(
    var device: XsensDotDevice,
    var canRecord: Boolean,
    var isNotificationEnabled: Boolean = false,
    var recordingManager: XsensDotRecordingManager,
    var isRecording: Boolean = false
) {
    var recordingFileInfoList: ArrayList<XsRecordingFileInfo> = ArrayList()
}