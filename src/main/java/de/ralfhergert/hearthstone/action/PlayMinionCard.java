package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.MinionEntersBattlefieldEvent;
import de.ralfhergert.hearthstone.game.model.Card;
import de.ralfhergert.hearthstone.game.model.CardRef;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This action lets the active player play a minion card from the hand.
 */
public class PlayMinionCard implements Action<HearthstoneGameState> {

	private final CardRef minionCardRef;
	private final int position;

	public PlayMinionCard(CardRef minionCardRef, int position) {
		this.minionCardRef = minionCardRef;
		this.position = position;
	}

	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		final Player player = nextState.getActivePlayer();
		final Card card = player.findInHand(minionCardRef);
		if (card != null && card instanceof MinionCard) {
			MinionCard minionCard = (MinionCard)card;
			player.setAvailableMana(player.getAvailableMana() - minionCard.getManaCost());
			player.setCrystalsLockedNextTurn(player.getCrystalsLockedNextTurn() + minionCard.getOverloadCost());
			player.removeFromHand(minionCard);
			player.addToPlayedCards(minionCard);
			final Minion minion = minionCard.getMinionFactory().create();
			Effect effect = minion.getBattlecry();
			if (effect != null) {
				if (effect instanceof GeneralEffect) {
					nextState = ((GeneralEffect)effect).applyTo(nextState);
				}
			}
			nextState.getActivePlayer().addToBattlefield(minion, position);
			return nextState.onEvent(new MinionEntersBattlefieldEvent(minion.getTargetRef()));
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		final Player player = state.getActivePlayer();
		if (player == null) {
			return false;
		}
		Card card = player.findInHand(minionCardRef);
		return card != null &&
			card instanceof MinionCard &&
			player.getAvailableMana() >= card.getManaCost();
	}
}
