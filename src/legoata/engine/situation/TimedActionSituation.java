package legoata.engine.situation;

import java.io.PrintStream;
import java.util.ArrayList;

import legoata.engine.actions.CharacterAction;
import legoata.engine.gamecharacter.GameCharacter;

/**
 * Default implementation for TurnBasedSituation which utilizes action cost.  
 * Use addGameCharacter method to add characters to the situation. 
 * @author Mark
 *
 */
public abstract class TimedActionSituation implements TurnBasedSituation {
	
	private TurnTracker turnTracker = new TurnTracker();
	private GameCharacter currentCharacter = null;
	private SituationState State = SituationState.Initialized;

	public TimedActionSituation() {
		
	}
	
	public final GameCharacter getNextUp() {
		if (State == SituationState.CharacterLoaded) {
			throw new IllegalStateException("getNextUp called in CharacterLoaded state.");
		}
		currentCharacter = turnTracker.getNextUp();
		State = SituationState.CharacterLoaded;
		return currentCharacter;
	}
	
	public final void performAction(CharacterAction action, PrintStream stream) {
		if (State == SituationState.ActionDone) {
			throw new IllegalStateException("performAction called in ActionDone state.");
		}
		action.performAction(stream);
		turnTracker.payActionCost(currentCharacter, action.getCost());
		State = SituationState.ActionDone;
		afterActionComplete();
	}
	
	protected void afterActionComplete() {
		
	}
	
	protected GameCharacter getCurrentCharacter() {
		return currentCharacter;
	}
	
	protected void addGameCharacter(GameCharacter gChar) {
		turnTracker.addGameCharacter(gChar);
	}
	
	protected boolean removeGameCharacter(GameCharacter gChar) {
		return turnTracker.removeGameCharacter(gChar);
	}
	
	public enum SituationState {
		Initialized,
		CharacterLoaded,
		ActionDone
	}
}
