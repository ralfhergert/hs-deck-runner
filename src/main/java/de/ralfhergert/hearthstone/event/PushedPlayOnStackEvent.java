package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This event is send after an intended play has been created.
 */
public class PushedPlayOnStackEvent extends GameEvent {

	private final PlayerOrdinal playerOrdinal;

	public PushedPlayOnStackEvent(PlayerOrdinal playerOrdinal) {
		this.playerOrdinal = playerOrdinal;
	}

	public PlayerOrdinal getPlayerOrdinal() {
		return playerOrdinal;
	}
}
