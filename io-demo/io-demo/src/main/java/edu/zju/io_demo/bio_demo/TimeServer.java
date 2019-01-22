package edu.zju.io_demo.bio_demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.zju.io_demo.Util;

public class TimeServer {

	public static void main(String[] args) {
		int port = 8080;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			Util.print("The time server listens on port: " + port);
			Socket socket = null;
			TimeServerHandlerExecutorPool executor = new TimeServerHandlerExecutorPool(50, 1000);
			while (true) {
				socket = serverSocket.accept();
//				new Thread(new TimeServerHandler(socket)).start();
				executor.execute(new TimeServerHandler(socket));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
