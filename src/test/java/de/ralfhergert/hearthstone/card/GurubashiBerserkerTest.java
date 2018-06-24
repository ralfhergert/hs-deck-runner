package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayMinionCard;
import de.ralfhergert.hearthstone.action.PlayTargetedHeroPower;
import de.ralfhergert.hearthstone.game.heropower.MageHeroPowerEffect;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.HeroPower;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Gurubashi Berserker' is working correctly.
 */
public class GurubashiBerserkerTest {

	@Test
	public void testPlayGuardianOfKings() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(7)
			.setHeroPower(new HeroPower(2, new MageHeroPowerEffect()).setAvailable(true))
			.addToHand(CardRepository.createByName("Gurubashi Berserker"));
		final Player player2 = new Player()
			.setHitPoints(30);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		actions = ActionUtil.remove(actions, PlayTargetedHeroPower.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayMinionCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 2, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one should one minion on board", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("power of Gurubashi Berserker", 2, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getPower());
		// ping the berserker with the mage hero power.
		actions = new ActionFactory().createAllApplicableActions(afterState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 3, actions.size());
		Action<HearthstoneGameState> heroPowerAction = actions.stream()
			.filter(action -> action instanceof PlayTargetedHeroPower)
			.filter(action -> ((PlayTargetedHeroPower)action).getTargetRef().equals(afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getTargetRef()))
			.findFirst().orElseThrow(AssertionError::new);
		Assert.assertNotNull("found action should not be null", heroPowerAction);
		Assert.assertEquals("found action should by of type", PlayTargetedHeroPower.class, heroPowerAction.getClass());
		HearthstoneGameState finalState = heroPowerAction.apply(afterState);
		Assert.assertNotNull("final state should not be null", finalState);
		Assert.assertEquals("player one should one minion on board", 1, finalState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("power of Gurubashi Berserker", 5, finalState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getPower());
	}
}
