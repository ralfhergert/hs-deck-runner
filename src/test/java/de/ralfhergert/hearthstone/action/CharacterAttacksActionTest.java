package de.ralfhergert.hearthstone.action;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.game.model.GameOutcome;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.TargetRef;
import de.ralfhergert.hearthstone.game.model.Turn;
import de.ralfhergert.hearthstone.game.model.Weapon;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that {@link CharacterAttacksAction} is working correctly.
 */
public class CharacterAttacksActionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testCharacterAttacksActionRejectsNullAttacker() {
		new CharacterAttacksAction(null, new TargetRef());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCharacterAttacksActionRejectsNullTarget() {
		new CharacterAttacksAction(new TargetRef(), null);
	}

	@Test
	public void testPlayerAttacksPlayer() {
		// both players get a 1/1 weapon and 1 hitPoint.
		final Player player1 = new Player()
			.setCurrentHitPoints(1)
			.setWeapon(new Weapon().setAttack(1).setDurability(1).setActive(true));
		final Player player2 = new Player()
			.setCurrentHitPoints(1)
			.setWeapon(new Weapon().setAttack(1).setDurability(1).setActive(false));
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", CharacterAttacksAction.class, foundAction.getClass());
		// play the attack.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("numberOfAttacks player one made", 1, afterState.getPlayer(PlayerOrdinal.One).getNumberOfAttacksThisTurn());
		Assert.assertEquals("after state gameOutcome should be", GameOutcome.Player1Wins, afterState.getOutcome());
		Assert.assertNull("player one's weapon should be destroyed", afterState.getPlayer(PlayerOrdinal.One).getWeapon());
	}

	@Test
	public void testPlayerSuicidesIntoMinion() {
		// player one gets a 1/2 weapon and 1 hitPoint.
		final Player player1 = new Player()
			.setCurrentHitPoints(1)
			.setWeapon(new Weapon().setAttack(1).setDurability(2).setActive(true));
		// player two gets a 1/1 taunt minion.
		final Player player2 = new Player()
			.setCurrentHitPoints(1)
			.addToBattlefield(new Minion().setPower(1).setCurrentHitPoints(1).setHasTaunt(true));
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", CharacterAttacksAction.class, foundAction.getClass());
		// play the attack.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("numberOfAttacks player one made", 1, afterState.getPlayer(PlayerOrdinal.One).getNumberOfAttacksThisTurn());
		Assert.assertNotNull("player one's weapon should exist", afterState.getPlayer(PlayerOrdinal.One).getWeapon());
		Assert.assertEquals("player one's weapon should have durability", 1, afterState.getPlayer(PlayerOrdinal.One).getWeapon().getDurability());
		Assert.assertEquals("after state gameOutcome should be", GameOutcome.Player2Wins, afterState.getOutcome());
		Assert.assertEquals("player two's minions on battlefield", 0, afterState.getPlayer(PlayerOrdinal.Two).getBattlefield().size());
	}

	@Test
	public void testMinionAttacksPlayerForLethal() {
		final Player player1 = new Player()
			.addToBattlefield(new Minion().setPower(3));
		// player two gets a 1/1 taunt minion.
		final Player player2 = new Player()
			.setCurrentHitPoints(3);
		final HearthstoneGameState startState = new HearthstoneGameState(null, null).setPlayers(player1, player2);
		startState.setTurn(Turn.Player1Turn);
		// ask the action factory for all possible plays.
		List<Action<HearthstoneGameState>> actions = new ActionFactory().createAllApplicableActions(startState);
		actions = ActionUtil.remove(actions, EndTurnAction.class);
		Assert.assertNotNull("actions should not be null", actions);
		Assert.assertEquals("number of found actions", 1, actions.size());
		Action<HearthstoneGameState> foundAction = actions.get(0);
		Assert.assertNotNull("found action should not be null", foundAction);
		Assert.assertEquals("found action should by of type", CharacterAttacksAction.class, foundAction.getClass());
		// play the attack.
		HearthstoneGameState afterState = foundAction.applyTo(startState);
		Assert.assertNotNull("after state should not be null", afterState);
		Assert.assertEquals("numberOfAttacks minion made", 1, afterState.getPlayer(PlayerOrdinal.One).getBattlefield().get(0).getNumberOfAttacksThisTurn());
		Assert.assertEquals("after state gameOutcome should be", GameOutcome.Player1Wins, afterState.getOutcome());
	}
}
