package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This event is send after a card has been discarded.
 */
public class CardDiscardedEvent extends GameEvent {

	private final PlayerOrdinal playerOrdinal;

	public CardDiscardedEvent(PlayerOrdinal playerOrdinal) {
		this.playerOrdinal = playerOrdinal;
	}

	public PlayerOrdinal getPlayerOrdinal() {
		return playerOrdinal;
	}
}
