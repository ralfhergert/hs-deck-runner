package de.ralfhergert.hearthstone.game.effect.modifier;

import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * This implementation of {@link EffectEventListener} does not react to events at all.
 *
 * @param <E> Type of the effect this listener implementation is working for.
 */
public class NoEffectEventListener<E extends Effect> implements EffectEventListener<E> {

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event, E effect) {
		return state;
	}
}
