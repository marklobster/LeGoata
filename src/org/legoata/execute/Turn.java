package org.legoata.execute;

import java.util.HashMap;
import java.util.Map;

import org.legoata.model.LGObject;

class Turn {

	private LGObject turnTaker;
	private Map<String, Object> data = new HashMap<String, Object>();
	private int actionCount;
	private int actionLimit;
	
	Turn(LGObject turnTaker) {
		this.turnTaker = turnTaker;
	}

	public LGObject getTurnTaker() {
		return turnTaker;
	}

	public int getActionCount() {
		return actionCount;
	}

	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}

	public int getActionLimit() {
		return actionLimit;
	}

	public void setActionLimit(int actionLimit) {
		this.actionLimit = actionLimit;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
