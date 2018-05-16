package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.GameOutcome;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;

/**
 * This action creates a new game of hearthstone with both given players.
 */
public class CreateGameAction implements Action<HearthstoneGameState> {

	private final Player[] players = new Player[2];

	public CreateGameAction(Player player1, Player player2) {
		if (player1 == null) {
			throw new IllegalArgumentException("player1 can not be null");
		}
		if (player2 == null) {
			throw new IllegalArgumentException("player2 can not be null");
		}
		players[0] = player1;
		players[1] = player2;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState previousState) {
		final HearthstoneGameState state = new HearthstoneGameState(null, this);
		state.setPlayers(players);
		state.setTurn(Turn.DrawStartingHand);
		state.setOutcome(GameOutcome.Undecided);

		return state
			.apply(new DrawCardsAction(PlayerOrdinal.One, 4))
			.apply(new DrawCardsAction(PlayerOrdinal.Two, 3));
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return state == null;
	}
}
