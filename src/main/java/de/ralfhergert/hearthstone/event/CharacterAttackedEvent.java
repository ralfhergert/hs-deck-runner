package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This event is send after a character attacked.
 */
public class CharacterAttackedEvent extends GameEvent {

	private final TargetRef attackerTargetRef;

	public CharacterAttackedEvent(TargetRef attackerTargetRef) {
		this.attackerTargetRef = attackerTargetRef;
	}

	public TargetRef getAttackerTargetRef() {
		return attackerTargetRef;
	}
}
