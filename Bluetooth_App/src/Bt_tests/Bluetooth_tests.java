package Bt_tests;

import junit.framework.TestCase;
import Threading.alreadyConnectedThread;
import android.app.Activity;
import android.bluetooth.BluetoothSocket;

public class Bluetooth_tests extends TestCase {
	private alreadyConnectedThread acThread;
	private BluetoothSocket btSocket;
	private Activity activity;
	
	public Bluetooth_tests(Activity activity) { //Constructor for the tests class  
		this.activity = activity;
	}
	
	//This test no longer works after new implementation
	public void startConnectedThread() { //starts the connected thread
	//	acThread = new alreadyConnectedThread(activity); 
		acThread.run();
	}
	
	//This test no longer works after new implementation
	public void runConnectedThread() {
	//	acThread = new alreadyConnectedThread(activity);
		byte[] buffer = "hi".getBytes();
		acThread.writeText(buffer);
	}
}
