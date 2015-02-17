package Threading;

import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.app.Activity;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.UUID;

public class newConnectionThread extends Threads {
	private BluetoothSocket btSocket;
	private BluetoothAdapter btAdapter;
	private BluetoothDevice btDevice;
	private Activity activity;
	private static final UUID INSECURE_UUID = UUID.fromString("5c978ab2-b6eb-11e4-a71e-12e3f512a338"); //From https://www.uuidgenerator.net/
	
	public newConnectionThread(BluetoothDevice device, BluetoothAdapter blueAdapter) {
		this.activity = activity;
		btAdapter = blueAdapter;
		BluetoothSocket temp1 = null;
		btDevice = device;
		
		try {
			temp1 = device.createInsecureRfcommSocketToServiceRecord(INSECURE_UUID);
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
