package legoata.engine.controller.command;

public class RunGameOp implements TurnCommand {
	
	private String gameOpName;

	public RunGameOp(String gameOpName) {
		this.gameOpName = gameOpName;
	}

	public String getGameOpName() {
		return this.gameOpName;
	}
	
}
