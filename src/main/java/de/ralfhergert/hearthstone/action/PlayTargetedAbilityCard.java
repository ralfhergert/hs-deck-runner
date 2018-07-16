package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.event.PushedPlayOnStackEvent;
import de.ralfhergert.hearthstone.game.model.AbilityCard;
import de.ralfhergert.hearthstone.game.model.Card;
import de.ralfhergert.hearthstone.game.model.CardRef;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.play.TargetedPlay;

/**
 * This action lets the active player play an {@link AbilityCard} from the hand.
 */
public class PlayTargetedAbilityCard implements Action<HearthstoneGameState> {

	private final CardRef abilityCardRef;
	private final TargetRef targetRef;

	public PlayTargetedAbilityCard(CardRef abilityCardRef, TargetRef targetRef) {
		this.abilityCardRef = abilityCardRef;
		this.targetRef = targetRef;
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
			if (effect instanceof TargetedEffect) {
				nextState.setIntendedPlay(new TargetedPlay((TargetedEffect)effect, targetRef));
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
