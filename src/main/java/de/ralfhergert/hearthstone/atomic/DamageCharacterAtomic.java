package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.MinionTakesDamageEvent;
import de.ralfhergert.hearthstone.event.PlayerTakesDamageEvent;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This atomic will damage a character.
 */
public class DamageCharacterAtomic implements Action<HearthstoneGameState> {

	private final TargetRef targetRef;
	private final int damage;

	public DamageCharacterAtomic(TargetRef targetRef, int damage) {
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
	public HearthstoneGameState apply(HearthstoneGameState state) {
		if (damage < 1) {
			return state;
		}
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		Character target = nextState.findTarget(targetRef);
		if (target == null) {
			return nextState;
		}
		final int hitPointsBefore = target.getCurrentHitPoints();
		final int hitPointsAfter = target.takeDamage(damage);
		if (hitPointsBefore > hitPointsAfter) {
			if (target instanceof Minion) {
				return nextState.onEvent(new MinionTakesDamageEvent((Minion)target, hitPointsBefore));
			} else if (target instanceof Player) {
				return nextState.onEvent(new PlayerTakesDamageEvent((Player)target, hitPointsBefore));
			}
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
