package de.ralfhergert.hearthstone.game.model;

/**
 * Represents the turn a game can be in.
 */
public enum Turn {
	DrawStartingHand(null),
	Player1Turn(PlayerOrdinal.One),
	Player2Turn(PlayerOrdinal.Two);

	private final PlayerOrdinal playerOrdinal;

	Turn(PlayerOrdinal playerOrdinal) {
		this.playerOrdinal = playerOrdinal;
	}

	public PlayerOrdinal getPlayerOrdinal() {
		return playerOrdinal;
	}
}
