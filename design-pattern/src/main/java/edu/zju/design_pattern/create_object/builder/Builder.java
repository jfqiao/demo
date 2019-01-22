package edu.zju.design_pattern.create_object.builder;

public interface Builder {
	void makeWindow();
	void makeFloor();
	Room getRoom();
	
}
