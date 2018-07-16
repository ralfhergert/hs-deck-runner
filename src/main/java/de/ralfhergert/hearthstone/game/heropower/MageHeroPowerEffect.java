package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.atomic.DamageMinionAtomic;
import de.ralfhergert.hearthstone.atomic.DamagePlayerAtomic;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.Target;
import de.ralfhergert.hearthstone.game.model.TargetFinder;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.target.AnyNonElusiveCharacter;

import java.util.List;

/**
 * This is the effect of the mage hero power.
 */
public class MageHeroPowerEffect implements TargetedEffect {

	private static final TargetFinder TARGET_FINDER = new AnyNonElusiveCharacter();
	private final int inflictDamage = 1;

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, TargetRef targetRef) {
		Target target = state.findTarget(targetRef);
		if (target instanceof Player) {
			return new DamagePlayerAtomic(state.getPlayerOrdinal((Player)target), getInflictDamage()).apply(state);
		} else if (target instanceof Minion) {
			return new DamageMinionAtomic(targetRef, getInflictDamage()).apply(state);
		}
		return state;
	}

	@Override
	public List<TargetRef> getPossibleTargets(HearthstoneGameState state) {
		return TARGET_FINDER.findPossibleTargets(state);
	}

	public int getInflictDamage() {
		return inflictDamage;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
