package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This event is fired after a player had been healed.
 */
public class PlayerHealedEvent extends CharacterHealedEvent {

	public PlayerHealedEvent(TargetRef targetRef, int hitPointsAfter) {
		super(targetRef, hitPointsAfter);
	}
}
