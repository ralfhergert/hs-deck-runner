package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;

/**
 * Represents a minion on the battlefield.
 */
public class Minion extends Character<Minion> implements Target,GameEventListener {

	private Card card; // the card this minion was summoned from.

	public Card getCard() {
		return card;
	}

	@Override
	public void onEvent(GameEvent event) {}
}
