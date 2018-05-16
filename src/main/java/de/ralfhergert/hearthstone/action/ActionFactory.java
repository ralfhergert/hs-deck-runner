package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.StartingHandState;
import de.ralfhergert.hearthstone.game.model.Turn;

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

		if (state.getTurn() == Turn.DrawStartingHand) {
			for (PlayerOrdinal playerOrdinal : PlayerOrdinal.values()) {
				if (state.getPlayers()[playerOrdinal.ordinal()].getStartingHandState() == StartingHandState.Undecided) {
					foundActions.add(new KeepStartingHandAction(playerOrdinal));
					foundActions.add(new MulliganStartingHandAction(playerOrdinal));
				}
			}
			if (state.getPlayers()[0].getStartingHandState() != StartingHandState.Undecided &&
				state.getPlayers()[1].getStartingHandState() != StartingHandState.Undecided) {
				foundActions.add(new StartGameAction());
			}
		}
		return foundActions;
	}
}
