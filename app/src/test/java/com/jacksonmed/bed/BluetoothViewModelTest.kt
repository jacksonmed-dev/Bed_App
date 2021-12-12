package com.jacksonmed.bed.utils.bluetooth

import android.os.Message
import androidx.lifecycle.MutableLiveData
import com.jacksonmed.bed.api.BluetoothResponse
import com.jacksonmed.bed.utils.bluetooth.HelperFunctions.Companion.generateBluetoothByteArray
import com.jacksonmed.bed.utils.bluetooth.util.BluetoothConstants.Companion.BED_STATUS_RESPONSE_HEADER
import com.jacksonmed.bed.utils.bluetooth.util.BluetoothConstants.Companion.TRAILER
import org.junit.Assert
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

    @Test
    fun checkDataFormatTest1(){
        //Good Data Example
        val testResponse: String = BED_STATUS_RESPONSE_HEADER + "{\"gpio_pins\": [{\"gpio_pin\": 0}]}" + TRAILER
        val result = bluetoothViewModel.checkDataFormat(testResponse)
        Assert.assertEquals(true, result)
    }

    @Test
    fun checkDataFormatTest2(){
        //Good Data Example. Represents the last chunk in bluetooth transmission
        val testResponse: String = "{\"gpio_pins\": [{\"gpio_pin\": 0}]}" + TRAILER
        val result = bluetoothViewModel.checkDataFormat(testResponse)
        Assert.assertEquals(true, result)
    }

    @Test
    fun checkDataFormatTest3(){
        //Bad data. trailer followed by header
        val testResponse: String = TRAILER + BED_STATUS_RESPONSE_HEADER + "{\"gpio_pins\": [{\"gpio_pin\": 0}]}" + TRAILER
        val result = bluetoothViewModel.checkDataFormat(testResponse)
        Assert.assertEquals(false, result)
    }

    @Test
    fun checkDataFormatTest4(){
        //Bad data. Contains two separate complete objects.
        val testResponse: String = BED_STATUS_RESPONSE_HEADER + "{\"gpio_pins\": [{\"gpio_pin\": 0}]}" + TRAILER + BED_STATUS_RESPONSE_HEADER + "{\"gpio_pins\": [{\"gpio_pin\": 0}]}" + TRAILER
        val result = bluetoothViewModel.checkDataFormat(testResponse)
        Assert.assertEquals(false, result)
    }

    @Test
    fun reformatBadDataTest1() {
        val testResponse: String = BED_STATUS_RESPONSE_HEADER + "{\"gpio_pins\": [{\"gpio_pin\": 0}]}" + TRAILER + BED_STATUS_RESPONSE_HEADER + "{\"gpio_pins\": [{\"gpio_pin\": 0}]}" + TRAILER
        val correctResult: List<String> = listOf(
            BED_STATUS_RESPONSE_HEADER + "{\"gpio_pins\": [{\"gpio_pin\": 0}]}" + TRAILER,
            BED_STATUS_RESPONSE_HEADER + "{\"gpio_pins\": [{\"gpio_pin\": 0}]}" + TRAILER)

        val result: List<String> = bluetoothViewModel.reformatBadData(testResponse)
        Assert.assertEquals(2, result.size)
        Assert.assertEquals(correctResult, result)
    }


}