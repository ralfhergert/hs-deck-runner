package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.WeaponRef;

/**
 * This event is fired after a weapon entered the battlefield.
 */
public class WeaponEntersBattlefieldEvent extends GameEvent {

	private final WeaponRef weaponRef;

	public WeaponEntersBattlefieldEvent(WeaponRef weaponRef) {
		this.weaponRef = weaponRef;
	}

	public WeaponRef getWeaponRef() {
		return weaponRef;
	}
}
