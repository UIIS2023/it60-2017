package command;

import shapes.Point;

public class CmdPointUpdate implements Command{
	
	private Point oldState;
	private Point newState;
	private Point originalState;
	
	public CmdPointUpdate(Point oldState, Point newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	

	@Override
	public void execute() {
		originalState = oldState.clone();
		oldState.moveTo(newState.getXcoordinate(), newState.getYcoordinate());
		oldState.setColor(newState.getColor());
	}

	@Override
	public void unexecute() {
		oldState.moveTo(originalState.getXcoordinate(), originalState.getYcoordinate());
		oldState.setColor(originalState.getColor());
	}



	
	public String txtForLog() {
		return "Updated->" + oldState.toString() + "->" + newState.toString();
	}


}
