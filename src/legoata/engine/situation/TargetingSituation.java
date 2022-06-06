package legoata.engine.situation;

import java.util.ArrayList;

import legoata.engine.actions.TargetType;
import legoata.engine.gamecharacter.GameCharacter;

public interface TargetingSituation extends Situation {
	public ArrayList<GameCharacter> getPossibleTargets(TargetType targetType);
}
