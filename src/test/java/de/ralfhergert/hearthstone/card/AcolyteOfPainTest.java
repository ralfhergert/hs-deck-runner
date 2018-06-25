package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayAbilityCard;
import de.ralfhergert.hearthstone.action.PlayMinionCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Acolyte of Pain' is working correctly.
 */
public class AcolyteOfPainTest {

	@Test
	public void testPlayAcolyteOfPainIntoHellfire() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(7)
			.addToHand(CardRepository.createByName("Acolyte of Pain"))
			.addToHand(CardRepository.createByName("Hellfire"))
			.addToLibrary(CardRepository.createByName("Wisp"));
		final Player player2 = new Player()
			.setHitPoints(30);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		actions = ActionUtil.remove(actions, PlayAbilityCard.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayMinionCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 4, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have one card in hand", 1, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one should have one minion on board", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one should have one card in library", 1, afterState.getPlayer(PlayerOrdinal.One).getLibrary().size());
		// play 'hellfire'
		actions = new ActionFactory().createAllApplicableActions(afterState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayAbilityCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState finalState = foundAction.apply(afterState);
		Assert.assertNotNull("final state should not be null", finalState);
		Assert.assertEquals("player one should have less mana", 0, finalState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, finalState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have one card in hand", 1, finalState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one's card in hand should be a 'Wisp'", "Wisp", finalState.getPlayer(PlayerOrdinal.One).getHand().get(0).getName());
		Assert.assertEquals("player one should have one minion on board", 0, finalState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player one should have one card in library", 0, finalState.getPlayer(PlayerOrdinal.One).getLibrary().size());
	}
}
