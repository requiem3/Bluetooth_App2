package Threading;

import com.example.bluetooth_app.*;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothServerSocket;

import java.io.IOException;

public class acceptNewThread extends Thread {
	private BluetoothServerSocket btServSocket;
	private BluetoothSocket btSocket;
	private BluetoothAdapter btAdapter;
	private Activity activity;
	private Constants consts;
	private Chat mChat;
	
	public acceptNewThread(BluetoothAdapter blueAdapter, Chat iChat) {
		consts = new Constants();
		mChat = iChat;
		btAdapter = blueAdapter;
		BluetoothServerSocket tmp = null;
		
		try{
			tmp = btAdapter.listenUsingInsecureRfcommWithServiceRecord(consts.NAME_INSECURE, consts.INSECURE_UUID);
		}
		catch(IOException e) {
			//TODO: some sort of error catching shit
		}
		btServSocket = tmp;
	}
	
	public void run() {

		btSocket = null;
		
		while(consts.curState != consts.SCONNECTED) {
			try {
				btSocket = btServSocket.accept();
			}
			catch(IOException e) {
				//TODO: do something
				break;
			}
			
			if(btSocket != null) {
				switch(consts.curState) {
					case 3://Connecting		
						mChat.connected(btSocket, btSocket.getRemoteDevice());
						break;
					case 2://Connected
						try{
							btSocket.close();
						}
						catch(IOException e) {
							//TODO: something
						}
					break;
				}
			}
		}
		
	}
	
	public void cancel() {
		try {
			btSocket.close();
		}
		catch(IOException e) {
			//TODO: something
		}
	}
}
