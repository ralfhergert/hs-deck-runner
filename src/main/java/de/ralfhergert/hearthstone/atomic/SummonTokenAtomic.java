package de.ralfhergert.hearthstone.atomic;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.hearthstone.card.CardRepository;
import de.ralfhergert.hearthstone.event.MinionEntersBattlefieldEvent;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;

/**
 * This atomic will summon a token named {@link #tokenName} for the player with {@link #playerOrdinal}.
 */
public class SummonTokenAtomic implements Action<HearthstoneGameState> {

	private final PlayerOrdinal playerOrdinal;
	private final String tokenName;

	public SummonTokenAtomic(PlayerOrdinal playerOrdinal, String tokenName) {
		if (playerOrdinal == null) {
			throw new IllegalArgumentException("playerOrdinal must not be null");
		}
		if (tokenName == null) {
			throw new IllegalArgumentException("tokenName must be null");
		}
		this.playerOrdinal = playerOrdinal;
		this.tokenName = tokenName;
	}

	@Override
	public HearthstoneGameState apply(HearthstoneGameState state) {
		HearthstoneGameState nextState = new HearthstoneGameState(state, this);
		Player player = nextState.getPlayer(playerOrdinal);
		if (player.getBattlefield().size() < 7) {
			Minion token = CardRepository.createTokenByName(tokenName);
			player.addToBattlefield(token);
			nextState = nextState.onEvent(new MinionEntersBattlefieldEvent(token.getMinionRef()));
		}
		return nextState;
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return false;
	}
}
