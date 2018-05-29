package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This event is send after a character attacked.
 */
public class CharacterAttackedEvent extends GameEvent {

	private final TargetRef attackerTargetRef;

	public CharacterAttackedEvent(HearthstoneGameState state, TargetRef attackerTargetRef) {
		super(state);
		this.attackerTargetRef = attackerTargetRef;
	}

	public TargetRef getAttackerTargetRef() {
		return attackerTargetRef;
	}
}
