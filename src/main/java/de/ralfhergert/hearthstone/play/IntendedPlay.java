package de.ralfhergert.hearthstone.play;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * Interface for all plays which can go onto the stack.
 */
public interface IntendedPlay {

	HearthstoneGameState execute(HearthstoneGameState state);
}
