package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This event is send after a player's weapon has been destroyed.
 */
public class WeaponDestroyedEvent extends GameEvent {

	private final PlayerOrdinal playerOrdinal;

	public WeaponDestroyedEvent(PlayerOrdinal playerOrdinal) {
		this.playerOrdinal = playerOrdinal;
	}

	public PlayerOrdinal getPlayerOrdinal() {
		return playerOrdinal;
	}
}
