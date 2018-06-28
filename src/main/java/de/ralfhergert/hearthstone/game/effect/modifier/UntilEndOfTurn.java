package de.ralfhergert.hearthstone.game.effect.modifier;

import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.event.EndTurnEvent;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * This abstract effect is the template for "Until the end of your turn"-effects.
 *
 * @param <E> Type of the effect this listener implementation is working for.
 */
public class UntilEndOfTurn<E extends Effect> implements EffectEventListener<E> {

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event, E effect) {
		if (event instanceof EndTurnEvent) {
			EndTurnEvent endTurnEvent = (EndTurnEvent)event;
			// if it is the owner of this effect end-of-turn.
			if (state.getPlayerOrdinal(state.getOwner(effect)) == endTurnEvent.getPlayerOrdinal()) {
				return effect.unapplyOn(state);
			}
		}
		return state;
	}
}
