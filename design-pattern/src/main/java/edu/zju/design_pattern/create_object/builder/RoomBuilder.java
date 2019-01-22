package edu.zju.design_pattern.create_object.builder;

public class RoomBuilder implements Builder {
	
	private Room room = new Room();

	@Override
	public void makeWindow() {
		// TODO Auto-generated method stub
		room.setWindow(new Window());
	}

	@Override
	public void makeFloor() {
		// TODO Auto-generated method stub
		room.setFloor(new Floor());
	}
	
	@Override
	public Room getRoom() {
		return this.room;
	}

}
