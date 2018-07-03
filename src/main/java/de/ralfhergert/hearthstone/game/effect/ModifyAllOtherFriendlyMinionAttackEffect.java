package de.ralfhergert.hearthstone.game.effect;

import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.event.GameEvent;
import de.ralfhergert.hearthstone.event.GameEventListener;
import de.ralfhergert.hearthstone.event.MinionChangedOwnerEvent;
import de.ralfhergert.hearthstone.event.MinionChangesOwnerEvent;
import de.ralfhergert.hearthstone.event.MinionEntersBattlefieldEvent;
import de.ralfhergert.hearthstone.game.effect.modifier.EffectEventListener;
import de.ralfhergert.hearthstone.game.effect.modifier.NoEffectEventListener;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.Player;

/**
 * This effect is a permanent modification of the attack of a character.
 */
public class ModifyAllOtherFriendlyMinionAttackEffect implements GeneralEffect, GameEventListener<HearthstoneGameState> {

	private final int modification;
	private final EffectEventListener<ModifyAllOtherFriendlyMinionAttackEffect> eventListener;

	public ModifyAllOtherFriendlyMinionAttackEffect(int modification) {
		this(modification, new NoEffectEventListener<>());
	}

	public ModifyAllOtherFriendlyMinionAttackEffect(int modification, EffectEventListener<ModifyAllOtherFriendlyMinionAttackEffect> eventListener) {
		this.modification = modification;
		this.eventListener = eventListener;
	}

	/**
	 * Applies the buff.
	 */
	@Override
	public HearthstoneGameState applyTo(HearthstoneGameState state) {
		final Character effectOwner = state.getEffectOwner(this);
		final Player player = state.getOwner(this);
		for (Minion minion : player.getBattlefield()) {
			if (!minion.getTargetRef().equals(effectOwner.getTargetRef())) {
				minion.setPower(minion.getPower() + modification);
			}
		}
		return state;
	}

	/**
	 * Removes the buff.
	 */
	@Override
	public HearthstoneGameState unapplyOn(HearthstoneGameState state) {
		final Character effectOwner = state.getEffectOwner(this);
		final Player player = state.getOwner(this);
		for (Minion minion : player.getBattlefield()) {
			if (!minion.getTargetRef().equals(effectOwner.getTargetRef())) {
				minion.setPower(minion.getPower() - modification);
			}
		}
		return state;
	}

	@Override
	public HearthstoneGameState onEvent(HearthstoneGameState state, GameEvent event) {
		HearthstoneGameState nextState = state;
		if (event instanceof MinionEntersBattlefieldEvent) {
			MinionEntersBattlefieldEvent minionEntersBattlefieldEvent = (MinionEntersBattlefieldEvent)event;
			if (nextState.getEffectOwner(this).getTargetRef().getId() == minionEntersBattlefieldEvent.getMinionRef().getId()) {
				nextState = applyTo(nextState);
			} else {
				final Player owner = nextState.getOwner(this);
				final Minion minion = nextState.findTarget(minionEntersBattlefieldEvent.getMinionRef());
				if (owner.getTargetRef().equals(nextState.getOwner(minion).getTargetRef())) {
					minion.setPower(minion.getPower() + modification);
				}
			}
		}
		if (event instanceof MinionChangesOwnerEvent) {
			MinionChangesOwnerEvent minionChangesOwnerEvent = (MinionChangesOwnerEvent)event;
			if (nextState.getEffectOwner(this).getTargetRef().getId() == minionChangesOwnerEvent.getMinionRef().getId()) {
				nextState = unapplyOn(nextState);
			} else {
				final Minion minion = nextState.findTarget(minionChangesOwnerEvent.getMinionRef());
				final Player owner = nextState.getOwner(this);
				if (minionChangesOwnerEvent.getOldOwner().equals(owner.getTargetRef())) {
					minion.setPower(minion.getPower() - modification);
				}
			}
		}
		if (event instanceof MinionChangedOwnerEvent) {
			MinionChangedOwnerEvent minionChangedOwnerEvent = (MinionChangedOwnerEvent)event;
			if (nextState.getEffectOwner(this).getTargetRef().getId() == minionChangedOwnerEvent.getMinionRef().getId()) {
				nextState = applyTo(nextState);
			} else {
				final Minion minion = nextState.findTarget(minionChangedOwnerEvent.getMinionRef());
				final Player owner = nextState.getOwner(this);
				if (minionChangedOwnerEvent.getNewOwner().equals(owner.getTargetRef())) {
					minion.setPower(minion.getPower() + modification);
				}
			}
		}
		return eventListener.onEvent(nextState, event, this);
	}

	@Override
	public boolean isApplicableTo(HearthstoneGameState state) {
		return true;
	}
}
