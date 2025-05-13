package legoata.engine.execute.controls;

import java.util.ArrayList;

import legoata.engine.model.LGObject;

public class GameControls {

	private ArrayList<LGObject> players = null;
	private RoundControls[] rounds = null;
	private boolean exit = false;
	
	public GameControls(ArrayList<LGObject> players, RoundControls[] rounds) {
		this.players = players;
		this.rounds = rounds;
	}
	
	public ArrayList<LGObject> getPlayers() {
		return this.players;
	}
	
	public RoundControls getCurrentRound() {
		return rounds[0];
	}
	
	public RoundControls getNextRound() {
		return rounds[1];
	}
	
	public boolean getExitFlag() {
		return this.exit;
	}
	
	public void setExitFlag(boolean value) {
		this.exit = value;
	}
	
}
