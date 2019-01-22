package edu.zju.design_pattern.create_object.builder;

public class Room {
	private Floor floor;
	private Window window;
	
	public Floor getFloor() {
		return this.floor;
	}
	
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	
	public Window getWindow() {
		return this.window;
	}
	
	public void setWindow(Window window) {
		this.window = window;
	}
}
