package org.legoata.equipment;

import java.io.PrintStream;

import org.legoata.gamecharacter.GameCharacter;
import org.legoata.utils.LGUtils;

public class SmackySlap extends Item {
	
	private static boolean slap = false;

	public SmackySlap() {
		super(1, "Smacky Slap", "Smacks and slaps whoever you throw it at.", TargetType.Foe, true);
	}

	@Override
	public void useItem(GameCharacter user, GameCharacter target, PrintStream out) {

		// attempt hit
		if (user.attemptRangedStrike()){
			out.println(slap ? "Smacky Slappy Slap!" : "Smacky Slappy Smack!");
			slap = !slap;
		} else {
			out.println("Miss!");
			return;
		}
		
		// attempt dodge
		if (target.attemptDodge()) {
			out.println(target.getFullName() + " dodges the attack!");
			return;
		}
		
		// attempt block
		if (target.attemptDeflection()) {
			out.println("Deflected!");
			return;
		}
		
		// do damage
		int damage = 25 + user.getBaseDexterity() + LGUtils.getRandom(-3, 3);
		damage = damage	- target.calculateDamageReduction();
		if (damage < 0) {
			damage = 0;
		}
		out.println(damage + " damage!");
		target.loseHealth(damage);
		out.println(target.getFullName() + " has " + target.getHealth() + " health left.");
		
		// see if target has fallen
		if (target.isFallen()) {
			out.println(target.getFullName() + " is down for the count!");
		}
	}

}
