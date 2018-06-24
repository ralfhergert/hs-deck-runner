package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.MinionTakesDamageEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.TargetRef;

/**
 * This abstract effect is the template for "Whenever this minion takes damage"-effects.
 */
public class WheneverThisMinionTakesDamage implements GeneralEffect, GameEventListener<HearthstoneGameState> {

	/**
	 * Override this method if required.
	 */
	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		return state;
	}

	/**
	 * Override this method if required.
	 */
	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		return state;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		if (event instanceof MinionTakesDamageEvent) {
			final MinionTakesDamageEvent takesDamageEvent = (MinionTakesDamageEvent)event;
			final TargetRef eventOwner = takesDamageEvent.getCharacter().getTargetRef();
			if (state.getEffectOwner(this).getTargetRef().equals(eventOwner)) {
				return applyTo(state);
			}
		}
		return state;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
