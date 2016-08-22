package org.usfirst.frc.team115.util;
import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class SocketTestClient extends WebSocketClient{

	NewMessageListener newMessageListener;
	
	public void setNewMessageListener(NewMessageListener listener) {
		newMessageListener = listener;
	}
	
	public SocketTestClient(String uri) throws URISyntaxException {
		super(new URI(uri));
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		System.out.println("Connection Opened!");
	}

	@Override
	public void onMessage(String message) {
		System.out.println("msg: " + message);
		if(newMessageListener != null) newMessageListener.onMessage(message);
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("Connection closed, reason: " + reason);
	}

	@Override
	public void onError(Exception ex) {
		ex.printStackTrace();
	}
	
	public interface NewMessageListener{
		public void onMessage(String msg);
	}
	
}
