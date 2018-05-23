package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.hearthstone.atomic.DamageMinionAtomic;
import de.ralfhergert.hearthstone.atomic.DamagePlayerAtomic;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the effect of the mage hero power.
 */
public class MageHeroPowerEffect implements TargetedEffect {

	private final int inflictDamage = 1;

	@Override
	public HearthstoneGameState applyOn(HearthstoneGameState state, Target target) {
		if (target instanceof Player) {
			return new DamagePlayerAtomic((Player)target, getInflictDamage()).applyTo(state);
		} else if (target instanceof Minion) {
			return new DamageMinionAtomic((Minion)target, getInflictDamage()).applyTo(state);
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
