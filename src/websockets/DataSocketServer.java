package websockets;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import sensorplot.MyLock;

public class DataSocketServer extends Thread {

	JsonObject object;
	MyLock lock;

	public DataSocketServer(MyLock lock) {
		this.lock = lock;
	}

	public void run() {
		try {
			ServerSocket server = new ServerSocket(52637);
			System.out.println("Waiting for client...");
			Socket client = server.accept();
			System.out.println("Client Found");
			JsonStreamParser reader = new JsonStreamParser(new InputStreamReader(client.getInputStream()));
			while (client.isConnected()) {
				while (!reader.hasNext()) {
					
					}
				if (reader.hasNext()) {
					this.object = (JsonObject) reader.next();
					synchronized (lock) {
						lock.notify();
					}
				}
			}
			System.out.println("Client @ " + client.getInetAddress() + "disconnected");
			server.close();
		} catch (IOException e) {
			System.out.println("Failed To Start Server!");
		}
	}

	public JsonObject getObject() {
		return object;
	}
}
