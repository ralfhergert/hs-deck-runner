package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.List;

/**
 * This blank {@link TargetedEffect} can be used to be overwritten.
 */
public class BlankTargetedEffect implements TargetedEffect {

	private final TargetFinder targetFinder;

	public BlankTargetedEffect(TargetFinder targetFinder) {
		this.targetFinder = targetFinder;
	}

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, final TargetRef targetRef) {
		return state;
	}

	@Override
	public List<TargetRef> getPossibleTargets(HearthstoneGameState state) {
		return targetFinder.findPossibleTargets(state);
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
