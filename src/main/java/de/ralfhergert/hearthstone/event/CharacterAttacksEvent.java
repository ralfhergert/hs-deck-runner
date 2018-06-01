package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This event is send when a character is about to attack.
 * This event gives effect the possibility to alter the target of the attack.
 */
public class CharacterAttacksEvent extends GameEvent {

	private final TargetRef attackerTargetRef;

	public CharacterAttacksEvent(TargetRef attackerTargetRef) {
		this.attackerTargetRef = attackerTargetRef;
	}

	public TargetRef getAttackerTargetRef() {
		return attackerTargetRef;
	}
}
