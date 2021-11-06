package com.jacksonmed.bed.utils.bluetooth.service

import android.bluetooth.BluetoothSocket

interface BluetoothResult {
     fun processFinish(m_bluetoothSocket: BluetoothSocket)
}