package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Represents the current game state a player can be in.
 */
public class Player implements GameEventListener {

	private String name;

	private StartingHandState startingHandState;

	private final Stack<Card> library = new Stack<>();
	private final List<Card> hand = new ArrayList<>();
	private final List<Minion> battlefield = new ArrayList<>();
	private final List<Card> graveyard = new ArrayList<>();

	private final List<Secret> secrets = new ArrayList<>();

	private int numberOfManaCrystals;
	private int availableMana;
	private int crystalsLockedNextTurn;

	private int hitPoints;
	private int power;
	private int armor;

	private boolean heroPowerAvailable;

	private boolean isImmune;
	private boolean isFrozen;

	private Weapon weapon;

	private int currentFatigueDamage;

	public Player() {}

	/**
	 * Copy-constructor
	 */
	public Player(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("player can not be null");
		}
		name = player.name;
		startingHandState = player.startingHandState;
		library.addAll(player.library);
		hand.addAll(player.hand);
		battlefield.addAll(player.battlefield);
		graveyard.addAll(player.graveyard);
		secrets.addAll(player.secrets);
		numberOfManaCrystals = player.numberOfManaCrystals;
		availableMana = player.availableMana;
		crystalsLockedNextTurn = player.crystalsLockedNextTurn;
		hitPoints = player.hitPoints;
		power = player.power;
		armor = player.armor;
		heroPowerAvailable = player.heroPowerAvailable;
		isImmune = player.isImmune;
		isFrozen = player.isFrozen;
		weapon = new Weapon(player.weapon);
		currentFatigueDamage = player.currentFatigueDamage;
	}

	public String getName() {
		return name;
	}

	public Player setName(String name) {
		this.name = name;
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

	public int getNumberOfManaCrystals() {
		return numberOfManaCrystals;
	}

	public void setNumberOfManaCrystals(int numberOfManaCrystals) {
		this.numberOfManaCrystals = numberOfManaCrystals;
	}

	public int getAvailableMana() {
		return availableMana;
	}

	public void setAvailableMana(int availableMana) {
		this.availableMana = availableMana;
	}

	public int getCrystalsLockedNextTurn() {
		return crystalsLockedNextTurn;
	}

	public void setCrystalsLockedNextTurn(int crystalsLockedNextTurn) {
		this.crystalsLockedNextTurn = crystalsLockedNextTurn;
	}

	public boolean isHeroPowerAvailable() {
		return heroPowerAvailable;
	}

	public void setHeroPowerAvailable(boolean heroPowerAvailable) {
		this.heroPowerAvailable = heroPowerAvailable;
	}

	public int getCurrentFatigueDamage() {
		return currentFatigueDamage;
	}

	public void setCurrentFatigueDamage(int currentFatigueDamage) {
		this.currentFatigueDamage = currentFatigueDamage;
	}

	/**
	 * @return true is the dealt damage was lethal.
	 */
	public boolean dealDamage(final int damage) {
		if (!isImmune) {
			// deal the damage to the armor first.
			armor -= damage;
			if (armor < 0) { // if the armor could not absorb the damage.
				hitPoints -= armor;
				armor = 0;
			}
		}
		return hitPoints <= 0;
	}

	@Override
	public void onEvent(GameEvent event) {
		// forward the event.
		library.forEach(card -> card.onEvent(event));
		hand.forEach(card -> card.onEvent(event));
		battlefield.forEach(minion -> minion.onEvent(event));
		graveyard.forEach(card -> card.onEvent(event));
		secrets.forEach(secret -> secret.onEvent(event));
		weapon.onEvent(event);
	}
}
