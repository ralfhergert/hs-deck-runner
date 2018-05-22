package de.ralfhergert.hearthstone.game.model;

/**
 * A character is either a minion or a player.
 */
public interface Character {

	int getHitPoints();

	/**
	 * Decreases the current hit point by the given amount of damage.
	 * @return the new hit points of this character.
	 */
	int takeDamage(final int damage);
}
