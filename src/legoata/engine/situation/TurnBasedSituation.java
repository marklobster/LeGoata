package legoata.engine.situation;

import legoata.engine.gamecharacter.GameCharacter;

public interface TurnBasedSituation extends TargetingSituation {
	public GameCharacter getNextUp();
	public boolean isSituationOver();
}
