package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.WeaponEquippedEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Weapon;

/**
 * This atomic will equip the given player with the given weapon.
 */
public class EquipWeaponAtomic implements Action<HearthstoneGameState> {

	private final PlayerOrdinal playerOrdinal;
	private final Weapon weapon;

	public EquipWeaponAtomic(PlayerOrdinal playerOrdinal, Weapon weapon) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal can not be null");
		}
		if (weapon == null) {
			throw new IllegalArgumentException("weapon can not be null");
		}
		this.playerOrdinal = playerOrdinal;
		this.weapon = weapon;
	}

	@Override
	public HearthstoneGameState apply(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		if (nextState.getPlayer(playerOrdinal).getWeapon() != null) {
			nextState = nextState.apply(new DestroyWeaponAtomic(playerOrdinal));
		}
		nextState.getPlayer(playerOrdinal).setWeapon(weapon);
		return nextState.onEvent(new WeaponEquippedEvent(playerOrdinal));
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
