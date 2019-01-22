package edu.zju.design_pattern.create_object.builder;

public class Client {
	
	public Room createRoom() {
		// 请来工人
		Builder builder = new RoomBuilder();
		// 请来设计师命令工人
		Director director = new Director();
		// 下命令建造房子
		director.command(builder);
		// 找工人验收房子
		Room room = builder.getRoom();
		return room;
	}
	
}
