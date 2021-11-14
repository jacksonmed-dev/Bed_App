package com.jacksonmed.bed.utils.bluetooth

import android.os.Message
import androidx.lifecycle.MutableLiveData
import com.jacksonmed.bed.api.BluetoothResponse
import com.jacksonmed.bed.utils.bluetooth.HelperFunctions.Companion.generateBluetoothByteArray
import org.junit.Before
import org.junit.Test

internal class BluetoothViewModelTest {
    private lateinit var bluetoothViewModel: BluetoothViewModel

    @Before
    fun setup(){
       bluetoothViewModel = BluetoothViewModel()
    }

    @Test
    fun handleBluetoothResponseBedStatus() {
        val jsonString: String = "&{\"gpio_pins\": [{\"gpio_pin\": 0, \"state\": 1}, {\"gpio_pin\": 1, \"state\": 1}, {\"gpio_pin\": 2, \"state\": 1}, {\"gpio_pin\": 3, \"state\": 1}, {\"gpio_pin\": 4, \"state\": 1}, {\"gpio_pin\": 5, \"state\": 1}, {\"gpio_pin\": 6, \"state\": 1}, {\"gpio_pin\": 7, \"state\": 1}, {\"gpio_pin\": 8, \"state\": 1}, {\"gpio_pin\": 9, \"state\": 1}, {\"gpio_pin\": 10, \"state\": 1}, {\"gpio_pin\": 11, \"state\": 1}, {\"gpio_pin\": 12, \"state\": 1}, {\"gpio_pin\": 13, \"state\": 1}, {\"gpio_pin\": 14, \"state\": 1}, {\"gpio_pin\": 15, \"state\": 1}, {\"gpio_pin\": 16, \"state\": 1}, {\"gpio_pin\": 17, \"state\": 1}, {\"gpio_pin\": 18, \"state\": 1}, {\"gpio_pin\": 19, \"state\": 1}]}*"
        bluetoothViewModel.bluetoothString = jsonString
        bluetoothViewModel.handleBluetoothResponse(jsonString)
    }


}