package de.ralfhergert.generic.game.model;

/**
 * Base class for all game states.
 * @param <Self> type of the implementing class.
 */
public class GameState<Self extends GameState<Self>> {

	/** the previous game state. */
	private final Self parent;
	/** the action which was taken onto the parent state to get to the current state. */
	private final Action<Self> action;

	public GameState(Self parent, Action<Self> action) {
		this.parent = parent;
		this.action = action;
	}

	public Self apply(Action<Self> action) {
		return action.applyTo((Self)this);
	}
}
