package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import metadata.Context;

/**
 * The client for the chat.
 * @author Balthazar Pavot
 *
 */
public class ChatClient {

    /**
     * Send the given message to the given ip:port.
     * @param ip
     * @param port
     * @param message
     * @return
     */
	public boolean sendMessage(String ip, int port, String message) {
		Socket socket = new Socket();
		InetSocketAddress adress = new InetSocketAddress(ip, port);
		OutputStream pipe;

		try {
			socket.connect(adress);
			socket.setReuseAddress(true);
			pipe = socket.getOutputStream();
			pipe.write(message.getBytes());
			pipe.flush();
			pipe.close();
		} catch (IOException e) {
			Context.singleton.setSilencedError(e);
			return false;
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				Context.singleton.setSilencedError(e);
			}
		}
		return true ;
	}


}
