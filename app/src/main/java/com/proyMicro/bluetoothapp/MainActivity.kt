package com.proyMicro.bluetoothapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import Bluetooth
import android.bluetooth.BluetoothAdapter
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var bluetooth: Bluetooth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetooth = Bluetooth(this)

        findViewById<Button>(R.id.btn_connect).setOnClickListener {
            val pairedDevices = BluetoothAdapter.getDefaultAdapter()?.bondedDevices ?: emptySet()
            val device = pairedDevices.find { it.name == "ModuloContador" } ?: return@setOnClickListener
            bluetooth.connect(device)
        }

        findViewById<Button>(R.id.btn_disconnect).setOnClickListener {
            bluetooth.disconnect()
        }
    }

    fun displayJsonData(jsonData: String) {
        try {
            val jsonObject = JSONObject(jsonData)
            val keys = jsonObject.keys()
            val stringBuilder = StringBuilder()

            while (keys.hasNext()) {
                val key = keys.next()
                val value = jsonObject.getInt(key)

                stringBuilder.append("$key: $value\n")
            }
            findViewById<TextView>(R.id.tv_json_data).text = stringBuilder.toString()
            //textView.text = stringBuilder.toString()
        } catch (e: JSONException) {
            Log.e("MainActivity", "Error parsing JSON", e)
        }
    }

}