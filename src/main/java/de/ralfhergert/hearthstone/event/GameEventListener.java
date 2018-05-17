package de.ralfhergert.hearthstone.event;

/**
 * Interface for all class which want to receive {@link GameEvent}.
 */
public interface GameEventListener {

	void onEvent(GameEvent event);
}
