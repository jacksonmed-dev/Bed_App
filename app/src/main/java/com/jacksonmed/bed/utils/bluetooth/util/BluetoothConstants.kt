package com.jacksonmed.bed.utils.bluetooth.util

class BluetoothConstants {
    companion object {
        const val TRAILER: String = "*"
//        const val BLUETOOTH_ADDRESS: String = "E4:5F:01:02:81:7F"
//        const val BLUETOOTH_ADDRESS = "E4:5F:01:09:59:C0"
        const val BLUETOOTH_ADDRESS = "DC:A6:32:62:84:A5"
        const val TEST_CHAR_RESPONSE = "@"

        const val MASSAGE_HEADER = "@"
        const val MASSAGE_STOP = "0"
        const val MASSAGE_START = "1"

        const val BED_DATA_RESPONSE_HEADER = "!"
        const val BED_DATA_RESPONSE_AUTOMATIC_HEADER = ")"

        const val BED_STATUS_RESPONSE_HEADER = "&"
        const val INFLATABLE_REGION_HEADER = "%"
        const val INFLATABLE_REGION_STATUS = "2"

        const val PATIENT_STATUS_HEADER = "#"

        const val EMPTY_STRING = ""

        val ALL_HEADERS = arrayOf(
            MASSAGE_HEADER,
            BED_DATA_RESPONSE_HEADER,
            BED_DATA_RESPONSE_AUTOMATIC_HEADER,
            BED_STATUS_RESPONSE_HEADER,
            INFLATABLE_REGION_HEADER,
            PATIENT_STATUS_HEADER
            )
    }
}