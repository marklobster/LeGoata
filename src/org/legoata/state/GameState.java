package org.legoata.state;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.legoata.execute.Clock;
import org.legoata.execute.Phase;
import org.legoata.execute.Round;
import org.legoata.execute.Turn;

public class GameState {

	private Phase phase = Phase.INIT_GAME;
	private List<UUID> turnOrder = new ArrayList<UUID>();
	private Clock clock;
	private Round round = null;
	private Turn turn = null;
	
	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}
	
	public List<UUID> getTurnOrder() {
		return turnOrder;
	}

	public void setTurnOrder(List<UUID> turnOrder) {
		this.turnOrder = turnOrder;
	}

	public Clock getClock() {
		return clock;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
	
	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public Turn getTurn() {
		return turn;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	
}
