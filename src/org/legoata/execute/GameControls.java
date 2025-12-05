package org.legoata.execute;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.legoata.config.LGConfig;
import org.legoata.model.structure.LGCollection;

public class GameControls {

	private Game game = null;
	
	GameControls(Game game) {
		this.game = game;
	}
	
	public LGCollection getPlayers() {
		return this.game.getPlayers();
	}
	
	public List<UUID> getTurnOrder() {
		return this.game.getTurnOrder();
	}
	
	public void setTurnOrder(List<UUID> turnOrder) {
		this.game.setTurnOrder(turnOrder);
	}
	
	public LGCollection getLooseObjects() {
		return this.game.getLooseObjections();
	}
	
	public Scanner getScanner() {
		return game.getScanner();
	}
	
	public PrintStream getOutStream() {
		return game.getOutStream();
	}
	
	public LGConfig getSettings() {
		return this.game.getSettings();
	}
	
	public boolean getExitFlag() {
		return this.game.getExitFlag();
	}
	
	public void setExitFlag(boolean value) {
		this.game.setExitFlag(value);
	}
	
}
