package legoata.engine.controller.command;

public class ExitGame implements FrameworkCommand {

	private boolean advance;
	
	public ExitGame() {
		this(false);
	}
	
	public ExitGame(boolean advance) {
		this.advance = advance;
	}
	
	public boolean isTurnFinished() {
		return this.advance;
	}
}
