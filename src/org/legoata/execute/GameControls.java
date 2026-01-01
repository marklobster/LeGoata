package org.legoata.execute;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.legoata.config.LGConfig;
import org.legoata.model.structure.HashMapCollection;

public class GameControls {

	private Game game = null;
	private StreamUnit streamUnit = null;
	
	GameControls(Game game, StreamUnit streamUnit) {
		this.game = game;
		this.streamUnit = streamUnit;
	}
	
	public HashMapCollection getPlayers() {
		return this.game.getPlayers();
	}
	
	public List<UUID> getTurnOrder() {
		return this.game.getTurnOrder();
	}
	
	public void setTurnOrder(List<UUID> turnOrder) {
		this.game.setTurnOrder(turnOrder);
	}
	
	public HashMapCollection getLooseObjects() {
		return this.game.getLooseObjections();
	}
	
	public Scanner getScanner() {
		return this.streamUnit.getIn();
	}
	
	public PrintStream getOutStream() {
		return this.streamUnit.getOut();
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
