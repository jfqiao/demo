package edu.zju.src_reading.basic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler{

	public void setProxy(Object proxied) {
		this.proxied = proxied;
	}
	
	private Object proxied;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("Invoke method before. " + method.getName());
		return method.invoke(proxied, args);
	}

	public static void main(String[] args) {
		In1 l1 = new In1() {
			public String getName() {
				return "name";
			}
			public String getAge() {
				return "123";
			}
		};
		In2 l2 = new In2() {
			public String getStr() {
				return "str";
			}
		};
		DynamicProxy dp = new DynamicProxy();
		dp.setProxy(l1);
		In1 ll1 = (In1)Proxy.newProxyInstance(l1.getClass().getClassLoader(), l1.getClass().getInterfaces(), dp);
		ll1.getName();
		dp.setProxy(l2);
		In2 ll2 = (In2)Proxy.newProxyInstance(l2.getClass().getClassLoader(), l2.getClass().getInterfaces(), dp);
		ll2.getStr();
	}
}

interface In1{
	String getName();
	String getAge();
}



interface In2 {
	String getStr();
}