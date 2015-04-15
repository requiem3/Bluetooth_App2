package com.example.bluetooth_app;

import java.util.UUID;

public final class Constants {
	public int VISIBLE = 0;
	public int INVISIBLE = 4;
	
	public int curState = 0;
	public int SNONE = 0;
	public int SLISTEN = 1;
	public int SCONNECTED = 2;
	public int SCONNECTING = 3;
	
	public int READ_MESSAGE = 2;
	public int WRITE_MESSAGE = 3;
	public int MESSAGE_DEVICE_NAE = 4;
	public int MESSAGE_FAIL = 5;
	
	public UUID INSECURE_UUID = UUID.fromString("5c978ab2-b6eb-11e4-a71e-12e3f512a338"); //From https://www.uuidgenerator.net
    // Name for the SDP record when creating server socket
    public String NAME_INSECURE = "btInsecure";
    public String DEVICE_NAME = "device_name";
    public String FAIL = "failed";
}
