package de.ralfhergert.hearthstone.event;

import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This event is fired after a minion had been healed.
 */
public class MinionEntersBattlefieldEvent extends GameEvent {

	private final TargetRef minionRef;

	public MinionEntersBattlefieldEvent(TargetRef minionRef) {
		this.minionRef = minionRef;
	}

	public TargetRef getMinionRef() {
		return minionRef;
	}
}
