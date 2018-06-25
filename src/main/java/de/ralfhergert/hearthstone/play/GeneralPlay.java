package de.ralfhergert.hearthstone.play;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * This class is used to store an {@link GeneralEffect} as intended play to come.
 * Intended plays are used to be counterable by a "Counterspell".
 */
public class GeneralPlay {

	private GeneralEffect effect;

	public GeneralPlay(GeneralEffect effect) {
		this.effect = effect;
	}

	public HearthstoneGameState execute(HearthstoneGameState state) {
		return effect != null ? effect.applyTo(state) : state;
	}
}
