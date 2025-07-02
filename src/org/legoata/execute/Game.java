package org.legoata.execute;

import java.io.PrintStream;
import java.util.Scanner;

import org.legoata.config.LGConfig;
import org.legoata.model.structure.LGCollection;

class Game {

	private Phase phase = Phase.INIT_GAME;
	private LGCollection players = new LGCollection();
	private LGCollection objects = new LGCollection();
	private Clock clock;
	private Round round = null;
	private Turn turn = null;
	private Scanner scanner = null;
	private PrintStream outStream = null;
	private LGConfig settings;
	private boolean exitFlag;
	
	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	LGCollection getPlayers() {
		return this.players;
	}
	
	LGCollection getLooseObjections() {
		return this.objects;
	}

	public Clock getClock() {
		return clock;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public boolean getExitFlag() {
		return exitFlag;
	}

	public void setExitFlag(boolean value) {
		this.exitFlag = value;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public PrintStream getOutStream() {
		return outStream;
	}

	public void setOutStream(PrintStream outStream) {
		this.outStream = outStream;
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

	public LGConfig getSettings() {
		return settings;
	}

	public void setSettings(LGConfig settings) {
		this.settings = settings;
	}

}
