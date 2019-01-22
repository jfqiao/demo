package edu.zju.design_pattern.create_object.builder;

public class Director {
	
	public void command(Builder builder) {
		// 先键地板 
		builder.makeFloor();
		// 在键窗户
		builder.makeWindow();
	}
}
