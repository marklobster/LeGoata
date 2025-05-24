package legoata.engine.execute;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import legoata.engine.model.LGObject;

public class GameControls {

	private Game grc = null;
	private RoundControls roundControls = null;
	private TurnControls turnControls = null;
	
	GameControls(Game game) {
		this.grc = game;
		if (grc.getRound() != null) {
			this.roundControls = new RoundControls(game);
		}
		if (grc.getTurn() != null) {
			this.turnControls = new TurnControls(game);
		}
	}
	
	public ArrayList<LGObject> getPlayers() {
		return this.grc.getPlayers();
	}
	
	public Scanner getScanner() {
		return grc.getScanner();
	}
	
	public PrintStream getOutStream() {
		return grc.getOutStream();
	}
	
	public boolean getExitFlag() {
		return this.grc.getExitFlag();
	}
	
	public void setExitFlag(boolean value) {
		this.grc.setExitFlag(value);
	}

	public RoundControls getRoundControls() {
		return roundControls;
	}

	public TurnControls getTurnControls() {
		return turnControls;
	}
	
}
