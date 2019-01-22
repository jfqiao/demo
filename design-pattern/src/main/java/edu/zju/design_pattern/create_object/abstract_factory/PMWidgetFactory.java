package edu.zju.design_pattern.create_object.abstract_factory;

public class PMWidgetFactory extends WidgetFactory {

	@Override
	public Window createWindow() {
		// TODO Auto-generated method stub
		return new PMWindow();
	}

	@Override
	public ScrollBar createScrollBar() {
		// TODO Auto-generated method stub
		return new PMScrollBar();
	}

}
