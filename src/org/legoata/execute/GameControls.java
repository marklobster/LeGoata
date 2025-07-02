package org.legoata.execute;

import java.io.PrintStream;
import java.util.Scanner;

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
