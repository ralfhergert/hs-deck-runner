package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;

/**
 * Represents a played and active weapon on the board.
 */
public class Weapon implements GameEventListener {

	private int attack;
	private int durability;

	public Weapon() {}

	/**
	 * Copy-constructor.
	 */
	public Weapon(Weapon weapon) {
		if (weapon == null) {
			throw new IllegalArgumentException("weapon can not be null");
		}
		attack = weapon.attack;
		durability = weapon.durability;
	}

	public int getAttack() {
		return attack;
	}

	public Weapon setAttack(int attack) {
		this.attack = attack;
		return this;
	}

	public int getDurability() {
		return durability;
	}

	public Weapon setDurability(int durability) {
		this.durability = durability;
		return this;
	}

	@Override
	public void onEvent(GameEvent event) {}
}
