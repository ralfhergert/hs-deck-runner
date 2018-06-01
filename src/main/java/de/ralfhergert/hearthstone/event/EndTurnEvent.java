package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This event is send before the turn is switched to the other player.
 */
public class EndTurnEvent extends GameEvent {

	private final PlayerOrdinal playerOrdinal;

	public EndTurnEvent(PlayerOrdinal playerOrdinal) {
		this.playerOrdinal = playerOrdinal;
	}

	public PlayerOrdinal getPlayerOrdinal() {
		return playerOrdinal;
	}
}
