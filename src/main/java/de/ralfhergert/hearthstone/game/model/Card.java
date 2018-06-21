package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.action.ActionDiscovery;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;

/**
 * Represents a card in library, hand or graveyard.
 * @param <Self> defines the return type of the fluent methods.
 */
public class Card<Self extends Card> implements GameEventListener<HearthstoneGameState> {

	private CardRef cardRef;

	private int id;
	private int manaCost;
	private int overloadCost;
	private String name;

	private ActionDiscovery<Self> actionDiscovery;

	public Card() {
		cardRef = new CardRef();
	}

	/**
	 * Copy constructor.
	 */
	public Card(Card other) {
		if (other == null) {
			throw new IllegalArgumentException("other card must not be null");
		}
		cardRef = other.cardRef;
		id = other.id;
		manaCost = other.manaCost;
		overloadCost = other.overloadCost;
		name = other.name;
		actionDiscovery = other.actionDiscovery;
	}

	public CardRef getCardRef() {
		return cardRef;
	}

	public void setCardRef(CardRef cardRef) {
		this.cardRef = cardRef;
	}

	public int getId() {
		return id;
	}

	public Self setId(int id) {
		this.id = id;
		return (Self)this;
	}

	public int getManaCost() {
		return manaCost;
	}

	public Self setManaCost(int manaCost) {
		this.manaCost = manaCost;
		return (Self)this;
	}

	public int getOverloadCost() {
		return overloadCost;
	}

	public Self setOverloadCost(int overloadCost) {
		this.overloadCost = overloadCost;
		return (Self)this;
	}

	public String getName() {
		return name;
	}

	public Self setName(String name) {
		this.name = name;
		return (Self)this;
	}

	public ActionDiscovery<Self> getActionDiscovery() {
		return actionDiscovery;
	}

	public Self setActionDiscovery(ActionDiscovery<Self> actionDiscovery) {
		this.actionDiscovery = actionDiscovery;
		return (Self)this;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		return state;
	}
}
