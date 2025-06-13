package org.legoata.gamecharacter;

import org.legoata.equipment.Shield;
import org.legoata.equipment.Weapon;

public class EquipmentManager {
	private Weapon weapon;
	private Shield shield;
	
	public boolean equipWeapon(Weapon weapon) {
		if (weapon.isTwoHanded() && shield != null) {
			return false;
		} else {
			this.weapon = weapon;
			return true;
		}
	}
	
	public Weapon unequipWeapon() {
		Weapon removal = weapon;
		weapon = null;
		return removal;
	}
	
	public boolean equipShield(Shield shield) {
		this.shield = shield;
		return true;
	}
	
	public Shield unequipShield() {
		Shield removal = shield;
		shield = null;
		return removal;
	}
}
