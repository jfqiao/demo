package edu.zju.io_demo.bio_demo;

import java.net.Socket;

import edu.zju.io_demo.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

public class TimeServerHandler implements Runnable {

	private Socket socket;

	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}

	private BufferedReader br;
	private PrintWriter pw;

	public void initIO() {
		br = null;
		pw = null;
		try {
			br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			pw = new PrintWriter(this.socket.getOutputStream());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.initIO();
		StringBuilder sb = new StringBuilder();
		try {
			if (br != null) {
				String line;
				while (true) {
					line = br.readLine();
					if (line == null || line.length() <= 0)
						break;
					sb.append(line);
				}
			}
			Util.print("client request msg: " + sb.toString());
			if (pw != null) {
				pw.println(new Date().toString());
				pw.flush();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (pw != null)
				pw.close();
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
