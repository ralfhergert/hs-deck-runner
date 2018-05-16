package de.ralfhergert.hearthstone.card;

import de.ralfhergert.hearthstone.game.model.Card;

/**
 * This factory can create instances of available cards.
 */
public class CardFactory {

	public Card createCoin() {
		return new Card();
	}
}
