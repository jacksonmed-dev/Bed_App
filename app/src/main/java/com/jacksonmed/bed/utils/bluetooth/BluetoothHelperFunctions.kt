package com.jacksonmed.bed.utils.bluetooth

import com.jacksonmed.bed.utils.bluetooth.BluetoothConstants.Companion.FIRST_CHAR
import com.jacksonmed.bed.utils.bluetooth.BluetoothConstants.Companion.LAST_CHAR

class HelperFunctions {
    companion object {
        fun removeBytePadding(byteArray: ByteArray): ByteArray {
            var result = mutableListOf<Byte>()
            for (byte in byteArray) {
                val b: Byte = byte
                val c: Byte = 0x0.toByte()
                if (b == c) {
                    return result.toByteArray()
                }
                result.add(byte)
            }
            return result.toByteArray()
        }

        fun generateBluetoothByteArray(string: String): ByteArray {
            return (FIRST_CHAR + string + LAST_CHAR).toByteArray()
        }
    }
}