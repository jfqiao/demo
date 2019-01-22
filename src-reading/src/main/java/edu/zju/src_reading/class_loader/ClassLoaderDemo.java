package edu.zju.src_reading.class_loader;

import edu.zju.src_reading.Util;

public class ClassLoaderDemo {
	
	public static void main(String[] args) {
		Util.print(ClassLoaderDemo.class.getClassLoader());
	}

}
