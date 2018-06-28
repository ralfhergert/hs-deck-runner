package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.game.weapon.WeaponFactory;

/**
 * Implementation of a weapon card.
 */
public class WeaponCard extends Card<WeaponCard> {

	private WeaponFactory weaponFactory;

	public WeaponCard(WeaponFactory weaponFactory) {
		if (weaponFactory != null) {
			setManaCost(weaponFactory.getManaCost());
			setName(weaponFactory.getWeaponName());
			this.weaponFactory = weaponFactory;
		}
	}

	/**
	 * Copy-constructor.
	 */
	public WeaponCard(WeaponCard other) {
		super(other);
		weaponFactory = other.weaponFactory;
	}

	public WeaponFactory getWeaponFactory() {
		return weaponFactory;
	}

	public WeaponCard setWeaponFactory(WeaponFactory weaponFactory) {
		this.weaponFactory = weaponFactory;
		return this;
	}

	public Weapon createWeapon() {
		return weaponFactory.create().setCardSupplier(() -> this);
	}
}
