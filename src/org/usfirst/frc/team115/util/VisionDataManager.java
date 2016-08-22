package org.usfirst.frc.team115.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.spectrum3847.RIOdroid.RIOadb;
import org.spectrum3847.RIOdroid.RIOdroid;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionDataManager {

	public static final String SOCKET_SERVER = "localhost:5801";
	
	public WebSocketClient socketClient;
	
	public volatile VisionPose latestPose;
	
	public VisionDataManager(){
		
	}
	
	public void init() {
		initRioDroid();
		initSocketClient();
	}
	
	public void close() {
		if(socketClient != null) socketClient.close();
		RIOdroid.executeCommand("adb shell kill com.mvrt.BullsEye");
	}
	
	public void initRioDroid() {
		RIOdroid.initUSB();
		RIOadb.init();
		Timer.delay(1);
		
		System.out.println(RIOadb.clearNetworkPorts());
        Timer.delay(1);
        System.out.println("FOWARD ADB: " + RIOadb.ForwardAdb(5801,5801));
        Timer.delay(1);
        System.out.println("START APP: " + RIOdroid.executeCommand("adb shell am start com.mvrt.BullsEye"));
        Timer.delay(1);
	}
	
	public void initSocketClient() {
		SmartDashboard.putBoolean("Socket Connected", false);
		
		URI u;
		try {
			u = new URI(SOCKET_SERVER);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return;
		}
		
		socketClient = new WebSocketClient(u) {

			@Override
			public void onOpen(ServerHandshake handshakedata) {
				System.out.println("Connection open!");
				SmartDashboard.putBoolean("Socket Connected", true);
			}

			@Override
			public void onMessage(String message) {
				//todo: extract actual pose data from message
				latestPose = new VisionPose(1000, 30, 30);
			}

			@Override
			public void onClose(int code, String reason, boolean remote) {
				System.out.println("Connection closed: " + reason);
				SmartDashboard.putBoolean("Socket Connected", false);
			}

			@Override
			public void onError(Exception ex) {
				ex.printStackTrace();
			}
			
		};
		
		socketClient.connect();
	}
	
}
