package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ApplicationServer implements Runnable {
	private String DS_IP;
	private int DS_Port;

	public static void main() throws InterruptedException {

	}

	@SuppressWarnings("resource")
	public void run() {
		setDS_Port(DS_Port + 1);
		ServerSocket server = null;
		try {
			server = new ServerSocket(DS_Port, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			Socket s = null;
			try {
				s = server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/***********/
	/* getters */
	/***********/

	public String getDS_IP() {
		return DS_IP;
	}

	public int getDS_Port() {
		return DS_Port;
	}

	/***********/
	/* setters */
	/***********/

	public void setDS_IP(String newDS_IP) {
		DS_IP = newDS_IP;
	}

	public void setDS_Port(int newDS_Port) {
		DS_Port = newDS_Port;
	}
}
