package de.ralfhergert.hearthstone.card;

import de.ralfhergert.hearthstone.game.minion.MinionFactory;
import de.ralfhergert.hearthstone.game.model.Card;
import de.ralfhergert.hearthstone.game.model.CardSet;
import de.ralfhergert.hearthstone.game.model.HeroClass;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Rarity;

import java.util.Arrays;
import java.util.List;

/**
 * This repository hold all known cards.
 */
public final class CardRepository {

	private static List<CardEntry<? extends Card>> cards = Arrays.asList(
		new MinionCardEntry(273, CardSet.Classic, Rarity.Common, HeroClass.Neutral, new MinionFactory().setManaCost(0).setMinionName("Wisp").setPower(1).setHitPoints(1)),
		new MinionCardEntry(346, CardSet.Classic, Rarity.Common, HeroClass.Neutral, new MinionFactory().setManaCost(4).setMinionName("Mogu'shan Warden").setPower(1).setHitPoints(7).setHasTaunt(true))
	);

	private CardRepository() { /* no instantiation necessary */ }

	public static Card createById(int id) {
		for (CardEntry<? extends Card> cardEntry : cards) {
			if (cardEntry.id == id) {
				return cardEntry.create();
			}
		}
		return null;
	}

	/**
	 * This is the super-class for all card entries in this repository.
	 * @param <Card> the card type this card entry will produce.
	 */
	private abstract static class CardEntry<Card> {

		private final int id;
		private final CardSet cardSet;
		private final Rarity rarity;
		private final HeroClass heroClass;

		public CardEntry(int id, CardSet cardSet, Rarity rarity, HeroClass heroClass) {
			this.id = id;
			this.cardSet = cardSet;
			this.rarity = rarity;
			this.heroClass = heroClass;
		}

		public int getId() {
			return id;
		}

		public abstract Card create();
	}

	/**
	 * This is a card entry for a minion card.
	 */
	private static class MinionCardEntry extends CardEntry<MinionCard> {

		private final MinionFactory minionFactory;

		public MinionCardEntry(int id, CardSet cardSet, Rarity rarity, HeroClass heroClass, MinionFactory minionFactory) {
			super(id, cardSet, rarity, heroClass);
			this.minionFactory = minionFactory;
		}

		@Override
		public MinionCard create() {
			return new MinionCard(minionFactory).setId(getId());
		}
	}
}
