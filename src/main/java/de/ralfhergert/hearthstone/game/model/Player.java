package de.ralfhergert.hearthstone.game.model;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Represents the current game state a player can be in.
 */
public class Player {

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

	public Card removeTopCardFromLibrary() {
		try {
			return library.pop();
		} catch (EmptyStackException e) {
			return null;
		}
	}

	public List<Card> getHand() {
		return hand;
	}

	public void addToHand(Card card) {
		hand.add(card);
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
}
