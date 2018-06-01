package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This event is send after the draw-starting-hand-phase before player one's turn starts.
 */
public class PlayerTakesDamageEvent extends CharacterTakesDamageEvent<Player> {

	public PlayerTakesDamageEvent(Player character, int hitPointsBefore) {
		super(character, hitPointsBefore);
	}
}
