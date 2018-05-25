package de.ralfhergert.hearthstone.game.model;

/**
 * A character is either a minion or a player.
 * @param <Self> type of the overriding class.
 */
public class Character<Self extends Character<Self>> implements Target {

	private TargetRef targetRef = new TargetRef();

	private int hitPoints;
	private int power;
	private int armor;

	private boolean isImmune;
	private boolean isFrozen;
	private boolean isElusive; // can not be targeted by heroPowers or targeted spells.

	public Character() {}

	/**
	 * Copy-constructor.
	 */
	public Character(Character other) {
		if (other == null) {
			throw new IllegalArgumentException("character can not be null");
		}
		targetRef = other.targetRef;
		hitPoints = other.hitPoints;
		power = other.power;
		armor = other.armor;
		isImmune = other.isImmune;
		isFrozen = other.isFrozen;
	}

	public TargetRef getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(TargetRef targetRef) {
		this.targetRef = targetRef;
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

	public Self setPower(int power) {
		this.power = power;
		return (Self)this;
	}

	public int getArmor() {
		return armor;
	}

	public Self setArmor(int armor) {
		this.armor = armor;
		return (Self)this;
	}

	public boolean isImmune() {
		return isImmune;
	}

	public Self setImmune(boolean immune) {
		isImmune = immune;
		return (Self)this;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public Self setFrozen(boolean frozen) {
		isFrozen = frozen;
		return (Self)this;
	}

	public boolean isElusive() {
		return isElusive;
	}

	public Self setElusive(boolean elusive) {
		isElusive = elusive;
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