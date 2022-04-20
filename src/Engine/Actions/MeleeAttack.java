package Engine.Actions;

import java.io.PrintStream;

public class MeleeAttack extends TargetingAction {

	@Override
	public void performAction(PrintStream sysOut) {
		// describe attack
		String attackerName = this.getActionPerformer().getFullName();
		String weaponName = this.getActionPerformer().getWeapon().getName();
		String targetName = this.getTarget().getFullName();
		sysOut.println(attackerName + " attacks " + targetName + " with " + weaponName + ".");
		
		// attempt hit
		if (!this.getActionPerformer().attemptMeleeStrike()) {
			sysOut.println("Miss!");
			return;
		}
		
		// attempt dodge
		if (this.getTarget().attemptDodge()) {
			sysOut.println(targetName + " dodges the attack!");
			return;
		}
		
		// attempt block
		if (this.getTarget().attemptDeflection()) {
			sysOut.println("Deflected!");
			return;
		}
		
		// do damage
		int damage = this.getActionPerformer().calculateDamageSent()
				- this.getTarget().calculateDamageReduction();
		sysOut.println(damage + " damage!");
		this.getTarget().loseHealth(damage);
		sysOut.println(targetName + " has " + this.getTarget().getHealth() + " health left.");
		
		// see if target has fallen
		if (this.getTarget().isFallen()) {
			sysOut.println(targetName + " is down for the count!");
		}
	}

	@Override
	public int getCost() {
		return 5;
	}
}
