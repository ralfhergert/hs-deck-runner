package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.CardDiscardedEvent;
import de.ralfhergert.hearthstone.game.model.Card;
import de.ralfhergert.hearthstone.game.model.CardRef;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This atomic will discard a card from a players hand.
 */
public class DiscardCardAtomic implements Action<HearthstoneGameState> {

	private final CardRef cardRef;

	public DiscardCardAtomic(CardRef cardRef) {
		if (cardRef == null) {
			throw new IllegalArgumentException("cardRef must not be null");
		}
		this.cardRef = cardRef;
	}

	@Override
	public HearthstoneGameState apply(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		final Player player = nextState.getOwner(cardRef);
		final Card card = player.findInHand(cardRef);
		if (card != null) {
			player.removeFromHand(card);
			return nextState.onEvent(new CardDiscardedEvent(nextState.getPlayerOrdinal(player)));
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
