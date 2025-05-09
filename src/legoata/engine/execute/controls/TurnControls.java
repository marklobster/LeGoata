package legoata.engine.execute.controls;

import java.util.HashMap;
import java.util.Map;

public class TurnControls {
	
	private Map<String, Object> state = new HashMap<String, Object>();
	private String immediateGameOp = null;
	private boolean exitRequested = false;

	public Map<String, Object> getTurnState() {
		return this.state;
	}
	
	public String getImmediateGameOp() {
		return this.immediateGameOp;
	}

	public void setImmediateGameOp(String name) {
		this.immediateGameOp = name;
	}
	
	public boolean wasExitRequested() {
		return this.exitRequested;
	}
	
	public void exitGame() {
		this.exitRequested = true;
	}
}
