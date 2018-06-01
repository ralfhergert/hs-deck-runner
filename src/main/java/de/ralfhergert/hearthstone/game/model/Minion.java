package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.atomic.DestroyMinionAtomic;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.MinionTakesDamageEvent;

/**
 * Represents a minion on the battlefield.
 */
public class Minion extends Character<Minion> implements GameEventListener<HearthstoneGameState> {

	private Card card; // the card this minion was summoned from.

	public Card getCard() {
		return card;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		if (event instanceof MinionTakesDamageEvent) {
			MinionTakesDamageEvent minionTakesDamageEvent = (MinionTakesDamageEvent)event;
			if (minionTakesDamageEvent.getCharacter().getTargetRef().equals(getTargetRef())) {
				Minion minion = (Minion)state.findTarget(getTargetRef());
				if (minion.getCurrentHitPoints() <= 0) {
					return state.apply(new DestroyMinionAtomic(this));
				}
			}
		}
		return super.onEvent(state, event);
	}
}
