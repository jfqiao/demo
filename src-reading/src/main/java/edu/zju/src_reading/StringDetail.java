package edu.zju.src_reading;

public class StringDetail {
	public static void main(String[] args) {
		stringLiteralCmp();
		strLitCmpStrObj();
		strObjCmp();
		strLitInternCmp();
		strLitNotInInternCmp();
		strObjInternCmp();
		strObjNotInInternCmp();
	}
	
	public static void print(Object o) {
		System.out.println(o);
	}
	
	public static void stringLiteralCmp() {
		String s1 = "123";
		String s2 = "123";
		print(s1 == s2);
	}
	
	public static void strLitCmpStrObj() {
		String s1 = "123"; 				// 指向常量池中的引用
		String s2 = new String("123");   // 指向堆中的引用。
		print(s1 == s2);
	}
	
	public static void strObjCmp() {
		String s1 = new String("123");
		String s2 = new String(s1);
		print(s1 == s2);
	}
	
	public static void strLitInternCmp() {
		String s1 = "java";      // s1指向常量池中字符串
		String s2 = s1.intern();  // s2也是指向常量池中字符串的引用
		print(s1 == s2);  // true
	}
	
	public static void strLitNotInInternCmp() {
		String s1 = "1234123";
		String s2 = s1.intern();  // 同上
		print(s1 == s2);   // true
	}
	
	public static void strObjInternCmp() {
		String s1 = new String("java"); 
		String s2 = s1.intern();	 
		String s3 = "java";
		print(s2 == s3);  // true
	}
	
	public static void strObjNotInInternCmp() {
		String s1 = new String("a") + new String("a"); // jvm常量池中没有的字符串aa, 复制引用到常量池中
		String s2 = s1.intern();          // 
		print(s1 == s2); // true
		print(s1 == "aa"); // true
	}
}
