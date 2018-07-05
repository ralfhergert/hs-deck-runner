package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.atomic.DestroyWeaponAtomic;
import de.ralfhergert.hearthstone.event.WeaponEntersBattlefieldEvent;
import de.ralfhergert.hearthstone.game.model.Card;
import de.ralfhergert.hearthstone.game.model.CardRef;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Weapon;
import de.ralfhergert.hearthstone.game.model.WeaponCard;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This action lets the active player play a weapon card from the hand.
 */
public class PlayWeaponCard implements Action<HearthstoneGameState> {

	private final CardRef weaponCardRef;

	public PlayWeaponCard(CardRef weaponCardRef) {
		this.weaponCardRef = weaponCardRef;
	}

	@Override
	public HearthstoneGameState apply(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		final Player player = nextState.getActivePlayer();
		final Card card = player.findInHand(weaponCardRef);
		if (card != null && card instanceof WeaponCard) {
			WeaponCard weaponCard = (WeaponCard)card;
			player.setAvailableMana(player.getAvailableMana() - weaponCard.getManaCost());
			player.setCrystalsLockedNextTurn(player.getCrystalsLockedNextTurn() + weaponCard.getOverloadCost());
			player.removeFromHand(weaponCard);
			player.addToPlayedCards(weaponCard);
			if (player.getWeapon() != null) {
				nextState = new DestroyWeaponAtomic(nextState.getPlayerOrdinal(player)).apply(nextState);
			}
			final Weapon weapon = weaponCard.getWeaponFactory().create();
			weapon.setActive(true);
			nextState.getActivePlayer().setWeapon(weapon);
			return nextState.onEvent(new WeaponEntersBattlefieldEvent(weapon.getWeaponRef()));
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		final Player player = state.getActivePlayer();
		if (player == null) {
			return false;
		}
		Card card = player.findInHand(weaponCardRef);
		return card != null &&
			card instanceof WeaponCard &&
			player.getAvailableMana() >= card.getManaCost();
	}
}
