package com.jacksonmed.bed.utils

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
    }
}