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
 * This test checks that 'Starving Buzzard' is working correctly.
 */
public class StarvingBuzzardTest {

	@Test
	public void testPlayStarvingBuzzard() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(8)
			.addToHand(CardRepository.createByName("Starving Buzzard"))
			.addToHand(CardRepository.createByName("Animal Companion"))
			.addToLibrary(CardRepository.createByName("Acolyte of Pain"));
		final Player player2 = new Player();
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
		Assert.assertEquals("player one should have less mana", 3, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one, cards in hand", 1, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one, minions on board", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		// summon a beast with 'Animal Companion'
		HearthstoneGameState finalState = new ActionFactory().createAllApplicableActions(afterState).stream()
			.filter(action -> action instanceof PlayAbilityCard)
			.findFirst()
			.map(action -> action.apply(afterState))
			.orElseThrow(() -> new AssertionError("no PlayAbilityAction found"));
		Assert.assertNotNull("final state should not be null", finalState);
		Assert.assertEquals("player one should have less mana", 0, finalState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, finalState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one, cards in hand", 1, finalState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one, card in hand", "Acolyte of Pain", finalState.getPlayer(PlayerOrdinal.One).getHand().get(0).getName());
		Assert.assertEquals("player one, minions on board", 2, finalState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
	}
}
