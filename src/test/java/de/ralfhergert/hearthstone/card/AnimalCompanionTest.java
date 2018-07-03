package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayAbilityCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import de.ralfhergert.util.FakeRandom;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Animal Companion' is working correctly.
 */
public class AnimalCompanionTest {

	@Test
	public void testPlayAnimalCompanion0() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(3)
			.addToHand(CardRepository.createByName("Animal Companion"));
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayAbilityCard.class, foundAction.getClass());
		// use a prepared random.
		startState.setRandom(new FakeRandom(0));
		// play the ability card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one, number of minions", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one, minion one, name", "Huffer", afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getName());
	}

	@Test
	public void testPlayAnimalCompanion1() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(3)
			.addToHand(CardRepository.createByName("Animal Companion"))
			.addToBattlefield(((MinionCard)CardRepository.createByName("Wisp")).createMinion());
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayAbilityCard.class, foundAction.getClass());
		// use a prepared random.
		startState.setRandom(new FakeRandom(1));
		// play the ability card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one, number of minions", 2, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one, minion one, name", "Wisp", afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getName());
		Assert.assertEquals("player one, minion two, name", "Leokk", afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(1).getName());
		Assert.assertEquals("Wisp should have been buffed by Leokk", 2, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getPower());
	}

	@Test
	public void testPlayAnimalCompanion2() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(3)
			.addToHand(CardRepository.createByName("Animal Companion"));
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayAbilityCard.class, foundAction.getClass());
		// use a prepared random.
		startState.setRandom(new FakeRandom(2));
		// play the ability card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one, number of minions", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one, minion one, name", "Misha", afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getName());
	}
}
