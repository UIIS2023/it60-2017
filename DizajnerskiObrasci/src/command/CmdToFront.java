package command;


import model.DrawingModel;
import shapes.Shape;

public class CmdToFront implements Command{
	
	private DrawingModel model;
	private Shape shape;
	private int index;

	public CmdToFront(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	
	@Override
	public void execute() {
		index =  model.getIndexOf(shape);
		model.removeAtIndex(index);
		model.addToIndex(index + 1, shape);
	}

	
	@Override
	public void unexecute() {
		model.removeAtIndex(index + 1);
		model.addToIndex(index, shape);
	}
	
	public String txtForLog() {
		return "Moved to front->" + shape.toString();
	}

}
