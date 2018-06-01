package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This event is send after a player has equipped a weapon.
 */
public class WeaponEquippedEvent extends GameEvent {

	private final PlayerOrdinal playerOrdinal;

	public WeaponEquippedEvent(PlayerOrdinal playerOrdinal) {
		this.playerOrdinal = playerOrdinal;
	}

	public PlayerOrdinal getPlayerOrdinal() {
		return playerOrdinal;
	}
}
