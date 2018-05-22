package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This event is send after the draw-starting-hand-phase before player one's turn starts.
 */
public class PlayerTakesDamageEvent extends CharacterTakesDamageEvent<Player> {

	public PlayerTakesDamageEvent(HearthstoneGameState state, Player character, int hitPointsBefore) {
		super(state, character, hitPointsBefore);
	}
}
