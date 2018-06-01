package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.MinionTakesDamageEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Target;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This atomic will damage a minion.
 */
public class DamageMinionAtomic implements Action<HearthstoneGameState> {

	private final TargetRef targetRef;
	private final int damage;

	public DamageMinionAtomic(TargetRef targetRef, int damage) {
		if (targetRef == null) {
			throw new IllegalArgumentException("targetRef can not be null");
		}
		if (damage < 0) {
			throw new IllegalArgumentException("damage can not be smaller than 0");
		}
		this.targetRef = targetRef;
		this.damage = damage;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		if (damage < 1) {
			return state;
		}
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		Target target = nextState.findTarget(targetRef);
		if (target == null || !(target instanceof Minion)) {
			return nextState;
		}
		final Minion minion = (Minion)target;
		final int hitPointsBefore = minion.getCurrentHitPoints();
		final int hitPointsAfter = minion.takeDamage(damage);
		if (hitPointsBefore > hitPointsAfter) {
			return nextState.onEvent(new MinionTakesDamageEvent(minion, hitPointsBefore));
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
