package de.ralfhergert.hearthstone.event;

/**
 * Interface for all class which want to receive {@link GameEvent}.
 * @param <State> onto which the event can be applied.
 */
public interface GameEventListener<State> {

	State onEvent(State state, GameEvent event);
}
