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
import java.util.Random;

/**
 * This test ensures that the {@link ShamanHeroPowerEffect} works correctly.
 */
public class ShamanHeroPowerTest {

	/**
	 * This test creates a game state in which the only playable action for player one
	 * is to use the hero power, using it create a totem one board.
	 */
	@Test
	public void testPlayerCreatesHealingTotem() {
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new ShamanHeroPowerEffect()).setAvailable(true))
			.addToBattlefield(new Minion().setCurrentHitPoints(1).setMaxHitPoints(2)); // add a injured minion to the board
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// modify the game state to use a rigged random, which always delivers 0.
		startState.setRandom(new Random() {
			@Override
			public int nextInt(int bound) {
				return 0;
			}
		});
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
		Assert.assertEquals("player one should have one minion on the battlefield", 2, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().size());
		{
			Minion minion = afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(1);
			Assert.assertNotNull("minion should not be null", minion);
			Assert.assertEquals("minion should be of name", "Healing Totem", minion.getName());
			Assert.assertEquals("minion should be of type", MinionType.Totem, minion.getMinionType());
			Assert.assertEquals("minion should have power", 0, minion.getPower());
			Assert.assertEquals("minion should have currentHitPoints", 2, minion.getCurrentHitPoints());
			Assert.assertEquals("minion should have maxHitPoints", 2, minion.getMaxHitPoints());
			Assert.assertEquals("game is still undecided", GameOutcome.Undecided, afterState.getOutcome());
		}
		// let the player end the turn.
		actions = new ActionFactory().createAllApplicableActions(afterState);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", EndTurnAction.class, foundAction.getClass());
		// end the turn.
		HearthstoneGameState finalState = foundAction.apply(afterState);
		// all minions of player 1 should be healed.
		for (Minion minion : finalState.getPlayer(PlayerOrdinal.One).getBattlefield()) {
			Assert.assertEquals("minion should have full health", minion.getMaxHitPoints(), minion.getCurrentHitPoints());
		}
	}

	/**
	 * This test creates a game state in which the only playable action for player one
	 * is to use the hero power, using it create a totem one board.
	 */
	@Test
	public void testPlayerCreatesSearingTotem() {
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new ShamanHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// modify the game state to use a rigged random, which alway delivers 0.
		startState.setRandom(new Random() {
			@Override
			public int nextInt(int bound) {
				return 1;
			}
		});
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
		Assert.assertEquals("minion should be of name", "Searing Totem", minion.getName());
		Assert.assertEquals("minion should be of type", MinionType.Totem, minion.getMinionType());
		Assert.assertEquals("minion should have power", 1, minion.getPower());
		Assert.assertEquals("minion should have currentHitPoints", 1, minion.getCurrentHitPoints());
		Assert.assertEquals("minion should have maxHitPoints", 1, minion.getMaxHitPoints());
		Assert.assertEquals("game is still undecided", GameOutcome.Undecided, afterState.getOutcome());
	}

	/**
	 * This test creates a game state in which the only playable action for player one
	 * is to use the hero power, using it create a totem one board.
	 */
	@Test
	public void testPlayerCreatesStoneClawTotem() {
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new ShamanHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// modify the game state to use a rigged random, which alway delivers 0.
		startState.setRandom(new Random() {
			@Override
			public int nextInt(int bound) {
				return 2;
			}
		});
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
		Assert.assertEquals("minion should be of name", "Stoneclaw Totem", minion.getName());
		Assert.assertEquals("minion should be of type", MinionType.Totem, minion.getMinionType());
		Assert.assertEquals("minion should have power", 0, minion.getPower());
		Assert.assertEquals("minion should have currentHitPoints", 2, minion.getCurrentHitPoints());
		Assert.assertEquals("minion should have maxHitPoints", 2, minion.getMaxHitPoints());
		Assert.assertEquals("minion should have taunt", true, minion.hasTaunt());
		Assert.assertEquals("game is still undecided", GameOutcome.Undecided, afterState.getOutcome());
	}

	/**
	 * This test creates a game state in which the only playable action for player one
	 * is to use the hero power, using it create a totem one board.
	 */
	@Test
	public void testPlayerCreatesWrathOfAirTotem() {
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new ShamanHeroPowerEffect()).setAvailable(true));
		final Player player2 = new Player();
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// modify the game state to use a rigged random, which alway delivers 0.
		startState.setRandom(new Random() {
			@Override
			public int nextInt(int bound) {
				return 3;
			}
		});
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
		Assert.assertEquals("minion should be of name", "Wrath of Air Totem", minion.getName());
		Assert.assertEquals("minion should be of type", MinionType.Totem, minion.getMinionType());
		Assert.assertEquals("minion should have power", 0, minion.getPower());
		Assert.assertEquals("minion should have currentHitPoints", 2, minion.getCurrentHitPoints());
		Assert.assertEquals("minion should have maxHitPoints", 2, minion.getMaxHitPoints());
		Assert.assertEquals("game is still undecided", GameOutcome.Undecided, afterState.getOutcome());
	}

	/**
	 * This test creates a game state in which the player one'S battlefield is flooded
	 * with seven 0/1 minion. This renders the paladin hero power not being usable.
	 */
	@Test
	public void testShamanHeroPowerCanNotBeUsedOnFullBoard() {
		final Player player1 = new Player()
			.setAvailableMana(2)
			.setHeroPower(new HeroPower(2, new ShamanHeroPowerEffect()).setAvailable(true))
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
