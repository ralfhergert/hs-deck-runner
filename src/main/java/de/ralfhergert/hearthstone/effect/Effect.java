package de.ralfhergert.hearthstone.effect;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * Marker interface for effects.
 */
public interface Effect {

	boolean isApplicableTo(HearthstoneGameState state);
}
