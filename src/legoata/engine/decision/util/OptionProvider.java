package legoata.engine.decision.util;

import java.util.ArrayList;

import legoata.engine.decision.Option;
import legoata.engine.gamecharacter.GameCharacter;

public interface OptionProvider {

	ArrayList<Option> getOptions(GameCharacter character);
	
}
