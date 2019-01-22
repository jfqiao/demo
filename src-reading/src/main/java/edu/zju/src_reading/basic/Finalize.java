package edu.zju.src_reading.basic;

public class Finalize {
	private byte[] all = new byte[1024 * 1024];
	
	@Override
	public void finalize() {
		System.out.println("Release memory.");
	}
	
	public static void main(String[] args) {
		Finalize fi = new Finalize();
		fi = null;
		System.gc();
	}
}
