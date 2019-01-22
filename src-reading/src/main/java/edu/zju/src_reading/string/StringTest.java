package edu.zju.src_reading.string;

import edu.zju.src_reading.Util;

public class StringTest {
	
	public static void main(String[] args) {
		String s1 = "123";
		String s4 = "123";
		String s = new String("1234");
		char[] c = {'1', '2', '3'};
		Util.print(c);
		String s2 = new String(c);
		Util.print(s1 == s);
	}

}
