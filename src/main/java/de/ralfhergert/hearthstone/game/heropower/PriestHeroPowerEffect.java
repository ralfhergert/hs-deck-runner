package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.atomic.HealCharacterAtomic;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.target.AnyNonElusiveCharacter;

import java.util.List;

/**
 * This is the effect of the priest hero power.
 */
public class PriestHeroPowerEffect implements TargetedEffect {

	private static final TargetFinder TARGET_FINDER = new AnyNonElusiveCharacter();
	private final int healAmount = 2;

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, TargetRef targetRef) {
		return state.apply(new HealCharacterAtomic(targetRef, getHealAmount()));
	}

	/**
	 * Returns all possible targets for this effect. Note that the original hearthstone
	 * game allows to heal undamaged characters, but since this action does not trigger
	 * other effects like "whenever [...] is healed", this implementation ignores all
	 * undamaged characters as valid targets.
	 */
	@Override
	public List<TargetRef> getPossibleTargets(HearthstoneGameState state) {
		return TARGET_FINDER.findPossibleTargets(state);
	}

	public int getHealAmount() {
		return healAmount;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
