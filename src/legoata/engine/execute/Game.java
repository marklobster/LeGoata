package legoata.engine.execute;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import legoata.engine.model.LGObject;

class Game {

	private ArrayList<LGObject> players = new ArrayList<LGObject>();
	private Round round = null;
	private RoundControls roundControls = null;
	private Turn turn = null;
	private TurnControls turnControls = null;
	private Scanner scanner = null;
	private PrintStream outStream = null;
	private boolean exitFlag;	
	
	ArrayList<LGObject> getPlayers() {
		return this.players;
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

	public RoundControls getRoundControls() {
		return roundControls;
	}

	public void setRoundControls(RoundControls roundControls) {
		this.roundControls = roundControls;
	}

	public TurnControls getTurnControls() {
		return turnControls;
	}

	public void setTurnControls(TurnControls turnControls) {
		this.turnControls = turnControls;
	}

}
