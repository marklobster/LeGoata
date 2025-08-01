package org.legoata.samples.gofish.decision;

import java.io.PrintStream;
import java.util.ArrayList;

import org.legoata.decision.Decision;
import org.legoata.decision.DecisionBuilder;
import org.legoata.decision.node.DecisionBuilderNode;
import org.legoata.decision.node.branching.Option;
import org.legoata.decision.node.branching.OptionSet;
import org.legoata.decision.node.nonbranching.DecisionComplete;
import org.legoata.model.LGObject;
import org.legoata.samples.gofish.action.ShowStatus;
import org.legoata.samples.gofish.model.User;

public class CardRequestBuilder extends DecisionBuilder {

	public CardRequestBuilder(User user) {
		this.setOptionSet(new RootOptionSet());
		this.setInitialText(user.getName() + "'s turn!");
	}
	
	private class RootOptionSet implements OptionSet {
		
		private static final String SELECT_OPPONENT = "Select Opponent";
		private static final String INFO = "Check Player Status";

		@Override
		public DecisionBuilderNode select(Decision decision, Option selection, LGObject actor, PrintStream out) {
			String title = selection.getTitle();
			DecisionBuilderNode node = null;
			if (title == INFO) {
				decision.setActionName(ShowStatus.LABEL);
				node = new DecisionComplete();
			} else if (title == SELECT_OPPONENT) {
				
			}
			return node;
		}

		@Override
		public void undoSelection(Decision decision, LGObject actor) {
			decision.setActionName(null);
			decision.setData(null);
		}

		@Override
		public ArrayList<Option> getOptions(Decision decision, LGObject actor) {
			ArrayList<Option> options = new ArrayList<Option>();
			options.add(new Option(SELECT_OPPONENT));
			options.add(new Option(INFO));
			return options;
		}

		@Override
		public String getPrompt() {
			return "Select an action.";
		}

		@Override
		public String getEmptySetText() {
			return null;
		}
		
	}

}
