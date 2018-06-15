package de.ralfhergert.hearthstone.card;

import de.ralfhergert.hearthstone.game.model.Card;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link CardRepository} is working correctly.
 */
public class CardRepositoryTest {

	@Test
	public void testRetrievalOfCardById() {
		final Card card = CardRepository.createById(273);
		Assert.assertNotNull("card should not be null", card);
		Assert.assertNotNull("card cardRef should not be null", card.getCardRef());
		Assert.assertEquals("id should be", 273, card.getId());
		Assert.assertEquals("mana cost should be", 0, card.getManaCost());
		Assert.assertEquals("name should be", "Wisp", card.getName());
	}

	@Test
	public void testRetrievalOfUnknownCardById() {
		final Card card = CardRepository.createById(-1);
		Assert.assertNull("card should be null", card);
	}

	@Test
	public void testCreatedCardsHaveDistinctCardRefs() {
		final Card card1 = CardRepository.createById(346);
		final Card card2 = CardRepository.createById(346);
		Assert.assertNotNull("card1 should not be null", card1);
		Assert.assertNotNull("card1 cardRef should not be null", card1.getCardRef());
		Assert.assertNotNull("card2 should not be null", card2);
		Assert.assertNotNull("card2 cardRef should not be null", card2.getCardRef());
		Assert.assertNotEquals("cardRefs should be different", card1.getCardRef(), card2.getCardRef());
	}
}
