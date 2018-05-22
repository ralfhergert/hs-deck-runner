package de.ralfhergert.hearthstone.effect;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * Interface for general effects.
 */
public interface GeneralEffect extends Effect {

	HearthstoneGameState applyTo(HearthstoneGameState state);
}
