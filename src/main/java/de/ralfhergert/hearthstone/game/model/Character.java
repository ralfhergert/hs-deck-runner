package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.event.CharacterAttackedEvent;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.StartTurnEvent;

/**
 * A character is either a minion or a player.
 * @param <Self> type of the overriding class.
 */
public class Character<Self extends Character<Self>> implements Target, GameEventListener<HearthstoneGameState> {

	private TargetRef targetRef;
	private String name;

	private int currentHitPoints;
	private int maxHitPoints;
	private int power;
	private int armor;

	private boolean isImmune;
	private boolean isFrozen;
	private boolean isElusive; // can not be targeted by heroPowers or targeted spells.
	private boolean hasTaunt;

	private int numberOfAttacksThisTurn = 0;
	private int numberOfAttacksLimit = 1;

	public Character() {
		targetRef = new TargetRef();
	}

	/**
	 * Copy-constructor.
	 */
	public Character(Character other) {
		if (other == null) {
			throw new IllegalArgumentException("character can not be null");
		}
		targetRef = other.targetRef;
		name = other.name;
		currentHitPoints = other.currentHitPoints;
		maxHitPoints = other.maxHitPoints;
		power = other.power;
		armor = other.armor;
		isImmune = other.isImmune;
		isFrozen = other.isFrozen;
		isElusive = other.isElusive;
		hasTaunt = other.hasTaunt;
		numberOfAttacksThisTurn = other.numberOfAttacksThisTurn;
		numberOfAttacksLimit = other.numberOfAttacksLimit;
	}

	public TargetRef getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(TargetRef targetRef) {
		this.targetRef = targetRef;
	}

	public String getName() {
		return name;
	}

	public Self setName(String name) {
		this.name = name;
		return (Self)this;
	}

	public int getCurrentHitPoints() {
		return currentHitPoints;
	}

	public Self setCurrentHitPoints(int currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
		return (Self)this;
	}

	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public Self setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
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

	public boolean hasTaunt() {
		return hasTaunt;
	}

	public Self setHasTaunt(boolean hasTaunt) {
		this.hasTaunt = hasTaunt;
		return (Self)this;
	}

	public int getNumberOfAttacksThisTurn() {
		return numberOfAttacksThisTurn;
	}

	public Self setNumberOfAttacksThisTurn(int numberOfAttacksThisTurn) {
		this.numberOfAttacksThisTurn = numberOfAttacksThisTurn;
		return (Self)this;
	}

	public int getNumberOfAttacksLimit() {
		return numberOfAttacksLimit;
	}

	public Self setNumberOfAttacksLimit(int numberOfAttacksLimit) {
		this.numberOfAttacksLimit = numberOfAttacksLimit;
		return (Self)this;
	}

	/**
	 * Decreases the current hit points by the given amount of damage.
	 * @return the remaining hit points of this character.
	 */
	public int takeDamage(final int damage) {
		if (!isImmune) {
			// deal the damage to the armor first.
			armor -= damage;
			if (armor < 0) { // if the armor could not absorb the damage.
				currentHitPoints += armor;
				armor = 0;
			}
		}
		return currentHitPoints;
	}

	public int heal(final int heal) {
		// healing can not exceed the maximum hit points.
		currentHitPoints = Math.min(maxHitPoints, currentHitPoints + heal);
		return currentHitPoints;
	}

	public boolean isDamaged() {
		return currentHitPoints < maxHitPoints;
	}

	public int getAttack() {
		return power;
	}

	public boolean canAttack() {
		return getAttack() > 0 && !isFrozen && numberOfAttacksThisTurn < numberOfAttacksLimit;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		if (event instanceof StartTurnEvent) {
			StartTurnEvent startTurnEvent = (StartTurnEvent)event;
			if (state.findOwnerOrdinal(targetRef) == startTurnEvent.getPlayerOrdinal()) {
				state.findTarget(targetRef).setNumberOfAttacksThisTurn(0);
			}
		} else if (event instanceof CharacterAttackedEvent) {
			CharacterAttackedEvent attackedEvent = (CharacterAttackedEvent)event;
			if (targetRef.equals(attackedEvent.getAttackerTargetRef())) {
				Character attacker = state.findTarget(targetRef);
				attacker.setNumberOfAttacksThisTurn(1 + attacker.getNumberOfAttacksThisTurn());
			}
		}
		return state;
	}
}
