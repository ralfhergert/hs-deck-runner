package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

import java.util.List;

/**
 * This atomic will execute all queued effects.
 */
public class ExecuteQueuedEffectsAtomic implements Action<HearthstoneGameState> {

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		final List<GeneralEffect> effects = nextState.getQueuedEffects();
		nextState.clearQueuedEffects();
		// fire off all effects.
		for (GeneralEffect effect : effects) {
			nextState = effect.applyTo(nextState);
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
