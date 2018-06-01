package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This event is send after a player has been destroyed.
 */
public class PlayerDestroyedEvent extends CharacterDestroyedEvent<Player> {

	public PlayerDestroyedEvent(Player character) {
		super(character);
	}
}
