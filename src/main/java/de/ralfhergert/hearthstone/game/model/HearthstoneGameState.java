package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.generic.game.model.Action;
import de.ralfhergert.generic.game.model.GameState;
import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Holds the current state of a game of Hearthstone.
 */
public class HearthstoneGameState extends GameState<HearthstoneGameState> implements GameEventListener<HearthstoneGameState> {

	private Player[] players = new Player[2];

	private Turn turn = Turn.DrawStartingHand;
	private GameOutcome outcome = GameOutcome.Undecided;

	private Random random;

	/**
	 * These two references will be set while an attack is going on.
	 * They enable effects to alter the target of an attack.
	 */
	private TargetRef currentAttackerRef;
	private TargetRef currentTargetRef;

	public HearthstoneGameState(HearthstoneGameState parent, Action<HearthstoneGameState> action) {
		super(parent, action);
		if (parent != null) {
			players[0] = new Player(parent.players[0]);
			players[1] = new Player(parent.players[1]);
			turn = parent.turn;
			outcome = parent.outcome;
			random = parent.random;
		} else {
			random = new Random();
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

	public HearthstoneGameState onEvent(GameEvent event) {
		return onEvent(this, event);
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		HearthstoneGameState nextState = players[0].onEvent(state, event);
		return players[1].onEvent(nextState, event);
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

	public Player getOwner(WeaponRef weaponRef) {
		for (Player player : players) {
			if (player.isOwnerOf(weaponRef)) {
				return player;
			}
		}
		return null;
	}

	public Character getEffectOwner(Effect effect) {
		for (Player player : players) {
			if (player.isEffectedBy(effect)) {
				return player;
			}
			for (Minion minion : player.getBattlefield()) {
				if (minion.isEffectedBy(effect)) {
					return minion;
				}
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

	public List<Character> getAllCharacters() {
		final List<Character> characters = new ArrayList<>();
		for (Player player : players) {
			characters.add(player);
			characters.addAll(player.getBattlefield());
		}
		return characters;
	}

	public List<Character> findAllEffectedCharacters(final Effect effect) {
		final List<Character> effectedCharacters = new ArrayList<>();
		for (Player player : players) {
			if (player.isEffectedBy(effect)) {
				effectedCharacters.add(player);
			}
			effectedCharacters.addAll(player.getBattlefield().stream()
				.filter(minion -> minion.isEffectedBy(effect))
				.collect(Collectors.toList()));
		}
		return effectedCharacters;
	}

	public Random getRandom() {
		return random;
	}

	/**
	 * Used for testing purposes to pass in a rigged random.
	 */
	public HearthstoneGameState setRandom(Random random) {
		this.random = random;
		return this;
	}
}
