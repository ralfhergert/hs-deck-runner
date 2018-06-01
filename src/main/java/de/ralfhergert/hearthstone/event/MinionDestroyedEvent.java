package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.Minion;

/**
 * This event is send after a minion has been destroyed.
 */
public class MinionDestroyedEvent extends CharacterDestroyedEvent<Minion> {

	public MinionDestroyedEvent(Minion character) {
		super(character);
	}
}
