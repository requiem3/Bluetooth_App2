package Threading;

import android.bluetooth.BluetoothSocket;

import com.example.bluetooth_app.*;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.app.Activity;
import android.widget.TextView;

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
	private Chat mChat;
	private TextView tView;
	
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
			return;
		}
		
		mChat.ncThread = null; //Finished the thread, end it, but its over in the chat class lol, shitty setup
		
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
