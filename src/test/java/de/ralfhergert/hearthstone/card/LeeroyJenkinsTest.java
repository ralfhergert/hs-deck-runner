package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.CharacterAttacksAction;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayMinionCard;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This test checks that 'Leeroy Jenkins' is working correctly.
 */
public class LeeroyJenkinsTest {

	@Test
	public void testPlayLeeroyJenkins() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(5)
			.addToHand(CardRepository.createByName("Leeroy Jenkins"));
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
		Assert.assertEquals("found action should by of type", PlayMinionCard.class, foundAction.getClass());
		// play the minion card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("active player should have less mana", 0, afterState.getActivePlayer().getAvailableMana());
		Assert.assertEquals("active player should have no overload", 0, afterState.getActivePlayer().getCrystalsLockedNextTurn());
		Assert.assertEquals("active player should have no card in hand", 0, afterState.getActivePlayer().getHand().size());
		Assert.assertEquals("player one, minions on board", 1, afterState.getActivePlayer().getBattlefield().size());
		Assert.assertEquals("player one, minion, name", "Leeroy Jenkins", afterState.getActivePlayer().getBattlefield().get(0).getName());
		Assert.assertEquals("player two, minions on board", 2, afterState.getPassivePlayer().getBattlefield().size());
		Assert.assertEquals("player two, minion one, name", "Whelp", afterState.getPassivePlayer().getBattlefield().get(0).getName());
		Assert.assertEquals("player two, minion two, name", "Whelp", afterState.getPassivePlayer().getBattlefield().get(1).getName());
		TargetRef leeroyRef = afterState.getActivePlayer().getBattlefield().get(0).getTargetRef();
		// confirm that Leeroy Jenkins can charge.
		List<Action<HearthstoneGameState>> attackActions = new ActionFactory().createAllApplicableActions(afterState).stream()
			.filter(action -> action instanceof CharacterAttacksAction && ((CharacterAttacksAction)action).getAttackerRef().equals(leeroyRef))
			.collect(Collectors.toList());
		Assert.assertEquals("Three attack actions with Leeroy Jenkins should have been found", 3, attackActions.size());
	}
}
