package command;

import shapes.Rectangle;

public class CmdRectangleUpdate implements Command{
	
	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle originalState;
	
	public CmdRectangleUpdate(Rectangle oldState, Rectangle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	
	@Override
	public void execute() {
		originalState = oldState.clone();
		oldState.setUpLeft(newState.getUpLeft().clone());
		oldState.setWidth(newState.getWidth());
		oldState.setHeight(newState.getHeight());
		oldState.setColor(newState.getColor());
		oldState.setInteriorColor(newState.getInteriorColor());
	}

	
	@Override
	public void unexecute() {
		oldState.setUpLeft(originalState.getUpLeft());
		oldState.setWidth(originalState.getWidth());
		oldState.setHeight(originalState.getHeight());
		oldState.setColor(originalState.getColor());
		oldState.setInteriorColor(originalState.getInteriorColor());
	}

	public String txtForLog() {
		return "Updated->" + oldState.toString() + "->" + newState.toString();
	}

}
