package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;

/**
 * Represents a minion on the battlefield.
 */
public class Minion implements Character,GameEventListener {

	private Card card; // the card this minion was summoned from.
	private int hitPoints;

	@Override
	public int getHitPoints() {
		return hitPoints;
	}

	@Override
	public int takeDamage(int damage) {
		hitPoints -= damage;
		return hitPoints;
	}

	public Card getCard() {
		return card;
	}

	@Override
	public void onEvent(GameEvent event) {}
}
