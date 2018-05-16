package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.generic.game.model.GameState;

/**
 * Holds the current state of a game of Hearthstone.
 */
public class HearthstoneGameState extends GameState<HearthstoneGameState> {

	private Player[] players = new Player[2];

	private Turn turn;

	private GameOutcome outcome;

	public HearthstoneGameState(HearthstoneGameState parent, Action<HearthstoneGameState> action) {
		super(parent, action);
		if (parent != null) {
			players[0] = new Player(parent.players[0]);
			players[1] = new Player(parent.players[1]);
			turn = parent.turn;
			outcome = parent.outcome;
		}
	}

	public Player[] getPlayers() {
		return players;
	}

	public Player getPlayer(PlayerOrdinal ordinal) {
		return players[ordinal.ordinal()];
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public Turn getTurn() {
		return turn;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public GameOutcome getOutcome() {
		return outcome;
	}

	public void setOutcome(GameOutcome outcome) {
		this.outcome = outcome;
	}

	public void setPlayerAsLoser(PlayerOrdinal playerOrdinal) {
		if (outcome == GameOutcome.Undecided) {
			outcome = playerOrdinal == PlayerOrdinal.One ? GameOutcome.Player2Wins : GameOutcome.Player1Wins;
		} else if (outcome == GameOutcome.Player1Wins && playerOrdinal == PlayerOrdinal.One) {
			outcome = GameOutcome.Draw;
		} else if (outcome == GameOutcome.Player2Wins && playerOrdinal == PlayerOrdinal.Two) {
			outcome = GameOutcome.Draw;
		}
	}
}
