# ScanFragment

## OnCreate

### scanAdapter 정의

com.xsens.dot.android.example.adapters.ScanAdapter  
A view adapter for item view of scanned BLE device.

```
public constructor ScanAdapter(
val mContext: Context,
val mSensorList: ArrayList<HashMap<String, Any>>?
)
```

### scanAdapter.setSensorClickListener 정의

Initialize click listener of item view.

```
public final fun setSensorClickListener(
    listener: SensorClickInterface?
): Unit
```

Params:  
listener - The fragment which implemented SensorClickInterface

## onScanTriggered

모든 스캔 해제

```
mSensorViewModel!!.disconnectAllSensors()
mSensorViewModel!!.removeAllDevice()
mScannedSensorList.clear()
mScanAdapter!!.notifyDataSetChanged()
```

블루투스 스캔

```
// Android S 이상인 경우 권한체크 후 실행
ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.BLUETOOTH_SCAN), 1000)

// 버전이 Android S 미만인 경우
mIsScanning = if (mXsDotScanner == null) false else mXsDotScanner!!.startScan()
```

ScanState 변경

```
mBluetoothViewModel!!.updateScanState(mIsScanning)
```

스캔 중지

```
mIsScanning = !mXsDotScanner!!.stopScan()
```

com.xsens.dot.android.sdk.interfaces
