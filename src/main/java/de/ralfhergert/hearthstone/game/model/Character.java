package de.ralfhergert.hearthstone.game.model;

/**
 * A character is either a minion or a player.
 * @param <Self> type of the overriding class.
 */
public class Character<Self extends Character<Self>> {

	private int hitPoints;
	private int power;
	private int armor;

	private boolean isImmune;
	private boolean isFrozen;

	public Character() {}

	/**
	 * Copy-constructor.
	 */
	public Character(Character other) {
		if (other == null) {
			throw new IllegalArgumentException("character can not be null");
		}
		hitPoints = other.hitPoints;
		power = other.power;
		armor = other.armor;
		isImmune = other.isImmune;
		isFrozen = other.isFrozen;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public Self setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
		return (Self)this;
	}

	public int getPower() {
		return power;
	}

	public Character setPower(int power) {
		this.power = power;
		return (Self)this;
	}

	public int getArmor() {
		return armor;
	}

	public Character setArmor(int armor) {
		this.armor = armor;
		return (Self)this;
	}

	public boolean isImmune() {
		return isImmune;
	}

	public Character setImmune(boolean immune) {
		isImmune = immune;
		return (Self)this;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public Character setFrozen(boolean frozen) {
		isFrozen = frozen;
		return (Self)this;
	}

	/**
	 * Decreases the current hit point by the given amount of damage.
	 * @return the remaining hit points of this character.
	 */
	public int takeDamage(final int damage) {
		if (!isImmune) {
			// deal the damage to the armor first.
			armor -= damage;
			if (armor < 0) { // if the armor could not absorb the damage.
				hitPoints += armor;
				armor = 0;
			}
		}
		return hitPoints;
	}

}
