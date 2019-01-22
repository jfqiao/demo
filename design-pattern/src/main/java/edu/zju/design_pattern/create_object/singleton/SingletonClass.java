package edu.zju.design_pattern.create_object.singleton;

public class SingletonClass {
	
	private static SingletonClass instance;
	
	private SingletonClass() {
		
	}
	
	public static SingletonClass getInstance() {
		// 懒汉模式，在多线程下可能会产生问题。
		if (instance == null)
			instance = new SingletonClass();
		return instance;
	}
}
