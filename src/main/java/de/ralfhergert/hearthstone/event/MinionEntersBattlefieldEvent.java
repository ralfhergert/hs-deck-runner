package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.MinionRef;

/**
 * This event is fired after a minion had been healed.
 */
public class MinionEntersBattlefieldEvent extends GameEvent {

	private final MinionRef minionRef;

	public MinionEntersBattlefieldEvent(MinionRef minionRef) {
		this.minionRef = minionRef;
	}

	public MinionRef getMinionRef() {
		return minionRef;
	}
}
