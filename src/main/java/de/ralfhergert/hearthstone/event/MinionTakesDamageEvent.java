package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;

/**
 * This event is send after the draw-starting-hand-phase before player one's turn starts.
 */
public class MinionTakesDamageEvent extends CharacterTakesDamageEvent<Minion> {

	public MinionTakesDamageEvent(HearthstoneGameState state, Minion character, int hitPointsBefore) {
		super(state, character, hitPointsBefore);
	}
}
