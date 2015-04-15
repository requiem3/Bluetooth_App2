package com.example.bluetooth_app;

import Threading.*;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Chat {
	private Constants constants;
	private BluetoothAdapter mBluetoothAdapter; 
	private int state; //Current state of the class(none, listening, connected, or connecting)
	private Handler chatHandle; //Handle to the chat
	private newConnectionThread ncThread; //ConnectThread
	private alreadyConnectedThread acThread; //ConnectedThread
	private acceptNewThread anThread; //AcceptThread
	private Activity activity;

	
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
		if(ncThread != null){
			ncThread.end();
			ncThread = null;
		}
		
		if(acThread != null){
			acThread.end();
			acThread = null;
		}
		
		if(anThread != null){
			anThread.cancel();
			anThread = null;
		}
		
		acThread = new alreadyConnectedThread(activity, mBtSocket, chatHandle);
		acThread.start();
		
		Message msg = chatHandle.obtainMessage(constants.MESSAGE_DEVICE_NAE);
		Bundle bundle = new Bundle();
		bundle.putString(constants.DEVICE_NAME, device.getName());
		msg.setData(bundle);
		chatHandle.sendMessage(msg);
		
		setState(constants.SCONNECTED);
	}
	
	
	public void stop(){
		if(ncThread != null){
			ncThread.end();
			ncThread = null;
		}
		
		if(acThread != null){
			acThread.end();
			acThread = null;
		}
		
		if(anThread != null){
			anThread.cancel();
			anThread = null;
		}
		setState(constants.SNONE);
	}
	
	private void connectionFailed(){
		//send a failure message back to the Activity
		Message msg = chatHandle.obtainMessage(constants.MESSAGE_FAIL);
		Bundle bundle = new Bundle();
		bundle.putString(constants.FAIL, "Unable to connect.");
		msg.setData(bundle);
		chatHandle.sendMessage(msg);
		
		//Start the service over to restart listening mode
		Chat.this.start();
	}
	
	private void connectionLost(){
		//send a failure message back to the Activity
		Message msg = chatHandle.obtainMessage(constants.MESSAGE_FAIL);
		Bundle bundle = new Bundle();
		bundle.putString(constants.FAIL, "Connection lost.");
		msg.setData(bundle);
		chatHandle.sendMessage(msg);
		
		//Start the service over to restart listening mode
		Chat.this.start();
	}
}












