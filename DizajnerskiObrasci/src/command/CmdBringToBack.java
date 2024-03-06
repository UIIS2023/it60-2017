package command;



import model.DrawingModel;
import shapes.Shape;



public class CmdBringToBack implements Command {
	
	private DrawingModel model;
	private Shape shape;
	private int index;
	
	public CmdBringToBack(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	
	
	@Override
	public void execute() {
		index =  model.getIndexOf(shape);
		model.removeAtIndex(index);
		model.addToIndex(0, shape);
	}

	
	
	@Override
	public void unexecute() {
		model.removeAtIndex(0);
		model.addToIndex(index, shape);
	}

	
	public String txtForLog() {
		return "Bringed to back->" + shape.toString();
	}


}
