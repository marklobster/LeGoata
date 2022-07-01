package legoata.engine.actions;

import java.io.PrintStream;

import legoata.engine.equipment.Item;

public class UseItem extends TargetingAction {
	
	private Item item;

	@Override
	public void performAction(PrintStream out) {
		// flavor text
		Item item = this.getItem();
		String frag1 = String.format("%s uses %s", this.getActionPerformer().getFullName(), item.getName());
		String frag2 = this.getActionPerformer() == this.getTarget() ?
				"." : String.format(" on %s.", this.getTarget().getFullName());
		out.println(frag1 + frag2);
		
		// use item
		item.useItem(this.getActionPerformer(), this.getTarget(), out);
		
		// dispose item if it is disposable
		if (item.disposeOnUse()) {
			this.getActionPerformer().removeItem(item);
		}
	}

	@Override
	public int getCost() {
		return 7; // for now
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public TargetType getTargetType() {
		return item.getTargetType();
	}
}
