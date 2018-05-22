package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.MinionTakesDamageEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;

/**
 * This atomic will damage a minion.
 */
public class DamageMinionAtomic implements Action<HearthstoneGameState> {

	private final Minion minion;
	private final int damage;

	public DamageMinionAtomic(Minion minion, int damage) {
		if (minion == null) {
			throw new IllegalArgumentException("minion can not be null");
		}
		if (damage < 1) {
			throw new IllegalArgumentException("damage can not be smaller than 1");
		}
		this.minion = minion;
		this.damage = damage;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		final int hitPointsBefore = minion.getHitPoints();
		final int hitPointsAfter = minion.takeDamage(damage);
		if (hitPointsBefore > hitPointsAfter) {
			nextState.onEvent(new MinionTakesDamageEvent(nextState, minion, hitPointsBefore));
		}
		// firing the event might have altered the minion's hit points, so don't use hitPointsAfter.
		return (minion.getHitPoints() < 1)
			? new DestroyMinionAtomic(minion).applyTo(nextState)
			: nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
