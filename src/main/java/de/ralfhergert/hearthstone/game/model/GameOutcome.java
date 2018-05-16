package de.ralfhergert.hearthstone.game.model;

/**
 * Possible states a game can be in.
 */
public enum GameOutcome {
	Undecided(false),
	Draw(true),
	Player1Wins(true),
	Player2Wins(true);

	private final boolean isFinal;

	GameOutcome(boolean isFinal) {
		this.isFinal = isFinal;
	}

	/**
	 * A final game state means this game is over.
	 */
	public boolean isFinal() {
		return isFinal;
	}
}
