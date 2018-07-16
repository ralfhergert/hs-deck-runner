package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.atomic.DamageCharacterAtomic;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.List;

/**
 * This effect causes spell-damage to be inflicted to a target {@link Character}.
 * The damage is effected by the active player's spell damage modifier.
 */
public class DamageCharacterBySpellEffect implements TargetedEffect {

	private final int damage;
	private final TargetFinder targetFinder;

	public DamageCharacterBySpellEffect(int damage, TargetFinder targetFinder) {
		this.damage = damage;
		this.targetFinder = targetFinder;
	}

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, final TargetRef targetRef) {
		final Character target = state.findTarget(targetRef);
		if (target != null) {
			return new DamageCharacterAtomic(targetRef, getDamage(state)).apply(state);
		}
		return state;
	}

	public int getDamage(HearthstoneGameState state) {
		return damage + state.getActivePlayer().getSpellDamage();
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
