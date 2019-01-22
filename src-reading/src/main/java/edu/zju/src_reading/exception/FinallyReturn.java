package edu.zju.src_reading.exception;

import edu.zju.src_reading.Util;

public class FinallyReturn {
	
	public static void main(String[] args) {
		// 打印结果 2
		Util.print(test());
	}
	
	public  static int test() {
		try { 
			int i =  1 / 0;
		} catch (ArithmeticException e) {
			return 1;
		} finally {
			return 2;
		}
		// 此处不能返回，finally执行后就一定返回了。
//		return 3;
	}

}
