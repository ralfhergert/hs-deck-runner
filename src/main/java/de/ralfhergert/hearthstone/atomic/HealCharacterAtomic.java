package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.MinionHealedEvent;
import de.ralfhergert.hearthstone.event.PlayerHealedEvent;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.Target;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This atomic will heal a character (minion or player).
 */
public class HealCharacterAtomic implements Action<HearthstoneGameState> {

	private final TargetRef targetRef;
	private final int heal;

	public HealCharacterAtomic(TargetRef targetRef, int heal) {
		if (targetRef == null) {
			throw new IllegalArgumentException("targetRef can not be null");
		}
		if (heal < 1) {
			throw new IllegalArgumentException("heal can not be smaller than 1");
		}
		this.targetRef = targetRef;
		this.heal = heal;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		Target target = nextState.findTarget(targetRef);
		if (target == null || !(target instanceof Character)) {
			return nextState;
		}
		Character character = (Character)target;
		final int hitPointsBefore = character.getCurrentHitPoints();
		final int hitPointsAfter = character.heal(heal);
		if (hitPointsBefore < hitPointsAfter) {
			if (target instanceof Minion) {
				nextState.onEvent(new MinionHealedEvent(nextState, targetRef, hitPointsAfter));
			} else if (target instanceof Player) {
				nextState.onEvent(new PlayerHealedEvent(nextState, targetRef, hitPointsAfter));
			}
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
