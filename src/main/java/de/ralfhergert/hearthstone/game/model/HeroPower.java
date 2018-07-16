package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.effect.TargetedEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.StartTurnEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic implementation of a hero power.
 */
public class HeroPower implements GameEventListener<HearthstoneGameState> {

	private final int manaCost;
	private final Effect effect;

	private boolean isAvailable;

	public HeroPower(int manaCost, Effect effect) {
		this.manaCost = manaCost;
		this.effect = effect;
	}

	/**
	 * Copy-Constructor
	 */
	public HeroPower(HeroPower other) {
		manaCost = other.manaCost;
		effect = other.effect;
		isAvailable = other.isAvailable;
	}

	public int getManaCost() {
		return manaCost;
	}

	public Effect getEffect() {
		return effect;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public HeroPower setAvailable(boolean available) {
		isAvailable = available;
		return this;
	}

	/**
	 * Should return true, when this hero power needs to be applied onto target.
	 */
	public boolean isTargeted() {
		return effect instanceof TargetedEffect;
	}

	/**
	 * If the hero power is applicable to targets this method should identify all of them.
	 */
	public List<TargetRef> getPossibleTargets(HearthstoneGameState state) {
		return (effect instanceof TargetedEffect) ? ((TargetedEffect)effect).getPossibleTargets(state) : new ArrayList<>();
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		if (event instanceof StartTurnEvent) {
			// reset availability if it is the heroPower's player turn.
			if (state.getPlayerOrdinal(state.getOwner(this)) == ((StartTurnEvent)event).getPlayerOrdinal()) {
				isAvailable = true; // FIXME find by reference.
			}
		}
		return state;
	}

	public boolean isApplicableTo(HearthstoneGameState state) {
		return effect != null && effect.isApplicableTo(state);
	}
}
