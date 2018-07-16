package de.ralfhergert.hearthstone.play;

import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This class is used to store a {@link TargetedEffect} as intended play to come.
 * Intended plays are used to be counterable by a "Counterspell".
 */
public class TargetedPlay implements IntendedPlay {

	private TargetedEffect effect;
	private TargetRef targetRef;

	public TargetedPlay(TargetedEffect effect, TargetRef targetRef) {
		this.effect = effect;
		this.targetRef = targetRef;
	}

	@Override
	public HearthstoneGameState execute(HearthstoneGameState state) {
		return effect != null ? effect.applyOn(state, targetRef) : state;
	}
}
