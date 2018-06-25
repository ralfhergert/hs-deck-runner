package de.ralfhergert.hearthstone.effect;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * Marker interface for effects.
 */
public interface Effect {

	default boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}

	default HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		return state;
	}
}
