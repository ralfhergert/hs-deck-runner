package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This event is fired after a player had been healed.
 */
public class PlayerHealedEvent extends CharacterHealedEvent {

	public PlayerHealedEvent(HearthstoneGameState state, TargetRef targetRef, int hitPointsAfter) {
		super(state, targetRef, hitPointsAfter);
	}
}
