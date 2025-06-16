package org.legoata.execute;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.legoata.config.LGConfig;
import org.legoata.model.LGObject;

public class GameControls {

	private Game game = null;
	
	GameControls(Game game) {
		this.game = game;
	}
	
	public ArrayList<LGObject> getPlayers() {
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
