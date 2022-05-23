package legoata.engine.actions;

import legoata.engine.gamecharacter.GameCharacter;

public abstract class TargetingAction extends CharacterAction {
	private GameCharacter target;

	public GameCharacter getTarget() {
		return target;
	}

	public void setTarget(GameCharacter target) {
		this.target = target;
	}
}
