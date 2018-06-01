package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This event is fired after a minion had been healed.
 */
public class MinionHealedEvent extends CharacterHealedEvent {

	public MinionHealedEvent(TargetRef targetRef, int hitPointsAfter) {
		super(targetRef, hitPointsAfter);
	}
}
