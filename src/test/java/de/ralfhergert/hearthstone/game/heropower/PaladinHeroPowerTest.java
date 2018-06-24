package de.ralfhergert.hearthstone.game.heropower;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.action.ActionFactory;
import de.ralfhergert.hearthstone.action.ActionUtil;
import de.ralfhergert.hearthstone.action.EndTurnAction;
import de.ralfhergert.hearthstone.action.PlayHeroPower;
import de.ralfhergert.hearthstone.game.model.GameOutcome;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.HeroPower;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.MinionType;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that the {@link PaladinHeroPowerEffect} works correctly.
 */
public class PaladinHeroPowerTest {

	/**
	 * This test creates a game state in which the only playable action for player one
	 * is to use the hero power, using it create a minion one board.
	 */
	@Test
	public void testPlayerCreatesSilverHandRecruit() {
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new PaladinHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		// remaining action should be to play the hero power.
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", PlayHeroPower.class, foundAction.getClass());
		// play the hero power.
		HearthstoneGameState afterState = foundAction.apply(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("player one should have one minion on the battlefield", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		Minion minion = afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0);
		Assert.assertNotNull("minion should not be null", minion);
		Assert.assertEquals("minion should be of type", MinionType.SilverHandRecruit, minion.getMinionType());
		Assert.assertEquals("minion should have power", 1, minion.getPower());
		Assert.assertEquals("minion should have currentHitPoints", 1, minion.getCurrentHitPoints());
		Assert.assertEquals("minion should have maxHitPoints", 1, minion.getMaxHitPoints());
		Assert.assertEquals("game is still undecided", GameOutcome.Undecided, afterState.getOutcome());
	}

	/**
	 * This test creates a game state in which the player one'S battlefield is flooded
	 * with seven 0/1 minion. This renders the paladin hero power not being usable.
	 */
	@Test
	public void testPaladinHeroPowerCanNotBeUsedOnFullBoard() {
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new PaladinHeroPowerEffect()).setAvailable(true))
			.addToBattlefield(new Minion().setPower(0).setCurrentHitPoints(1))
			.addToBattlefield(new Minion().setPower(0).setCurrentHitPoints(1))
			.addToBattlefield(new Minion().setPower(0).setCurrentHitPoints(1))
			.addToBattlefield(new Minion().setPower(0).setCurrentHitPoints(1))
			.addToBattlefield(new Minion().setPower(0).setCurrentHitPoints(1))
			.addToBattlefield(new Minion().setPower(0).setCurrentHitPoints(1))
			.addToBattlefield(new Minion().setPower(0).setCurrentHitPoints(1));
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 0, actions.size());
	}
}
