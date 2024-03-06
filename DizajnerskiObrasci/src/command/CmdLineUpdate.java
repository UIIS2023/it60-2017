package command;

import shapes.Line;

public class CmdLineUpdate implements Command{
	
	private Line oldState;
	private Line newState;
	private Line originalState;
	
	public CmdLineUpdate(Line oldState, Line newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = oldState.clone();
		oldState.setInitial(newState.getInitial().clone());
		oldState.setLast(newState.getLast().clone());
		oldState.setColor(newState.getColor());
	}

	
	@Override
	public void unexecute() {
		oldState.setInitial(originalState.getInitial());
		oldState.setLast(originalState.getLast());
		oldState.setColor(originalState.getColor());
	}
	
	
	public String txtForLog() {
		return "Updated->" + oldState.toString() + "->" + newState.toString();
	}

}
