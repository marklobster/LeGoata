package legoata.engine.actions;

import java.io.PrintStream;

import legoata.engine.equipment.Equipment;

public class UseItem extends TargetingAction {
	
	private Equipment item;
	
	public UseItem() {
	}

	@Override
	public void performAction(PrintStream out) {
		// flavor text
		String frag1 = String.format("%s uses %s", this.getActionPerformer().getFullName(), this.getItem().getName());
		String frag2 = this.getActionPerformer() == this.getTarget() ?
				"." : String.format(" on %s.", this.getTarget().getFullName());
		out.println(frag1 + frag2);
		
		// use item
		this.getItem().useItem(this.getActionPerformer(), this.getTarget(), out);
	}

	@Override
	public int getCost() {
		return 7; // for now
	}
	
	public Equipment getItem() {
		return item;
	}
	
	public void setItem(Equipment item) {
		this.item = item;
	}

	@Override
	public TargetType getTargetType() {
		return item.getTargetType();
	}
}
