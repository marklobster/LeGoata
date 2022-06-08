package legoata.engine.situation;

import java.io.PrintStream;

import legoata.engine.decision.Decision;
import legoata.engine.decision.DecisionBuilder;

/**
 * Provides infrastructure for characters to perform actions.
 * @author Mark
 *
 */
public interface Situation {
	
	public abstract DecisionBuilder getDecisionBuilder();

	public abstract void submitDecision(Decision decision, PrintStream stream);
}
