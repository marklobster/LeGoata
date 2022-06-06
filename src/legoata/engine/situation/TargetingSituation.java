package legoata.engine.situation;

import java.util.ArrayList;

import legoata.engine.actions.TargetType;
import legoata.engine.gamecharacter.GameCharacter;

/**
 * A Situation where characters can target each other with their actions.
 * @author Mark
 *
 */
public interface TargetingSituation extends Situation {
	public ArrayList<GameCharacter> getPossibleTargets(TargetType targetType);
}
