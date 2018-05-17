package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.StartGameEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.StartingHandState;
import de.ralfhergert.hearthstone.game.model.Turn;

/**
 * This action creates a new game of hearthstone with both given players.
 */
public class StartGameAction implements Action<HearthstoneGameState> {

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState previousState) {
		final HearthstoneGameState state = new HearthstoneGameState(null, this);
		state.onEvent(new StartGameEvent(state));
		return state.apply(new StartTurnAction(PlayerOrdinal.One));
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return state != null &&
			state.getTurn() == Turn.DrawStartingHand &&
			state.getPlayer(PlayerOrdinal.One).getStartingHandState() != StartingHandState.Undecided &&
			state.getPlayer(PlayerOrdinal.Two).getStartingHandState() != StartingHandState.Undecided;
	}
}
