package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.StartTurnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Generic implementation of a hero power.
 */
public class HeroPower implements GameEventListener {

	private final TargetFinder targetFinder;

	private boolean isAvailable;

	public HeroPower(TargetFinder targetFinder) {
		this.targetFinder = targetFinder;
	}

	/**
	 * Copy-Constructor
	 */
	public HeroPower(HeroPower other) {
		targetFinder = other.targetFinder;
		isAvailable = other.isAvailable;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean available) {
		isAvailable = available;
	}

	/**
	 * Should return true, when this hero power needs to be applied onto target.
	 */
	public boolean isTargeted() {
		return targetFinder != null;
	}

	/**
	 * If the hero power is applicable to targets this method should identify all of them.
	 */
	public List<Target> getPossibleTargets(HearthstoneGameState state) {
		return (targetFinder != null) ? targetFinder.findPossibleTargets(state) : new ArrayList<>();
	}

	@Override
	public void onEvent(GameEvent event) {
		if (event instanceof StartTurnEvent) {
			// reset availability if it is the heroPower's player turn.
			if (Objects.equals(event.getState().getOwner(this), ((StartTurnEvent)event).getPlayer())) {
				isAvailable = true;
			}
		}
	}
}
