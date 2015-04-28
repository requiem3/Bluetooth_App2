package Threading;

import android.bluetooth.BluetoothSocket;

import com.example.bluetooth_app.*;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import java.io.IOException;

public class newConnectionThread extends Thread {
	private BluetoothSocket btSocket;
	private BluetoothAdapter btAdapter;
	private BluetoothDevice btDevice;
	private Constants consts;
	private Chat mChat;
	
	public newConnectionThread(BluetoothDevice device, BluetoothAdapter blueAdapter, Chat iChat) {
		btAdapter = blueAdapter;
		BluetoothSocket temp1 = null;
		btDevice = device;
		consts = new Constants();
		mChat = iChat;
		
		try {
			temp1 = device.createInsecureRfcommSocketToServiceRecord(consts.INSECURE_UUID);
		}
		catch(IOException e) {
			//TODO: do something
		}
		
		btSocket = temp1;
	}
	
	public void run() {
		btAdapter.cancelDiscovery(); //Stop discovering
		
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
			return;
		}
		
		mChat.ncThread = null; //Finished the thread
		
		mChat.connected(btSocket, btDevice);
		
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
