package com.example.ble.ble

object SampleGattAttributes {
    private val attributes: HashMap<String, String> = HashMap()
    var HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb"
    var CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb"

    init {
        // Sample Services.
        SampleGattAttributes.attributes.put(
            "0000180d-0000-1000-8000-00805f9b34fb",
            "Heart Rate Service"
        )
        SampleGattAttributes.attributes.put(
            "0000180a-0000-1000-8000-00805f9b34fb",
            "Device Information Service"
        )
        // Sample Characteristics.
        SampleGattAttributes.attributes.put(
            SampleGattAttributes.HEART_RATE_MEASUREMENT,
            "Heart Rate Measurement"
        )
        SampleGattAttributes.attributes.put(
            "00002a29-0000-1000-8000-00805f9b34fb",
            "Manufacturer Name String"
        )
    }

    fun lookup(uuid: String?, defaultName: String): String {
        val name: String? = SampleGattAttributes.attributes.get(uuid)
        return name ?: defaultName
    }
}