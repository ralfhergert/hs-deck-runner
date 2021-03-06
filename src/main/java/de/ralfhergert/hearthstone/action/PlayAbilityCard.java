package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.PushedPlayOnStackEvent;
import de.ralfhergert.hearthstone.game.model.AbilityCard;
import de.ralfhergert.hearthstone.game.model.Card;
import de.ralfhergert.hearthstone.game.model.CardRef;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.play.GeneralPlay;

/**
 * This action lets the active player play an {@link AbilityCard} from the hand.
 */
public class PlayAbilityCard implements Action<HearthstoneGameState> {

	private final CardRef abilityCardRef;

	public PlayAbilityCard(CardRef abilityCardRef) {
		this.abilityCardRef = abilityCardRef;
	}

	@Override
	public HearthstoneGameState apply(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		final Player player = nextState.getActivePlayer();
		final Card card = player.findInHand(abilityCardRef);
		if (card instanceof AbilityCard) {
			AbilityCard abilityCard = (AbilityCard)card;
			player.setAvailableMana(player.getAvailableMana() - abilityCard.getManaCost());
			player.setCrystalsLockedNextTurn(player.getCrystalsLockedNextTurn() + abilityCard.getOverloadCost());
			player.removeFromHand(abilityCard);
			player.addToPlayedCards(abilityCard);
			// push the play onto a stack to allow "Counterspell" or "Redirect" to manipulate it.
			final Effect effect = abilityCard.getEffect();
			if (effect instanceof GeneralEffect) {
				nextState.setIntendedPlay(new GeneralPlay((GeneralEffect)effect));
				nextState = nextState.onEvent(new PushedPlayOnStackEvent(nextState.getPlayerOrdinal(player)));
			}
			if (nextState.getIntendedPlay() != null) {
				return nextState.removeIntendedPlay().execute(nextState);
			}
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		final Player player = state.getActivePlayer();
		if (player == null) {
			return false;
		}
		Card card = player.findInHand(abilityCardRef);
		return card instanceof AbilityCard &&
			player.getAvailableMana() >= card.getManaCost();
	}
}
