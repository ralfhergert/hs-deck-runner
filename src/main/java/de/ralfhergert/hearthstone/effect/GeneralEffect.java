package de.ralfhergert.hearthstone.effect;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

public interface GeneralEffect extends Effect {

	HearthstoneGameState applyOn(HearthstoneGameState state);
}
