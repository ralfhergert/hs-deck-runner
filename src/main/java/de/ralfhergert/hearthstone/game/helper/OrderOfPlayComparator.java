package de.ralfhergert.hearthstone.game.helper;

import de.ralfhergert.hearthstone.game.model.Minion;

import java.util.Comparator;

/**
 * This comparator uses the targetRef to sort the minions to their play order.
 */
public class OrderOfPlayComparator implements Comparator<Minion> {

	@Override
	public int compare(Minion o1, Minion o2) {
		return (int)(o1.getTargetRef().getId() - o2.getTargetRef().getId());
	}
}
