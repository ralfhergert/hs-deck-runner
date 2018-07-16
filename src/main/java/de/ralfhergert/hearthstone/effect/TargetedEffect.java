package de.ralfhergert.hearthstone.effect;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.List;

/**
 * Interface for targeted effects.
 */
public interface TargetedEffect extends Effect {

	HearthstoneGameState applyOn(HearthstoneGameState state, TargetRef targetRef);

	List<TargetRef> getPossibleTargets(HearthstoneGameState state);
}
