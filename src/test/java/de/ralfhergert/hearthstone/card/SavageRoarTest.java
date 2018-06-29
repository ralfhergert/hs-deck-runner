package de.ralfhergert.hearthstone.card;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayAbilityCard;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test checks that 'Savage Roar' is working correctly.
 */
public class SavageRoarTest {

	@Test
	public void testPlaySavageRoar() {
		final Player player1 = new Player()
			.setHitPoints(30)
			.setAvailableMana(3)
			.addToHand(CardRepository.createByName("Savage Roar"))
			.addToBattlefield(new Minion().setHitPoints(3))
			.addToBattlefield(new Minion().setHitPoints(3));
		final Player player2 = new Player()
			.setHitPoints(30)
			.addToBattlefield(new Minion().setHitPoints(3));
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
		// play the minion card.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have less mana", 0, afterState.getPlayer(PlayerOrdinal.One).getAvailableMana());
		Assert.assertEquals("player one should have no overload", 0, afterState.getPlayer(PlayerOrdinal.One).getCrystalsLockedNextTurn());
		Assert.assertEquals("player one should have no card in hand", 0, afterState.getPlayer(PlayerOrdinal.One).getHand().size());
		Assert.assertEquals("player one should one minion on board", 2, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Assert.assertEquals("player two, attack", 0, afterState.getPlayer(PlayerOrdinal.Two).getAttack());
		for (Character character : afterState.getPlayer(PlayerOrdinal.One).getAllCharactersInOrderOfPlay()) {
			Assert.assertEquals("each player one character has attack", 2, character.getAttack());
		}
		// end the turn
		Action<HearthstoneGameState> endTurnAction = new ActionFactory().createAllApplicableActions(afterState).stream()
			.filter(action -> action instanceof EndTurnAction)
			.findFirst()
			.orElseThrow(() -> new AssertionError("no EndTurnAction found"));
		HearthstoneGameState finalState = endTurnAction.apply(afterState);
		for (Character character : finalState.getPlayer(PlayerOrdinal.One).getAllCharactersInOrderOfPlay()) {
			Assert.assertEquals("each player one character has attack", 0, character.getAttack());
		}
	}
}
