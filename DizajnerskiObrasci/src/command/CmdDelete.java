package command;

import java.util.ArrayList;
import model.DrawingModel;
import shapes.Shape;

public class CmdDelete implements Command{
	
	private ArrayList<Shape> shapes;
	private DrawingModel model;
	
	public CmdDelete(ArrayList<Shape> shapes, DrawingModel model) {
		this.shapes = shapes;
		this.model = model;
	}
	
	public CmdDelete(Shape shape, DrawingModel model) {
		this.shapes = new ArrayList<Shape>();
		this.shapes.add(shape);
		this.model = model;	
	}


	@Override
	public void execute() { 
		if (shapes != null) model.removeMultiple(shapes);
	}

	
	@Override
	public void unexecute() {
		if (shapes != null) model.addMultiple(shapes);
	}
	
	public String txtForLog() {
		return "Deleted->" + shapes;
	}	

}
