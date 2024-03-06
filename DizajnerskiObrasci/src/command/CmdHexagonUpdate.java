package command;


import adapter.HexagonAdapter;



public class CmdHexagonUpdate implements Command{

	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter originalState;

	public CmdHexagonUpdate(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = oldState.clone();
		oldState.moveTo(newState.getXcoordinate(), newState.getYcoordinate());
		oldState.setColor(newState.getColor());
		oldState.setInteriorColor(newState.getInteriorColor());
		oldState.setR(newState.getR());	
	}

	@Override
	public void unexecute() {
		oldState.moveTo(originalState.getXcoordinate(), originalState.getYcoordinate());
		oldState.setColor(originalState.getColor());
		oldState.setInteriorColor(originalState.getInteriorColor());
		oldState.setR(originalState.getR());
	}
	

	public String txtForLog() {
		return "Updated->" + oldState.toString() + "->" + newState.toString();
	}


}
