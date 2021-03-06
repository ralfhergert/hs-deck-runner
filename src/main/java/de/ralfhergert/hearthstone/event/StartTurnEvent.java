package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This event is send after the draw-starting-hand-phase before player one's turn starts.
 */
public class StartTurnEvent extends GameEvent {

	private final PlayerOrdinal playerOrdinal;

	public StartTurnEvent(PlayerOrdinal playerOrdinal) {
		this.playerOrdinal = playerOrdinal;
	}

	public PlayerOrdinal getPlayerOrdinal() {
		return playerOrdinal;
	}
}
