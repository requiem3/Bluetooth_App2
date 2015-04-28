package Threading;


import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import com.example.bluetooth_app.R;
import android.bluetooth.BluetoothSocket;
import com.example.bluetooth_app.Constants;

public class alreadyConnectedThread extends Thread {
    private TextView tView;
	private Activity activity;
	private BluetoothSocket btSocket;
	private InputStream iStream;
	private OutputStream oStream;
	private Handler mHandle;
	private Constants constants;

	public alreadyConnectedThread(BluetoothSocket socket) {
		tView = (TextView) activity.findViewById(R.id.textView1);
		constants = new Constants();
		InputStream temp1 = null;
		OutputStream temp2 = null;
		btSocket = socket;
		
		try {
			temp1 = socket.getInputStream();
			temp2 = socket.getOutputStream();
		}
		catch (IOException e) {
			//TODO: do something
		}
		
		iStream = temp1;
		oStream = temp2;
	}
	
	//public alreadyConnectedThread(Activity activity) {
		//this.activity = activity;
		//tView = (TextView) activity.findViewById(R.id.textView1);
	//}
	
	public void run() {
		byte[] buffer = new byte[1024];
		int bytes;
		
		while(true) { //While connected to the InputStream
			try {
				bytes = iStream.read(buffer);
				
				mHandle.obtainMessage(constants.READ_MESSAGE, bytes, -1, buffer).sendToTarget();
			}
			catch (IOException e) {
				//TODO: need to restart thread listening mode
				
				break;
			}
		}
	}
	
	public void writeText(byte[] buffer) {
		try {
			oStream.write(buffer);
			
			mHandle.obtainMessage(constants.WRITE_MESSAGE, -1, -1, buffer).sendToTarget();
		}
		catch (IOException e) {
			//TODO: do something
		}
	}
	
	public void end() {
		try{
			btSocket.close();
		}
		catch(IOException e) {
			//TODO: do something
		}
	}

}
