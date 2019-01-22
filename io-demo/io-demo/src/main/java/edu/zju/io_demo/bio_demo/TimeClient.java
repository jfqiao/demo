package edu.zju.io_demo.bio_demo;

import java.net.Socket;
import java.net.InetSocketAddress;

import edu.zju.io_demo.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class TimeClient {

	public static void main(String[] args) {
		int port = 8080;
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress("localhost", port), 2000);
			pw = new PrintWriter(socket.getOutputStream());
			pw.println("Time query order.");
			pw.println(); // 打印空行通知服务器发送完毕。
			pw.flush();   // 刷新缓存发送。
			StringBuilder sb = new StringBuilder();
			String line;
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			Util.print(sb.toString());
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
			if (pw != null) {
				pw.close();
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
