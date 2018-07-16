package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.atomic.DamageCharacterAtomic;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.List;

/**
 * This effect causes spell-damage to be inflicted to a target {@link Character}.
 * The damage is effected by the active player's spell damage modifier.
 */
public class SpellDamageCharacterEffect implements TargetedEffect {

	private final int damage;
	private final TargetFinder targetFinder;

	public SpellDamageCharacterEffect(int damage, TargetFinder targetFinder) {
		this.damage = damage;
		this.targetFinder = targetFinder;
	}

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, final TargetRef targetRef) {
		final Player player = state.getActivePlayer();
		final Character target = state.findTarget(targetRef);
		if (target != null) {
			return new DamageCharacterAtomic(targetRef, damage + player.getSpellDamage()).apply(state);
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
