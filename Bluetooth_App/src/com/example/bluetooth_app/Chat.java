package com.example.bluetooth_app;

import Threading.*;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.app.Activity;
import android.widget.TextView;
import android.content.Context;
import android.os.Handler;

public class Chat {
	private Constants constants;
	private BluetoothAdapter mBluetoothAdapter; 
	private int state; //Current state of the class(none, listening, connected, or connecting)
	private Handler chatHandle; //Handle to the chat
	public newConnectionThread ncThread; //ConnectThread
	private alreadyConnectedThread acThread; //ConnectedThread
	private acceptNewThread anThread; //AcceptThread
	private Activity activity;
	
	private TextView tView;

	public Chat() {
		constants = new Constants();
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		state = constants.SNONE; //Currently not doing anything
	}
	
	public int getState() { //Return the current state of the chat object
		return state;
	}
	
	public void setState(int num) {
		state = num;
	}
	
	public void start() { //Start chat
		if(ncThread != null) {
			ncThread.end();
			ncThread = null;
		}
		
		if(acThread != null) {
			acThread.end();
			acThread = null;
		}
		
		setState(constants.SLISTEN);
		
		if(anThread == null) { //Start listening for incoming threads
			anThread = new acceptNewThread(mBluetoothAdapter, this);
			anThread.start();
		}
	}
	
	public void stop() { //stop everything /panic
		if(ncThread != null) {
			ncThread.end();
			ncThread = null;
		}
		
		if(acThread != null) {
			acThread.end();
			acThread = null;
		}
		
		if(anThread != null) {
			anThread.cancel();
			anThread = null;
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
		
		ncThread = new newConnectionThread(device, mBluetoothAdapter, this);
		ncThread.start();
		setState(constants.SCONNECTING);
	}
	
	public void connected(BluetoothSocket mBtSocket, BluetoothDevice device) {
		if(ncThread != null) {
			ncThread.end();
			ncThread = null;
		}
		
		if(acThread != null) {
			acThread.end();
			acThread = null;
		}
		
		if(anThread != null) {
			anThread.cancel();
			anThread = null;
		}

		acThread = new alreadyConnectedThread(mBtSocket);
		acThread.start();
	}
}
