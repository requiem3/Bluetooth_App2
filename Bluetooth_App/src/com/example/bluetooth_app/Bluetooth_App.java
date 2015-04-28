package com.example.bluetooth_app;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Bluetooth_App extends ActionBarActivity {
	private Bluetooth bT;
	private BluetoothAdapter mBluetoothAdapter;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth__app);
				
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		if (!mBluetoothAdapter.isEnabled()) {
		    startActivityForResult(enableBtIntent, 1);
		}
		
		onActivityResult(1,1,enableBtIntent);
		
		bT = new Bluetooth(this);
		
		bT.isCompat();
		//bT.getAdapter();	
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		bT.destroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bluetooth__app, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
