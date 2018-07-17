package de.ralfhergert.hearthstone.card;

import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.PlayTargetedAbilityCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test checks that 'Mortal Coil' is working correctly.
 */
public class MortalCoilTest {

	@Test
	public void testPlayMortalCoinNotDrawingACard() {
		final Minion ooze = ((MinionCard)CardRepository.createByName("Acidic Swamp Ooze")).createMinion();
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(1)
			.addToHand(CardRepository.createByName("Mortal Coil"))
			.addToBattlefield(ooze);
		final Player player2 = new Player()
			.setHitPoints(30);
		HearthstoneGameState state = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		state.setTurn(Turn.Player1Turn);
		state = ActionUtil.playAction(state, action ->
			action instanceof PlayTargetedAbilityCard && ((PlayTargetedAbilityCard)action).getTargetRef().equals(ooze.getTargetRef()));
		Assert.assertNotNull("after state should not be null", state);
		Assert.assertEquals("player one should have less mana", 0, state.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, state.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, state.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one, current health", 30, state.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
		Assert.assertEquals("player one, number of minions", 1, state.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one, minion one, current health", 1, state.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getCurrentHitPoints());
	}

	@Test
	public void testPlayMortalCoinDrawingACard() {
		final Minion boar = ((MinionCard)CardRepository.createByName("Stonetusk Boar")).createMinion();
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(1)
			.addToHand(CardRepository.createByName("Mortal Coil"))
			.addToLibrary(CardRepository.createByName("Shadow Bolt"))
			.addToBattlefield(boar);
		final Player player2 = new Player()
			.setHitPoints(30);
		HearthstoneGameState state = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		state.setTurn(Turn.Player1Turn);
		state = ActionUtil.playAction(state, action ->
			action instanceof PlayTargetedAbilityCard && ((PlayTargetedAbilityCard)action).getTargetRef().equals(boar.getTargetRef()));
		Assert.assertNotNull("after state should not be null", state);
		Assert.assertEquals("player one should have less mana", 0, state.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, state.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 1, state.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one, card in hand, name", "Shadow Bolt", state.getPlayer(PlayerOrdinal.One).getHand().get(0).getName());
		Assert.assertEquals("player one, current health", 30, state.getPlayer(PlayerOrdinal.One).getCurrentHitPoints());
		Assert.assertEquals("player one, number of minions", 0, state.getPlayer(PlayerOrdinal.One).getBattlefield().size());
	}
}
