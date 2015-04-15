package Threading;

import android.bluetooth.BluetoothSocket;
import com.example.bluetooth_app.*;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.app.Activity;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.UUID;

public class newConnectionThread extends Thread {
	private BluetoothSocket btSocket;
	private BluetoothAdapter btAdapter;
	private BluetoothDevice btDevice;
	private Activity activity;
	private Constants consts;
	
	public newConnectionThread(BluetoothDevice device, BluetoothAdapter blueAdapter) {
		this.activity = activity;
		btAdapter = blueAdapter;
		BluetoothSocket temp1 = null;
		btDevice = device;
		consts = new Constants();
		
		try {
			temp1 = device.createInsecureRfcommSocketToServiceRecord(consts.INSECURE_UUID);
		}
		catch(IOException e) {
			//TODO: should do something for an error lol
		}
		
		btSocket = temp1;
	}
	
	public void run() {
		btAdapter.cancelDiscovery(); //Stop discovering, shit is very resource intensive
		
		try {
			btSocket.connect();
		}
		catch(IOException e) {
			try {
				btSocket.close();
			}
			catch(IOException f) {
				//TODO: something
			}
		}
		
		//Need to reset the thread
		//Cant do this yet because the function you need does not exist
		
		//Run function to start the connected thread (doesn't exist yet)
		
	}
	
	public void end() {
		try {
			btSocket.close();
		}
		catch(IOException e) {
			//TODO: Something
		}
	}
}
