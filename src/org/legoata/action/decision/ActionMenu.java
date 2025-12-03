package org.legoata.action.decision;

import org.legoata.decision.node.branching.InputSpecificity;
import org.legoata.decision.node.branching.ListDisplayMode;
import org.legoata.decision.node.branching.OptionSet;
import org.legoata.execute.ControlSet;

/**
 * An OptionSet for convenient use with the ActionBuilder.
 */
public abstract class ActionMenu extends OptionSet<ActionDecision> {

	public ActionMenu(ControlSet controls, ListDisplayMode displayOption, InputSpecificity specificityOption) {
		super(controls, displayOption, specificityOption);
	}
	
	public ActionMenu(ControlSet controls, ListDisplayMode displayOption, InputSpecificity specificityOption, boolean supplyBackOption) {
		super(controls, displayOption, specificityOption, supplyBackOption, DEFAULT_SEPARATOR);
	}
	
	public ActionMenu(ControlSet controls, ListDisplayMode displayOption, InputSpecificity specificityOption, String separator) {
		super(controls, displayOption, specificityOption, false, separator);
	}
	
	public ActionMenu(ControlSet controls, ListDisplayMode displayOption, InputSpecificity specificityOption, boolean supplyBackOption, String separator) {
		super(controls, displayOption, specificityOption, supplyBackOption, separator);
	}

}
