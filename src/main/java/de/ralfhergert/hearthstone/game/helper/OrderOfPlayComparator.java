package de.ralfhergert.hearthstone.game.helper;

import de.ralfhergert.hearthstone.game.model.Character;

import java.util.Comparator;

/**
 * This comparator uses the targetRef to sort the minions to their play order.
 */
public class OrderOfPlayComparator implements Comparator<Character> {

	@Override
	public int compare(Character o1, Character o2) {
		return (int)(o1.getTargetRef().getId() - o2.getTargetRef().getId());
	}
}
