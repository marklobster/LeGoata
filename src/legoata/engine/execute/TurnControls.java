package legoata.engine.execute;

import java.util.Map;

public class TurnControls {
	
	private Game game;
	
	public TurnControls(Game game) {
		this.game = game;
	}

	public Map<String, Object> getTurnData() {
		return this.game.getTurn().getData();
	}

	public int getActionCount() {
		return this.game.getTurn().getActionCount();
	}

	public void setActionCount(int actionCount) {
		this.game.getTurn().setActionCount(actionCount);
	}

	public int getActionLimit() {
		return this.game.getTurn().getActionLimit();
	}

	public void setActionLimit(int actionLimit) {
		this.game.getTurn().setActionLimit(actionLimit);
	}
	
}
