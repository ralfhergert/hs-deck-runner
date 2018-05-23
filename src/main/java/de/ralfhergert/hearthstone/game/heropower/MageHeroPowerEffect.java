package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.atomic.DamageMinionAtomic;
import de.ralfhergert.hearthstone.atomic.DamagePlayerAtomic;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.Target;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the effect of the mage hero power.
 */
public class MageHeroPowerEffect implements TargetedEffect {

	private final int inflictDamage = 1;

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, TargetRef targetRef) {
		Target target = state.findTarget(targetRef);
		if (target instanceof Player) {
			return new DamagePlayerAtomic(state.getPlayerOrdinal((Player)target), getInflictDamage()).applyTo(state);
		} else if (target instanceof Minion) {
			return new DamageMinionAtomic(targetRef, getInflictDamage()).applyTo(state);
		}
		return state;
	}

	@Override
	public List<Target> getPossibleTargets(HearthstoneGameState state) {
		final List<Target> targets = new ArrayList<>();
		final Player opponent = state.getOpposingPlayer(state.getOwner(this));
		if (!opponent.isElusive()) {
			targets.add(opponent);
		}
		for (Minion minion : opponent.getBattlefield()) {
			if (!minion.isElusive()) {
				targets.add(minion);
			}
		}
		return targets;
	}

	public int getInflictDamage() {
		return inflictDamage;
	}
}
