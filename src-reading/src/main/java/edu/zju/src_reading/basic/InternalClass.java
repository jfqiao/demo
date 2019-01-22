package edu.zju.src_reading.basic;

public class InternalClass {
	
	private class AClass {
		private int age;
		public void setAge(int age) {
			this.age = age;
		}
		public int getAge() {
			return this.age;
		}
	}
	
	public AClass getAClass() {
		return this.new AClass();
	}
	
	public User getUser(String name) {
		final String s = "123";
		return new User() {
			private String string = s;
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return string;
			}
		};
	}
	
	public static void main(String[] args) {
		InternalClass ic = new InternalClass();
		User user = new User() {
			@Override
			public String getName() {
				// TODO Auto-generated method stub
		        return ic.getAClass().getClass().getName();	
			}
		};
		System.out.println(user.getName());
	}

}

interface User {
	String getName();
}