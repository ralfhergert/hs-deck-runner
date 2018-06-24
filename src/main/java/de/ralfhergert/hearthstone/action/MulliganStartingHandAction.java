package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.card.CardFactory;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.StartingHandState;

/**
 * This action causes the play to keep his starting hand.
 */
public class MulliganStartingHandAction implements Action<HearthstoneGameState> {

	private final CardFactory cardFactory = new CardFactory();

	private final PlayerOrdinal playerOrdinal;

	public MulliganStartingHandAction(PlayerOrdinal playerOrdinal) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal can not be null");
		}
		this.playerOrdinal = playerOrdinal;
	}

	@Override
	public HearthstoneGameState apply(HearthstoneGameState previousState) {
		final HearthstoneGameState state = new HearthstoneGameState(previousState, this);
		{
			final Player player = state.getPlayers()[playerOrdinal.ordinal()];
			player.setStartingHandState(StartingHandState.Mulligan);
			// shuffle the hand back into the library.
			player.addToLibrary(player.removeAllFromHand());
			player.shuffleLibrary();
		}
		// we want the player to draw first the new cards before the coin is added.
		final HearthstoneGameState newHandState = new DrawCardsAction(playerOrdinal, playerOrdinal == PlayerOrdinal.One ? 4 : 3).apply(state);

		if (playerOrdinal == PlayerOrdinal.Two) {
			newHandState.getPlayer(playerOrdinal).addToHand(cardFactory.createCoin());
		}
		return newHandState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
