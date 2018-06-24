package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.event.PlayerTakesDamageEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This atomic will damage a playerOrdinal.
 */
public class DamagePlayerAtomic implements Action<HearthstoneGameState> {

	private final PlayerOrdinal playerOrdinal;
	private final int damage;

	public DamagePlayerAtomic(PlayerOrdinal playerOrdinal, int damage) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal can not be null");
		}
		if (damage < 0) {
			throw new IllegalArgumentException("damage can not be smaller than 0");
		}
		this.playerOrdinal = playerOrdinal;
		this.damage = damage;
	}

	@Override
	public HearthstoneGameState apply(HearthstoneGameState state) {
		if (damage < 1) {
			return state;
		}
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		Player player = nextState.getPlayer(playerOrdinal);
		final int hitPointsBefore = player.getCurrentHitPoints();
		final int hitPointsAfter = player.takeDamage(damage);
		if (hitPointsBefore > hitPointsAfter) {
			return nextState.onEvent(new PlayerTakesDamageEvent(player, hitPointsBefore));
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
