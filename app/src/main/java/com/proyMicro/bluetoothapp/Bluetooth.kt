import java.util.UUID
import android.bluetooth.BluetoothSocket
import android.bluetooth.BluetoothDevice
import android.util.Log
import com.proyMicro.bluetoothapp.MainActivity

class Bluetooth(private val activity: MainActivity) {

    private var socket: BluetoothSocket? = null
    private val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // UUID para Bluetooth SPP

    fun connect(device: BluetoothDevice) {
        val thread = Thread {
            try {
                socket = device.createRfcommSocketToServiceRecord(uuid)
                socket?.connect()

                val inputStream = socket?.inputStream
                val buffer = ByteArray(1024)

                while (true) {
                    val bytes = inputStream?.read(buffer) ?: 0
                    if (bytes > 0) {
                        val jsonData = String(buffer, 0, bytes)
                        activity.runOnUiThread {
                            activity.displayJsonData(jsonData)
                        }
                    }
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
