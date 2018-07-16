package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.atomic.DestroyMinionAtomic;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.List;

/**
 * This effect causes the targeted {@link Minion} to be destroyed.
 */
public class DestroyMinionEffect implements TargetedEffect {

	private final TargetFinder targetFinder;

	public DestroyMinionEffect(TargetFinder targetFinder) {
		this.targetFinder = targetFinder;
	}

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, final TargetRef targetRef) {
		final Character target = state.findTarget(targetRef);
		if (target instanceof Minion) {
			return new DestroyMinionAtomic((Minion)target).apply(state);
		}
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
