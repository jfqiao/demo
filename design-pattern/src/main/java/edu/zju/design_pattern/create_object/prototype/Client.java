package edu.zju.design_pattern.create_object.prototype;

public class Client {
	private Prototype prototype;
	
	public void user() {
		Prototype p = prototype.clone();
		// do some operation.
		p.user();
	}
}
