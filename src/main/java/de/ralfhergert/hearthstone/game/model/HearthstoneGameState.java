package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.generic.game.model.GameState;
import de.ralfhergert.hearthstone.effect.Effect;
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

	private Turn turn = Turn.DrawStartingHand;
	private GameOutcome outcome = GameOutcome.Undecided;

	/**
	 * These two references will be set while an attack is going on.
	 * They enable effects to alter the target of an attack.
	 */
	private TargetRef currentAttackerRef;
	private TargetRef currentTargetRef;

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

	public Player getOpposingPlayer(Player currentPlayer) {
		for (Player player : players) {
			if (player != currentPlayer) {
				return player;
			}
		}
		return null;
	}

	public PlayerOrdinal getPlayerOrdinal(Player player) {
		for (PlayerOrdinal ordinal : PlayerOrdinal.values()) {
			if (players[ordinal.ordinal()] == player) {
				return ordinal;
			}
		}
		return null;
	}

	public HearthstoneGameState setPlayers(Player... players) {
		this.players = players;
		return this;
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

	public TargetRef getCurrentAttackerRef() {
		return currentAttackerRef;
	}

	public HearthstoneGameState setCurrentAttackerRef(TargetRef currentAttackerRef) {
		this.currentAttackerRef = currentAttackerRef;
		return this;
	}

	public TargetRef getCurrentTargetRef() {
		return currentTargetRef;
	}

	public HearthstoneGameState setCurrentTargetRef(TargetRef currentTargetRef) {
		this.currentTargetRef = currentTargetRef;
		return this;
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

	public Player getOwner(Effect effect) {
		for (Player player : players) {
			if (player.isOwnerOf(effect)) {
				return player;
			}
		}
		return null;
	}

	public PlayerOrdinal findOwnerOrdinal(TargetRef targetRef) {
		for (PlayerOrdinal playerOrdinal : PlayerOrdinal.values()) {
			final Player player = getPlayer(playerOrdinal);
			if (getPlayer(playerOrdinal).getTargetRef().equals(targetRef)) {
				return playerOrdinal;
			}
			final Target target = player.findTarget(targetRef);
			if (target != null) {
				return playerOrdinal;
			}
		}
		return null;
	}

	public Character findTarget(TargetRef targetRef) {
		for (Player player : players) {
			if (player.getTargetRef().equals(targetRef)) {
				return player;
			}
			final Minion target = player.findTarget(targetRef);
			if (target != null) {
				return target;
			}
		}
		return null;
	}
}
