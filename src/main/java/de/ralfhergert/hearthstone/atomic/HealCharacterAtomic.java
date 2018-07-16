package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.MinionHealedEvent;
import de.ralfhergert.hearthstone.event.PlayerHealedEvent;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
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
	public HearthstoneGameState apply(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		Character character = nextState.findTarget(targetRef);
		if (character == null) {
			return nextState;
		}
		final int hitPointsBefore = character.getCurrentHitPoints();
		final int hitPointsAfter = character.heal(heal);
		if (hitPointsBefore < hitPointsAfter) {
			if (character instanceof Minion) {
				return nextState.onEvent(new MinionHealedEvent(targetRef, hitPointsAfter));
			} else if (character instanceof Player) {
				return nextState.onEvent(new PlayerHealedEvent(targetRef, hitPointsAfter));
			}
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
