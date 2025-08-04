package org.legoata.decision.util;

import org.legoata.decision.Decision;
import org.legoata.model.LGObject;

public interface UndoOptionSelection {
	
	void undoSelection(Decision decision, LGObject character);
	
}
