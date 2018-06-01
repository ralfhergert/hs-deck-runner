package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This event is send after a {@link Character} had been healed.
 */
public class CharacterHealedEvent extends GameEvent {

	private final TargetRef targetRef;
	private final int hitPointsAfter;

	public CharacterHealedEvent(TargetRef targetRef, int hitPointsAfter) {
		this.targetRef = targetRef;
		this.hitPointsAfter = hitPointsAfter;
	}

	public TargetRef getTargetRef() {
		return targetRef;
	}

	public int getHitPointsAfter() {
		return hitPointsAfter;
	}
}
