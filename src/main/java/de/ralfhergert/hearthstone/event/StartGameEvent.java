package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * This event is send after the draw-starting-hand-phase before player one's turn starts.
 */
public class StartGameEvent extends GameEvent {

	public StartGameEvent(HearthstoneGameState state) {
		super(state);
	}
}
