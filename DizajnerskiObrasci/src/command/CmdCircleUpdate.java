package command;


import shapes.Circle;



public class CmdCircleUpdate implements Command{
	
	private Circle oldState;
	private Circle newState;
	private Circle originalState;
	
	public CmdCircleUpdate(Circle oldState, Circle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	
	@Override
	public void execute() {
		originalState = oldState.clone();
		oldState.setRadius(newState.getRadius());
		oldState.setCenter(newState.getCenter().clone());
		oldState.setInteriorColor(newState.getInteriorColor());
		oldState.setColor(newState.getColor());
	}

	
	@Override
	public void unexecute() {
		oldState.setRadius(originalState.getRadius());
		oldState.setCenter(originalState.getCenter());
		oldState.setInteriorColor(originalState.getInteriorColor());
		oldState.setColor(originalState.getColor());
	}

	
	public String txtForLog() {
		return "Updated->" + oldState.toString() + "->" + newState.toString();
	}

}
