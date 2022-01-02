package com.jacksonmed.bed.utils.bluetooth.service

import android.bluetooth.BluetoothSocket
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BluetoothServiceEntryPoint {
     val bluetoothService: MyBluetoothService
}