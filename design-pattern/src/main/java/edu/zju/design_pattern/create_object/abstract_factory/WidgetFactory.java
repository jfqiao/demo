package edu.zju.design_pattern.create_object.abstract_factory;
/*
 * 界面主要有Window与ScrollBar组成（即包括两类产品），
 * 对于不同界面，有不同的window与scroll bar，因此交由子类创建。
 * 当然，也可以将产品
 */
public abstract class WidgetFactory {
	
	public abstract Window createWindow();
	public abstract ScrollBar createScrollBar();
	
	public void otherOp() {
		
	}
	
}
