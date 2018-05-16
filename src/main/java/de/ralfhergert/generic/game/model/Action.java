package de.ralfhergert.generic.game.model;

/**
 * Interface for actions being applicable to the given state.
 * @param <State> type of state onto which the action is applicable.
 */
public interface Action<State extends GameState<State>> {

	/**
	 * This method performs this action onto the given state, creating a new state.
	 */
	State applyTo(State state);

	/**
	 * This method reports whether the given action would be applicable onto the given state.
	 */
	boolean isApplicableTo(State state);
}
