package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.atomic.DestroyWeaponAtomic;
import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.event.CharacterAttackedEvent;
import de.ralfhergert.hearthstone.event.EndTurnEvent;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.StartTurnEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * Represents a played and active weapon on the board.
 */
public class Weapon implements GameEventListener<HearthstoneGameState> {

	/** The card this weapon was created from. */
	private Supplier<WeaponCard> cardSupplier;

	private WeaponRef weaponRef;

	private String name;
	private int attack;
	private int durability;
	private boolean isActive;

	private List<Effect> effects = new ArrayList<>();

	public Weapon() {
		weaponRef = new WeaponRef();
	}

	/**
	 * Copy-constructor.
	 */
	public Weapon(Weapon weapon) {
		if (weapon == null) {
			throw new IllegalArgumentException("weapon must not be null");
		}
		weaponRef = weapon.weaponRef;
		name = weapon.name;
		attack = weapon.attack;
		durability = weapon.durability;
		isActive = weapon.isActive;
		effects.addAll(weapon.effects);
	}

	public Supplier<WeaponCard> getCardSupplier() {
		return cardSupplier;
	}

	public Weapon setCardSupplier(Supplier<WeaponCard> cardSupplier) {
		this.cardSupplier = cardSupplier;
		return this;
	}

	public WeaponRef getWeaponRef() {
		return weaponRef;
	}

	public Weapon setWeaponRef(WeaponRef weaponRef) {
		this.weaponRef = weaponRef;
		return this;
	}

	public String getName() {
		return name;
	}

	public Weapon setName(String name) {
		this.name = name;
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

	public Weapon addEffects(Collection<Effect> effects) {
		this.effects.addAll(effects);
		return this;
	}

	public Weapon addEffect(Effect effect) {
		effects.add(effect);
		return this;
	}

	public Weapon removeEffect(Effect effect) {
		effects.remove(effect);
		return this;
	}

	public boolean isEffectedBy(Effect effect) {
		return effects.contains(effect);
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
