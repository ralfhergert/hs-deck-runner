package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;

/**
 * Represents a card in library, hand or graveyard.
 */
public class Card implements GameEventListener<HearthstoneGameState> {

	private CardRef cardRef;

	private int manaCost;
	private String name;

	public Card() {
		cardRef = new CardRef();
	}

	/**
	 * Copy constructor.
	 */
	public Card(Card other) {
		if (other == null) {
			throw new IllegalArgumentException("other card must not be null");
		}
		cardRef = other.cardRef;
		manaCost = other.manaCost;
		name = other.name;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		return state;
	}
}
