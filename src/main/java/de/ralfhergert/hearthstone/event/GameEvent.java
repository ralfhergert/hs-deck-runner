package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * This is the interface for all game events.
 */
public class GameEvent {

	private final HearthstoneGameState state;

	public GameEvent(HearthstoneGameState state) {
		this.state = state;
	}

	public HearthstoneGameState getState() {
		return state;
	}
}
