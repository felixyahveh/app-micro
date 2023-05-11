import java.util.UUID
import android.bluetooth.BluetoothSocket
import android.bluetooth.BluetoothDevice
import android.util.Log
import com.proyMicro.bluetoothApp.MainActivity

class Bluetooth(private val activity: MainActivity) {

    private var socket: BluetoothSocket? = null
    private val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // UUID para Bluetooth SPP

    fun connect(device: BluetoothDevice) {
        val thread = Thread {
            try {
                socket = device.createRfcommSocketToServiceRecord(uuid)
                socket?.connect()

                val inputStream = socket?.inputStream
                val buffer = ByteArray(8192)

                while (true) {
                    val bytes = inputStream?.read(buffer) ?: 0
                    if (bytes > 0) {
                        val jsonData = String(buffer, 0, bytes)
                        activity.runOnUiThread {
                            activity.displayJsonData(jsonData)
                        }
                    /*
                        activity.runOnUiThread {
                            try {
                                val jsonArray = JSONArray(jsonData)
                                val stringBuilder = StringBuilder()

                                for (i in 0 until jsonArray.length()) {
                                    val jsonObject = jsonArray.getJSONObject(i)
                                    stringBuilder.append("Tipo de moneda: ${jsonObject.getString("moneda")} - ")
                                    stringBuilder.append("Cantidad: ${jsonObject.getInt("cantidad")}\n")
                                }

                                activity.displayJsonData(stringBuilder.toString())
                            } catch (e: JSONException) {
                                Log.e(TAG, "Error al procesar el mensaje recibido: $e")
                            }
                        }*/
                    }
                    Thread.sleep(3000L)
                }

            } catch (e: Exception) {
                Log.e("Bluetooth", e.message, e)
            }
        }

        thread.start()
    }

    fun disconnect() {
        socket?.close()
    }

}
