package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.atomic.HealCharacterAtomic;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.List;

/**
 * This effect causes the targeted {@link Character} to be healed.
 */
public class HealCharacterEffect implements TargetedEffect {

	private final int heal;
	private final TargetFinder targetFinder;

	public HealCharacterEffect(int heal, TargetFinder targetFinder) {
		this.heal = heal;
		this.targetFinder = targetFinder;
	}

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, final TargetRef targetRef) {
		return new HealCharacterAtomic(targetRef, heal).apply(state);
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
