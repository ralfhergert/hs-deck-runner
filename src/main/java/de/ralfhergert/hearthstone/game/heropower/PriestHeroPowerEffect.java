package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.atomic.HealCharacterAtomic;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.Target;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the effect of the priest hero power.
 */
public class PriestHeroPowerEffect implements TargetedEffect {

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
	public List<Target> getPossibleTargets(HearthstoneGameState state) {
		final List<Target> targets = new ArrayList<>();
		final Player owner = state.getOwner(this);
		if (owner.isDamaged()) {
			targets.add(owner);
		}
		for (Minion minion : owner.getBattlefield()) {
			if (minion.isDamaged()) {
				targets.add(minion);
			}
		}
		final Player opponent = state.getOpposingPlayer(state.getOwner(this));
		for (Minion minion : opponent.getBattlefield()) {
			if (minion.isDamaged() && !minion.isElusive()) {
				targets.add(minion);
			}
		}
		return targets;
	}

	public int getHealAmount() {
		return healAmount;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
