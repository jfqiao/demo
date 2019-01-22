package edu.zju.design_pattern.create_object.fatory_method;

import java.util.List;
import java.util.ArrayList;
/*
 * 工厂方法模式
 * 假设要创建一个应用，应用可以打开不同的文档。客户必须通过子类来做与具体应用相关的实现。
 */
public abstract class Application {
	
	private List<Document> docs = new ArrayList<>();
	
	// 应用创建文档，但是不同的应用会创建不同的文档。
	public abstract Document createDocument();
	
	// 所有的应用都需要将文档加入列表，并且打开该文档。通用操作。
	public void newDocument() {
		Document doc = this.createDocument();
		docs.add(doc);
		doc.open();
	}
	
	public void openDocument() {
		
	}
}
