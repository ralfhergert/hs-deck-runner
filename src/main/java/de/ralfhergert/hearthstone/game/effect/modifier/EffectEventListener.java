package de.ralfhergert.hearthstone.game.effect.modifier;

import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

/**
 * Effect may want to react to different events. The implementations of is interface
 * offer various default implementations, which effect can reuse.
 *
 * @param <E> Type of the effect this listener implementation is working for.
 */
public interface EffectEventListener<E extends Effect> {

	default HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event, E effect) {
		return state;
	}
}
