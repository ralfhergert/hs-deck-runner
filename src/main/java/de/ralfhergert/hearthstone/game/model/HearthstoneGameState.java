package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.generic.game.model.GameState;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the current state of a game of Hearthstone.
 */
public class HearthstoneGameState extends GameState<HearthstoneGameState> implements GameEventListener {

	private Player[] players = new Player[2];

	private Turn turn;

	private GameOutcome outcome;

	private List<GeneralEffect> queuedEffects = new ArrayList<>();

	public HearthstoneGameState(HearthstoneGameState parent, Action<HearthstoneGameState> action) {
		super(parent, action);
		if (parent != null) {
			players[0] = new Player(parent.players[0]);
			players[1] = new Player(parent.players[1]);
			turn = parent.turn;
			outcome = parent.outcome;
			queuedEffects.addAll(parent.queuedEffects);
		}
	}

	public Player[] getPlayers() {
		return players;
	}

	public Player getPlayer(PlayerOrdinal ordinal) {
		return players[ordinal.ordinal()];
	}

	/**
	 * @return the player who is on turn currently.
	 */
	public Player getActivePlayer() {
		switch (turn) {
			case Player1Turn: return players[0];
			case Player2Turn: return players[1];
			default: return null;
		}
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

	@Override
	public void onEvent(GameEvent event) {
		players[0].onEvent(event);
		players[1].onEvent(event);
	}

	public List<GeneralEffect> getQueuedEffects() {
		return queuedEffects;
	}

	public void clearQueuedEffects() {
		queuedEffects.clear();
	}

	public Player getOwner(HeroPower heroPower) {
		for (Player player : players) {
			if (player.isOwnerOf(heroPower)) {
				return player;
			}
		}
		return null;
	}

	public Player getOwner(Minion minion) {
		for (Player player : players) {
			if (player.isOwnerOf(minion)) {
				return player;
			}
		}
		return null;
	}
}
