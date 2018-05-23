package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;

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
public class Player extends Character<Player> implements Target,GameEventListener {

	private String name;

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

	public List<Minion> getBattlefield() {
		return battlefield;
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

	public int getCurrentFatigueDamage() {
		return currentFatigueDamage;
	}

	public void setCurrentFatigueDamage(int currentFatigueDamage) {
		this.currentFatigueDamage = currentFatigueDamage;
	}


	@Override
	public void onEvent(GameEvent event) {
		// forward the event.
		library.forEach(card -> card.onEvent(event));
		hand.forEach(card -> card.onEvent(event));
		playedCards.forEach(card -> card.onEvent(event));
		battlefield.forEach(minion -> minion.onEvent(event));
		graveyard.forEach(minion -> minion.onEvent(event));
		secrets.forEach(secret -> secret.onEvent(event));
		if (heroPower != null) {
			heroPower.onEvent(event);
		}
		weapon.onEvent(event);
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

	public void removeFromBattlefield(Minion minion) {
		battlefield.remove(minion);
	}

	public void addToGraveyard(Minion minion) {
		graveyard.add(minion);
	}
}
