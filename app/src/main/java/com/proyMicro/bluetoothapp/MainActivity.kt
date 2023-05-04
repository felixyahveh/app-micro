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
        //findViewById<TextView>(R.id.tv_json_data).text = jsonData
        try {
            val jsonObject = JSONObject(jsonData)
            findViewById<TextView>(R.id.tv_json_data).text = jsonObject.toString()
            findViewById<TextView>(R.id.mod1).text = jsonObject.getString("1")
            findViewById<TextView>(R.id.mod2).text = jsonObject.getString("2")
            findViewById<TextView>(R.id.mod5).text = jsonObject.getString("5")
            findViewById<TextView>(R.id.mod10).text = jsonObject.getString("10")
            findViewById<TextView>(R.id.total1).text = jsonObject.getString("1")
            findViewById<TextView>(R.id.total2).text = (jsonObject.getString("2").toInt() * 2).toString()
            findViewById<TextView>(R.id.total5).text = (jsonObject.getString("5").toInt() * 5).toString()
            findViewById<TextView>(R.id.total10).text = (jsonObject.getString("10").toInt() * 10).toString()
            val keys = jsonObject.keys()
            /*val stringBuilder = StringBuilder()

            while (keys.hasNext()) {
                val key = keys.next()
                val value = jsonObject.getInt(key)

                stringBuilder.append("$key: $value\n")
            }

            findViewById<TextView>(R.id.tv_json_data).text = jsonObject.toString()
            Log.d("JSON: ",stringBuilder.toString())*/
            //textView.text = stringBuilder.toString()
        } catch (e: JSONException) {
            Log.e("MainActivity", "Error parsing JSON", e)
        }
    }

}