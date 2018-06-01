package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.atomic.DestroyPlayerAtomic;
import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.PlayerTakesDamageEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * Represents the current game state a player can be in.
 */
public class Player extends Character<Player> implements GameEventListener<HearthstoneGameState> {

	private String name;
	private int numberOfTurns = 0;

	private StartingHandState startingHandState;

	private final Stack<Card> library = new Stack<>();
	private final List<Card> hand = new ArrayList<>();
	private final List<Card> playedCards = new ArrayList<>();
	private final List<Minion> battlefield = new ArrayList<>();
	private final List<Minion> graveyard = new ArrayList<>();

	private final List<Secret> secrets = new ArrayList<>();

	private int numberOfManaCrystals;
	private int availableMana;
	private int crystalsLockedNextTurn;

	private HeroPower heroPower;

	private Weapon weapon;

	private int currentFatigueDamage;

	public Player() {}

	/**
	 * Copy-constructor
	 */
	public Player(Player player) {
		super(player);
		name = player.name;
		numberOfTurns = player.numberOfTurns;
		startingHandState = player.startingHandState;
		library.addAll(player.library);
		hand.addAll(player.hand);
		battlefield.addAll(player.battlefield);
		graveyard.addAll(player.graveyard);
		secrets.addAll(player.secrets);
		numberOfManaCrystals = player.numberOfManaCrystals;
		availableMana = player.availableMana;
		crystalsLockedNextTurn = player.crystalsLockedNextTurn;
		heroPower = player.heroPower != null ? new HeroPower(player.heroPower) : null;
		weapon = player.weapon != null ? new Weapon(player.weapon) : null;
		currentFatigueDamage = player.currentFatigueDamage;
	}

	public String getName() {
		return name;
	}

	public Player setName(String name) {
		this.name = name;
		return this;
	}

	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public Player setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
		return this;
	}

	public StartingHandState getStartingHandState() {
		return startingHandState;
	}

	public Player setStartingHandState(StartingHandState startingHandState) {
		this.startingHandState = startingHandState;
		return this;
	}

	public Card removeTopCardFromLibrary() {
		try {
			return library.pop();
		} catch (EmptyStackException e) {
			return null;
		}
	}

	public Player addToLibrary(Collection<Card> cards) {
		library.addAll(cards);
		return this;
	}

	public Player shuffleLibrary() {
		Collections.shuffle(library);
		return this;
	}

	public List<Card> getHand() {
		return hand;
	}

	public List<Card> removeAllFromHand() {
		final List<Card> cards = new ArrayList<>(hand);
		hand.clear();
		return cards;
	}

	public void addToHand(Card card) {
		hand.add(card);
	}

	public List<Minion> getBattlefield() {
		return battlefield;
	}

	public Player addToBattlefield(Minion minion) {
		battlefield.add(minion);
		return this;
	}

	public void removeFromBattlefield(Minion minion) {
		battlefield.remove(minion);
	}

	public List<Minion> getGraveyard() {
		return graveyard;
	}

	public void addToGraveyard(Minion minion) {
		graveyard.add(minion);
	}

	public int getNumberOfManaCrystals() {
		return numberOfManaCrystals;
	}

	public void setNumberOfManaCrystals(int numberOfManaCrystals) {
		this.numberOfManaCrystals = numberOfManaCrystals;
	}

	public int getAvailableMana() {
		return availableMana;
	}

	public Player setAvailableMana(int availableMana) {
		this.availableMana = availableMana;
		return this;
	}

	public int getCrystalsLockedNextTurn() {
		return crystalsLockedNextTurn;
	}

	public void setCrystalsLockedNextTurn(int crystalsLockedNextTurn) {
		this.crystalsLockedNextTurn = crystalsLockedNextTurn;
	}

	public HeroPower getHeroPower() {
		return heroPower;
	}

	public Player setHeroPower(HeroPower heroPower) {
		this.heroPower = heroPower;
		return this;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public Player setWeapon(Weapon weapon) {
		this.weapon = weapon;
		return this;
	}

	public int getCurrentFatigueDamage() {
		return currentFatigueDamage;
	}

	public void setCurrentFatigueDamage(int currentFatigueDamage) {
		this.currentFatigueDamage = currentFatigueDamage;
	}

	@Override
	public int getAttack() {
		return getPower() + (weapon != null && weapon.isActive() ? weapon.getAttack() : 0);
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		HearthstoneGameState nextState = super.onEvent(state, event);
		// forward the event.
		for (Card card : library) {
			nextState = card.onEvent(nextState, event);
		}
		for (Card card : hand) {
			nextState = card.onEvent(nextState, event);
		}
		for (Card card : playedCards) {
			nextState = card.onEvent(nextState, event);
		}
		for (Minion minion : battlefield) {
			nextState = minion.onEvent(nextState, event);
		}
		for (Minion minion : graveyard) {
			nextState = minion.onEvent(nextState, event);
		}
		for (Secret secret : secrets) {
			nextState = secret.onEvent(nextState, event);
		}
		if (heroPower != null) {
			nextState = heroPower.onEvent(nextState, event);
		}
		if (weapon != null) {
			nextState = weapon.onEvent(nextState, event);
		}
		if (event instanceof PlayerTakesDamageEvent) {
			PlayerTakesDamageEvent takesDamageEvent = (PlayerTakesDamageEvent)event;
			if (takesDamageEvent.getCharacter().getTargetRef().equals(getTargetRef())) {
				Player player = (Player)nextState.findTarget(getTargetRef());
				if (player.getCurrentHitPoints() <= 0) {
					return nextState.apply(new DestroyPlayerAtomic(nextState.getPlayerOrdinal(player)));
				}
			}
		}
		return nextState;
	}

	public boolean isOwnerOf(HeroPower heroPower) {
		return Objects.equals(this.heroPower, heroPower);
	}

	public boolean isOwnerOf(Minion minion) {
		return battlefield.contains(minion);
	}

	public boolean isOwnerOf(Effect effect) {
		return heroPower.getEffect() == effect;
	}

	public boolean isOwnerOf(WeaponRef weaponRef) {
		return weapon != null && weapon.getWeaponRef().equals(weaponRef);
	}

	public Minion findTarget(TargetRef targetRef) {
		for (Minion minion : battlefield) {
			if (minion.getTargetRef().equals(targetRef)) {
				return minion;
			}
		}
		return null;
	}

	/**
	 * @return true if this player himself has taunt or has at least on tount minion.
	 */
	public boolean hasTauntsOnBoard() {
		if (hasTaunt()) {
			return true;
		}
		for (Minion minion : getBattlefield()) {
			if (minion.hasTaunt()) {
				return true;
			}
		}
		return false;
	}

}
