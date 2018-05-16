package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;

import java.util.ArrayList;
import java.util.List;

/**
 * This factory will find all applicable action for a given game state.
 */
public class ActionFactory {

	/**
	 * @param state for which all applicable actions should be found.
	 * @return list of actions
	 */
	public List<Action<HearthstoneGameState>> createAllApplicableActions(HearthstoneGameState state) {
		final List<Action<HearthstoneGameState>> foundActions = new ArrayList<>();
		return foundActions;
	}
}
