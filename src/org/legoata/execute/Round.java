package org.legoata.execute;

import java.util.ArrayList;
import java.util.UUID;

class Round {

	private int index = 0;
	private int nextIncrement = 0;
	private boolean completeFlag = false;
	private ArrayList<UUID> turnOrder = null;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		setIndex(index, true);
	}
	
	public void setIndex(int index, boolean updateNextIncrement) {
		this.index = index;
		if (updateNextIncrement) {
			this.nextIncrement = index + 1;
		}
	}
	
	public int getNextIncrement() {
		return nextIncrement;
	}

	public void setNextIncrement(int nextIncrement) {
		this.nextIncrement = nextIncrement;
	}

	public void incrementIndex() {
		setIndex(this.nextIncrement);
	}

	public boolean isComplete() {
		return this.completeFlag;
	}

	public void complete() {
		this.completeFlag = true;
	}

	public ArrayList<UUID> getTurnOrder() {
		return turnOrder;
	}

	public void setTurnOrder(ArrayList<UUID> turnOrder) {
		this.turnOrder = turnOrder;
	}
}
