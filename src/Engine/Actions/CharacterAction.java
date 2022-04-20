package Engine.Actions;

import java.io.PrintStream;

import Engine.GameCharacter.GameCharacter;

public abstract class CharacterAction {
	private GameCharacter actionPerformer;
	
	public abstract void performAction(PrintStream sysOut);
	
	public abstract int getCost();

	public GameCharacter getActionPerformer() {
		return actionPerformer;
	}

	public void setActionPerformer(GameCharacter actionPerformer) {
		this.actionPerformer = actionPerformer;
	}
}
