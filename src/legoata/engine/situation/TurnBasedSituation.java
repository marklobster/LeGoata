package legoata.engine.situation;

import legoata.engine.gamecharacter.GameCharacter;

/**
 * A situation where characters take turns performing actions.
 * @author Mark
 *
 */
public interface TurnBasedSituation extends TargetingSituation {
	public GameCharacter getNextUp();
	public boolean isSituationOver();
}
