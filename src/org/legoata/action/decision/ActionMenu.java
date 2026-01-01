package org.legoata.action.decision;

import org.legoata.decision.node.branching.InputSpecificity;
import org.legoata.decision.node.branching.ListDisplayMode;
import org.legoata.decision.node.branching.Menu;
import org.legoata.execute.ControlUnit;

/**
 * An OptionSet for convenient use with the ActionBuilder.
 */
public abstract class ActionMenu extends Menu<ActionDecision> {

	public ActionMenu(ControlUnit controls, ListDisplayMode displayOption, InputSpecificity specificityOption) {
		super(controls, displayOption, specificityOption);
	}
	
	public ActionMenu(ControlUnit controls, ListDisplayMode displayOption, InputSpecificity specificityOption, boolean supplyBackOption) {
		super(controls, displayOption, specificityOption, supplyBackOption, DEFAULT_SEPARATOR);
	}
	
	public ActionMenu(ControlUnit controls, ListDisplayMode displayOption, InputSpecificity specificityOption, String separator) {
		super(controls, displayOption, specificityOption, false, separator);
	}
	
	public ActionMenu(ControlUnit controls, ListDisplayMode displayOption, InputSpecificity specificityOption, boolean supplyBackOption, String separator) {
		super(controls, displayOption, specificityOption, supplyBackOption, separator);
	}

}
