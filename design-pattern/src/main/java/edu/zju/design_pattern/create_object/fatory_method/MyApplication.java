package edu.zju.design_pattern.create_object.fatory_method;

public class MyApplication extends Application{
	
	@Override
	public Document createDocument() {
		return new MyDocument();
	}
}
