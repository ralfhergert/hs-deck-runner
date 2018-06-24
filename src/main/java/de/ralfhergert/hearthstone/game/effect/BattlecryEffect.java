package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.MinionEntersBattlefieldEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * This effect gives the minion it is applied to charge.
 */
public class BattlecryEffect implements GeneralEffect, GameEventListener<HearthstoneGameState> {

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		return state;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		if (event instanceof MinionEntersBattlefieldEvent) {
			MinionEntersBattlefieldEvent minionEvent = (MinionEntersBattlefieldEvent)event;
			// is it the minion which owns this effect?
			if (state.getEffectOwner(this).getTargetRef().equals(minionEvent.getMinionRef())) {
				return applyTo(state);
			}
		}
		return state;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
