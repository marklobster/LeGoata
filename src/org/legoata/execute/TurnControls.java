package org.legoata.execute;

import java.util.Map;

import org.legoata.model.LGObject;

public class TurnControls {
	
	private Turn turn;
	
	public TurnControls(Turn turn) {
		this.turn = turn;
	}

	public Map<String, Object> getTurnData() {
		return this.turn.getData();
	}

	public int getActionCount() {
		return this.turn.getActionCount();
	}

	public void setActionCount(int actionCount) {
		this.turn.setActionCount(actionCount);
	}

	public int getActionLimit() {
		return this.turn.getActionLimit();
	}

	public void setActionLimit(int actionLimit) {
		this.turn.setActionLimit(actionLimit);
	}
	
	public LGObject getTurnTaker() {
		return this.turn.getTurnTaker();
	}
}
