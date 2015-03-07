package com.example.bluetooth_app;

import Threading.*;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import android.content.Context;
import android.os.Handler;

public class Chat {
	private Constants constants;
	private BluetoothAdapter mBluetoothAdapter; 
	private int state; //Current state of the class(none, listening, connected, or connecting)
	private final Handler chatHandle; //Handle to the chat
	private newConnectionThread ncThread; //ConnectThread
	private alreadyConnectedThread acThread; //ConnectedThread
	private acceptNewThread anThread; //AcceptThread

	
	/**
	 * Initialize Chat object with the state of the chat set to none, and the handle equal to the handle we passed it, also get the default
	 * bluetooth adapter that we need for everything
	 * @param context
	 * @param handler
	 */
	public Chat(Context context, Handler handler) {
		constants = new Constants();
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		state = constants.SNONE; //Current state is nothing, just initialized, derp
		chatHandle = handler; 
	}
	
	public int getState() { //Return the current state of the chat object
		return state;
	}
	
	public void setState(int num) {
		state = num;
	}
	
	public void start() {
		if(ncThread != null) {
			ncThread.end();
			ncThread = null;
		}
		
		if(acThread != null) {
			acThread.end();
			acThread = null;
		}
		
		setState(constants.SLISTEN);
		
		if(anThread == null) {
			anThread = new acceptNewThread(mBluetoothAdapter);
		}
	}
	
	public void connect(BluetoothDevice device) {
		if(state == constants.SCONNECTING) { //Cancel currently connecting threads so we can create a new one
			if(ncThread != null) {
				ncThread.end();
				ncThread = null;
			}
		}
		
		if(acThread != null) { //Cancel already connected device so we can connect again
			acThread.end();
			acThread = null;
		}
		
		ncThread = new newConnectionThread(device, mBluetoothAdapter);
		ncThread.start();
		setState(constants.SCONNECTING);
	}
	
	public void connected(BluetoothSocket mBtSocket, BluetoothDevice device) {
		
	}
}
