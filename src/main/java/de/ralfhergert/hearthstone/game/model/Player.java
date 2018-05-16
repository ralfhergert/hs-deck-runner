package de.ralfhergert.hearthstone.game.model;

import java.util.List;
import java.util.Stack;

/**
 * Represents the current game state a player can be in.
 */
public class Player {

	private String name;

	private Stack<Card> library;
	private List<Card> hand;
	private List<Minion> battlefield;
	private List<Card> graveyard;

	private List<Secret> secrets;

	private int numberOfManaCrystals;
	private int availableMana;
	private int crystalsLockedNextTurn;

	private int hitPoints;
	private int power;
	private int armor;

	private boolean heroPowerUsedThisTurn;
	private boolean tookMulligan;

	private boolean isImmune;
	private boolean isFrozen;

	private Weapon weapon;

	private int currentFatigueDamage;
}
