package edu.zju.src_reading;

class Father {
	public int age;

	public Father() {
		this.age = 40;
	}

	public void eat() {
		System.out.println("Father eat.");
	}
}

class Son extends Father {
	public int age;

	public Son() {
		this.age = 18;
	}

	public void eat() {
		System.out.println("Son eat.");
	}

	public void play() {
		System.out.println("Son play.");
	}
}

public class Inheritance {
	public static void main(String[] args) {
		Father f = new Son();
		f.eat();
		System.out.println(f.age);
	}
}
