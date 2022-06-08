package legoata.engine.situation;

import java.io.PrintStream;
import java.util.ArrayList;

import legoata.engine.actions.CharacterAction;
import legoata.engine.decision.Decision;
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
	private SituationState State = SituationState.NotLoaded;
	private boolean debug = false;

	/**
	 * Initializes TimedActionSituation with debug = false.
	 */
	public TimedActionSituation() {
		
	}
	
	/**
	 * Initialize TimedActionSituation with debug setting.  When debug 
	 * is true, TurnTracker prints state information whenenver it is 
	 * updated.
	 * @param debug
	 */
	public TimedActionSituation(boolean debug) {
		this.debug = debug;
	}
	
	public final GameCharacter getNextUp() {
		if (debug && State == SituationState.NotLoaded) {
			turnTracker.displayTrackerInfo(System.out);
		}
		if (State == SituationState.CharacterLoaded) {
			throw new IllegalStateException("getNextUp called in CharacterLoaded state.");
		}
		currentCharacter = turnTracker.getNextUp();
		State = SituationState.CharacterLoaded;
		if (debug) {
			turnTracker.displayTrackerInfo(System.out);
		}
		
		return currentCharacter;
	}
	
	public final void submitDecision(Decision decision, PrintStream stream) {
		if (State == SituationState.ActionComplete) {
			throw new IllegalStateException("performAction called in ActionDone state.");
		}
		CharacterAction action = decision.getAction();
		action.performAction(stream);
		turnTracker.payActionCost(currentCharacter, action.getCost());
		State = SituationState.ActionComplete;
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
		NotLoaded,
		CharacterLoaded,
		ActionComplete
	}
}
