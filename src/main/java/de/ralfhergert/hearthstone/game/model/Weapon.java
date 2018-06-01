package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.atomic.DestroyWeaponAtomic;
import de.ralfhergert.hearthstone.event.CharacterAttackedEvent;
import de.ralfhergert.hearthstone.event.EndTurnEvent;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.StartTurnEvent;

/**
 * Represents a played and active weapon on the board.
 */
public class Weapon implements GameEventListener<HearthstoneGameState> {

	private WeaponRef weaponRef;

	private int attack;
	private int durability;
	private boolean isActive;

	public Weapon() {
		weaponRef = new WeaponRef();
	}

	/**
	 * Copy-constructor.
	 */
	public Weapon(Weapon weapon) {
		if (weapon == null) {
			throw new IllegalArgumentException("weapon can not be null");
		}
		weaponRef = weapon.weaponRef;
		attack = weapon.attack;
		durability = weapon.durability;
		isActive = weapon.isActive;
	}

	public WeaponRef getWeaponRef() {
		return weaponRef;
	}

	public Weapon setWeaponRef(WeaponRef weaponRef) {
		this.weaponRef = weaponRef;
		return this;
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

	public boolean isActive() {
		return isActive;
	}

	public Weapon setActive(boolean active) {
		isActive = active;
		return this;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		if (event instanceof CharacterAttackedEvent) {
			CharacterAttackedEvent characterAttackedEvent = (CharacterAttackedEvent)event;
			// was the owner of this weapon the attacker?
			if (characterAttackedEvent.getAttackerTargetRef().equals(state.getOwner(weaponRef).getTargetRef())) {
				durability--;
				if (durability == 0) {
					return state.apply(new DestroyWeaponAtomic(state.getPlayerOrdinal(state.getOwner(weaponRef))));
				}
			}
		} else if (event instanceof EndTurnEvent) {
			EndTurnEvent endTurnEvent = (EndTurnEvent)event;
			// is it this weapon's owner end of turn?
			if (endTurnEvent.getPlayerOrdinal() == state.getPlayerOrdinal(state.getOwner(weaponRef))) {
				state.getPlayer(endTurnEvent.getPlayerOrdinal()).getWeapon().setActive(false);
			}
		} else if (event instanceof StartTurnEvent) {
			StartTurnEvent startTurnEvent = (StartTurnEvent)event;
			// is it this weapon's owner start of turn?
			if (startTurnEvent.getPlayerOrdinal() == state.getPlayerOrdinal(state.getOwner(weaponRef))) {
				state.getPlayer(startTurnEvent.getPlayerOrdinal()).getWeapon().setActive(true);
			}
		}
		return state;
	}
}
