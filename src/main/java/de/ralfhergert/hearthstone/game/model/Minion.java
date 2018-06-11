package de.ralfhergert.hearthstone.game.model;

import de.ralfhergert.hearthstone.atomic.DestroyMinionAtomic;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.MinionTakesDamageEvent;
import de.ralfhergert.hearthstone.game.minion.MinionFactory;

/**
 * Represents a minion on the battlefield.
 */
public class Minion extends Character<Minion> implements GameEventListener<HearthstoneGameState> {

	/* The card this minion was summoned from. Can be null, if the minion was created
	 * by a hero power or other effect. */
	private Card card;
	private MinionFactory minionFactory;
	private MinionType minionType;

	public Minion() {}

	public Minion(Minion other) {
		super(other);
		card = other.card;
		minionFactory = other.minionFactory;
		minionType = other.minionType;
	}

	public Card getCard() {
		return card;
	}

	public MinionFactory getMinionFactory() {
		return minionFactory;
	}

	public Minion setMinionFactory(MinionFactory minionFactory) {
		this.minionFactory = minionFactory;
		return this;
	}

	public MinionType getMinionType() {
		return minionType;
	}

	public Minion setMinionType(MinionType minionType) {
		this.minionType = minionType;
		return this;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		if (event instanceof MinionTakesDamageEvent) {
			MinionTakesDamageEvent minionTakesDamageEvent = (MinionTakesDamageEvent)event;
			if (minionTakesDamageEvent.getCharacter().getTargetRef().equals(getTargetRef())) {
				Minion minion = (Minion)state.findTarget(getTargetRef());
				if (minion.getCurrentHitPoints() <= 0) {
					return state.apply(new DestroyMinionAtomic(this));
				}
			}
		}
		return super.onEvent(state, event);
	}
}
