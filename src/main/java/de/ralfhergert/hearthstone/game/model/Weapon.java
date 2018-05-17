package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;

/**
 * Represents a played and active weapon on the board.
 */
public class Weapon implements GameEventListener {

	public Weapon() {}

	/**
	 * Copy-constructor.
	 */
	public Weapon(Weapon weapon) {}

	@Override
	public void onEvent(GameEvent event) {}
}
