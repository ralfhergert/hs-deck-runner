package de.ralfhergert.hearthstone.card;

import de.ralfhergert.hearthstone.action.ActionDiscovery;
import de.ralfhergert.hearthstone.action.DrawCardsAction;
import de.ralfhergert.hearthstone.atomic.BounceMinionToHandAtomic;
import de.ralfhergert.hearthstone.atomic.DamageCharacterAtomic;
import de.ralfhergert.hearthstone.atomic.HealCharacterAtomic;
import de.ralfhergert.hearthstone.atomic.SummonTokenAtomic;
import de.ralfhergert.hearthstone.effect.Effect;
import de.ralfhergert.hearthstone.effect.GeneralEffect;
import de.ralfhergert.hearthstone.game.effect.AfterYouSummonAMinion;
import de.ralfhergert.hearthstone.game.effect.BattlecryEffect;
import de.ralfhergert.hearthstone.game.effect.ChargeEffect;
import de.ralfhergert.hearthstone.game.effect.ModifyAttackEffect;
import de.ralfhergert.hearthstone.game.effect.SpellDamageEffect;
import de.ralfhergert.hearthstone.game.effect.TauntEffect;
import de.ralfhergert.hearthstone.game.effect.WheneverThisMinionTakesDamage;
import de.ralfhergert.hearthstone.game.minion.MinionFactory;
import de.ralfhergert.hearthstone.game.model.AbilityCard;
import de.ralfhergert.hearthstone.game.model.Card;
import de.ralfhergert.hearthstone.game.model.CardSet;
import de.ralfhergert.hearthstone.game.model.CardType;
import de.ralfhergert.hearthstone.game.model.Character;
import de.ralfhergert.hearthstone.game.model.HearthstoneGameState;
import de.ralfhergert.hearthstone.game.model.HeroClass;
import de.ralfhergert.hearthstone.game.model.Minion;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.MinionType;
import de.ralfhergert.hearthstone.game.model.Player;
import de.ralfhergert.hearthstone.game.model.PlayerOrdinal;
import de.ralfhergert.hearthstone.game.model.Rarity;
import de.ralfhergert.hearthstone.game.model.TargetRef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This repository hold all known cards.
 */
public final class CardRepository {

	private static List<CardEntry<? extends CardEntry, ? extends Card>> cards = Arrays.asList(
		new MinionCardEntry(15,  CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 4, "Oasis Snapjaw", CardType.Minion, new MinionFactory().setMinionType(MinionType.Beast).setPower(2).setHitPoints(7)),
		new MinionCardEntry(24,  CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Shieldbearer", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(4).addEffect(new TauntEffect())),
		new MinionCardEntry(27,  CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 5, "Booty Bay Bodyguard", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(4).addEffect(new TauntEffect())),
		new MinionCardEntry(31,  CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 4, "Chillwind Yeti", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(5)),
		new MinionCardEntry(36,  CardSet.Basic,   Rarity.Free, HeroClass.Warlock, 6, "Dread Infernal", CardType.Minion, new MinionFactory().setMinionType(MinionType.Demon).setPower(6).setHitPoints(6).addEffect(new BattlecryEffect() {
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				HearthstoneGameState nextState = state;
				final TargetRef targetRef = state.getEffectOwner(this).getTargetRef();
				for (Character character : state.getAllCharacters()) {
					if (!character.getTargetRef().equals(targetRef)) {
						nextState = new DamageCharacterAtomic(character.getTargetRef(), 1).apply(nextState);
					}
				}
				return nextState;
			}
		})),
		new AbilityCardEntry(38, CardSet.Classic, Rarity.Common, HeroClass.Priest, 0, "Circle of Healing", new GeneralEffect() {
			/** Restore 4 Health to ALL minions. */
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				HearthstoneGameState nextState = state;
				for (Minion minion : state.getActivePlayer().getBattlefield()) {
					nextState = new HealCharacterAtomic(minion.getTargetRef(), 4).apply(nextState);
				}
				for (Minion minion : state.getPassivePlayer().getBattlefield()) {
					nextState = new HealCharacterAtomic(minion.getTargetRef(), 4).apply(nextState);
				}
				return nextState;
			}
		}),
		new AbilityCardEntry(44, CardSet.Basic, Rarity.Free, HeroClass.Mage, 7, "Flamestrike", new GeneralEffect() {
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				HearthstoneGameState nextState = state;
				for (Minion minion : state.getOpposingPlayer(state.getActivePlayer()).getBattlefield()) {
					nextState = new DamageCharacterAtomic(minion.getTargetRef(), 4).apply(nextState);
				}
				return nextState;
			}
		}),
		new MinionCardEntry(55,  CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 1, "Murloc Raider", CardType.Minion, new MinionFactory().setMinionType(MinionType.Murloc).setPower(2).setHitPoints(1)),
		new MinionCardEntry(60,  CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 6, "Boulderfist Ogre", CardType.Minion, new MinionFactory().setPower(6).setHitPoints(7)),
		new MinionCardEntry(76,  CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 1, "Stonetusk Boar", CardType.Minion, new MinionFactory().setMinionType(MinionType.Beast).setPower(1).setHitPoints(1).addEffect(new ChargeEffect())),
		new MinionCardEntry(84,  CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 5, "Darkscale Healer", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(5).addEffect(new BattlecryEffect() {
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				HearthstoneGameState nextState = state;
				final Player player = state.getOwner(this);
				nextState = new HealCharacterAtomic(player.getTargetRef(), 2).apply(nextState);
				for (Minion minion : player.getBattlefield()) {
					nextState = new HealCharacterAtomic(minion.getTargetRef(), 2).apply(nextState);
				}
				return nextState;
			}
		})),
		new AbilityCardEntry(90, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 7, "Sprint", new GeneralEffect() {
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				return new DrawCardsAction(state.getPlayerOrdinal(state.getActivePlayer()), 4).apply(state);
			}
		}),
		new AbilityCardEntry(122, CardSet.Basic, Rarity.Free, HeroClass.Warlock, 4, "Hellfire", new GeneralEffect() {
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				HearthstoneGameState nextState = state;
				for (Character character : new ArrayList<>(nextState.getAllCharacters())) {
					nextState = new DamageCharacterAtomic(character.getTargetRef(), 3).apply(nextState);
				}
				return nextState;
			}
		}),
		new MinionCardEntry(124, CardSet.Classic, Rarity.Epic, HeroClass.Shaman, 5, "Earth Elemental", CardType.Minion, new MinionFactory().setMinionType(MinionType.Elemental).setPower(7).setHitPoints(8).addEffect(new TauntEffect())).setOverloadCost(3),
		new MinionCardEntry(130, CardSet.Basic,   Rarity.Free, HeroClass.Warrior, 4, "Kor'kron Elite", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(3).addEffect(new ChargeEffect())),
		new MinionCardEntry(173, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 7, "Core Hound", CardType.Minion, new MinionFactory().setMinionType(MinionType.Beast).setPower(9).setHitPoints(5)),
		new MinionCardEntry(174, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 3, "Wolfrider", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(1).addEffect(new ChargeEffect())),
		new MinionCardEntry(194, CardSet.Classic, Rarity.Legendary, HeroClass.Hunter, 9, "King Krush", CardType.Minion, new MinionFactory().setMinionType(MinionType.Beast).setPower(8).setHitPoints(8).addEffect(new ChargeEffect())),
		new MinionCardEntry(238, CardSet.Basic,   Rarity.Free, HeroClass.Druid, 8, "Ironbark Protector", CardType.Minion, new MinionFactory().setPower(8).setHitPoints(8).addEffect(new TauntEffect())),
		new MinionCardEntry(273, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 0, "Wisp", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)),
		new MinionCardEntry(283, CardSet.Basic,   Rarity.Free, HeroClass.Paladin, 7, "Guardian of Kings", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(6).addEffect(new BattlecryEffect() {
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				return new HealCharacterAtomic(state.getOwner(this).getTargetRef(), 6).apply(state);
			}
		})),
		new MinionCardEntry(289, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 2, "Bluegill Warrior", CardType.Minion, new MinionFactory().setMinionType(MinionType.Murloc).setPower(2).setHitPoints(1).addEffect(new ChargeEffect())),
		new MinionCardEntry(323, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 7, "War Golem", CardType.Minion, new MinionFactory().setPower(7).setHitPoints(7)),
		new MinionCardEntry(326, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 4, "Sen'jin Shieldmasta", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(5).addEffect(new TauntEffect())),
		new MinionCardEntry(340, CardSet.Basic,   Rarity.Free, HeroClass.Warlock, 1, "Voidwalker", CardType.Minion, new MinionFactory().setMinionType(MinionType.Demon).setPower(1).setHitPoints(3).addEffect(new TauntEffect())),
		new MinionCardEntry(346, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Mogu'shan Warden", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(7).addEffect(new TauntEffect())),
		new MinionCardEntry(362, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 3, "Magma Rager", CardType.Minion, new MinionFactory().setMinionType(MinionType.Elemental).setPower(5).setHitPoints(1)),
		new MinionCardEntry(388, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 3, "Dalaran Mage", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(4).addEffect(new SpellDamageEffect(1))),
		new MinionCardEntry(414, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 6, "Lord of the Arena", CardType.Minion, new MinionFactory().setPower(6).setHitPoints(5).addEffect(new TauntEffect())),
		new MinionCardEntry(422, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Knife Juggler", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2).addEffect(new AfterYouSummonAMinion() {
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				final Player owner = state.getOwner(this);
				final List<Character> opponents = state.getOpposingPlayer(owner).getAllCharactersInOrderOfPlay();
				final int targetIndex = state.getRandom().nextInt(opponents.size());
				return new DamageCharacterAtomic(opponents.get(targetIndex).getTargetRef(), 1).apply(state);
			}
		})),
		new MinionCardEntry(428, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Acolyte of Pain", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(3).addEffect(new WheneverThisMinionTakesDamage() {
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				return new DrawCardsAction(state.getPlayerOrdinal(state.getOwner(this)), 1).apply(state);
			}
		})),
		new MinionCardEntry(476, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 5, "Fen Creeper", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(6).addEffect(new TauntEffect())),
		new MinionCardEntry(479, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 2, "Kobold Geomancer", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2).addEffect(new SpellDamageEffect(1))),
		new MinionCardEntry(519, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 3, "Ironfur Grizzly", CardType.Minion, new MinionFactory().setMinionType(MinionType.Beast).setPower(3).setHitPoints(3).addEffect(new TauntEffect())),
		new MinionCardEntry(535, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 2, "River Crocolisk", CardType.Minion, new MinionFactory().setMinionType(MinionType.Beast).setPower(2).setHitPoints(3)),
		new MinionCardEntry(545, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 6, "Archmage", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(7).addEffect(new SpellDamageEffect(1))),
		new MinionCardEntry(560, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 6, "Reckless Rocketeer", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(2).addEffect(new ChargeEffect())),
		new MinionCardEntry(564, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 1, "Goldshire Footman", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(2).addEffect(new TauntEffect())),
		new MinionCardEntry(576, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 2, "Bloodfen Raptor", CardType.Minion, new MinionFactory().setMinionType(MinionType.Beast).setPower(3).setHitPoints(2)),
		new MinionCardEntry(603, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 4, "Stormwind Knight", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(5).addEffect(new ChargeEffect())),
		new MinionCardEntry(611, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 3, "Silverback Patriarch", CardType.Minion, new MinionFactory().setMinionType(MinionType.Beast).setPower(1).setHitPoints(4).addEffect(new TauntEffect())),
		new MinionCardEntry(624, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 5, "Gurubashi Berserker", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(7).addEffect(new WheneverThisMinionTakesDamage() {
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				Character character = state.getEffectOwner(this);
				final ModifyAttackEffect effect = new ModifyAttackEffect(3);
				character.addEffect(effect);
				return effect.applyTo(state);
			}
		})),
		new AbilityCardEntry(658, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 6, "Vanish", new GeneralEffect() {
			/**
			 * Return all minions to their owner's hand.
			 *  - The minions are returned in the order of play. (oldest minion first)
			 *  - Cards which can not be returned because the hand is already full are destroyed. (Thus deathrattle triggers.)
			 *  - TODO: for cards which generate tokens, the tokens enter play first, thus vanish returns them first. (for instance "Dr. Boom")
			 */
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				HearthstoneGameState nextState = state;
				List<Minion> minions = new ArrayList<>();
				minions.addAll(nextState.getPlayer(PlayerOrdinal.One).getBattlefield());
				minions.addAll(nextState.getPlayer(PlayerOrdinal.Two).getBattlefield());
				// sort the minion by creation-time (implicitly stored in the targetRef)
				minions.sort(new Comparator<Minion>() {
					@Override
					public int compare(Minion o1, Minion o2) {
						return (int)(o1.getTargetRef().getId() - o2.getTargetRef().getId());
					}
				});
				for (Minion minion : minions) {
					nextState = new BounceMinionToHandAtomic(minion.getTargetRef()).apply(nextState);
				}
				return nextState;
			}
		}),
		new MinionCardEntry(659, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 4, "Ogre Magi", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4).addEffect(new SpellDamageEffect(1))),
		new MinionCardEntry(663, CardSet.Basic,   Rarity.Free, HeroClass.Neutral, 2, "Frostwolf Grunt", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2).addEffect(new TauntEffect())),
		new AbilityCardEntry(671, CardSet.Basic, Rarity.Free, HeroClass.Priest, 5, "Holy Nova", new GeneralEffect() {
			/** Deal 2 damage to all enemies. Restore 2 Health to all friendly characters. */
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				HearthstoneGameState nextState = state;
				for (Character character : nextState.getPassivePlayer().getAllCharactersInOrderOfPlay()) {
					nextState = new DamageCharacterAtomic(character.getTargetRef(), 2).apply(nextState);
				}
				for (Character character : nextState.getActivePlayer().getAllCharactersInOrderOfPlay()) {
					nextState = new HealCharacterAtomic(character.getTargetRef(), 2).apply(nextState);
				}
				return nextState;
			}
		}),
		new MinionCardEntry(14443, CardSet.Blackrock, Rarity.Common, HeroClass.Warlock, 3, "Imp Gang Boss", CardType.Minion, new MinionFactory().setMinionType(MinionType.Demon).setPower(2).setHitPoints(4).addEffect(new WheneverThisMinionTakesDamage() {
			/** Whenever this minion takes damage, summon a 1/1 imp. */
			@Override
			public HearthstoneGameState applyTo(HearthstoneGameState state) {
				return new SummonTokenAtomic(state.getPlayerOrdinal(state.getOwner(this)), "Imp").apply(state);
			}
		})),
		new MinionCardEntry(14642, CardSet.Blackrock, Rarity.Free, HeroClass.Warlock, 1, "Imp", CardType.Token, new MinionFactory().setMinionType(MinionType.Demon).setPower(1).setHitPoints(1))
/* Cards which effects are not yet implemented.
401, CardSet.Basic, Rarity.Free, HeroClass.Priest, 10, "Mind Control", new Effect()
310, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 7, "Stormwind Champion", CardType.Minion, new MinionFactory().setPower(6).setHitPoints(6)
667, CardSet.Basic, Rarity.Free, HeroClass.Druid, 6, "Starfire", new Effect()
636, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 6, "Fire Elemental", CardType.Minion, new MinionFactory().setPower(6).setHitPoints(5)
182, CardSet.Basic, Rarity.Free, HeroClass.Warrior, 5, "Arcanite Reaper", new WeaponFactory().setAttack(5).setDurability(2)
433, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 5, "Assassin's Blade", new WeaponFactory().setAttack(3).setDurability(4)
568, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 5, "Assassinate", new Effect()
256, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 5, "Bloodlust", new Effect()
77486, CardSet.Basic, Rarity.Free, HeroClass.Mage, 5, "Polymorph: ???", new Effect()
604, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 5, "Frostwolf Warlord", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
184, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 5, "Nightblade", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
101, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 5, "Starving Buzzard", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(2)
325, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 5, "Stormpike Commando", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(2)
162, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 5, "Tundra Rhino", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(5)
293, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 4, "Truesilver Champion", new WeaponFactory().setAttack(4).setDurability(2)
29, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 4, "Blessing of Kings", new Effect()
260, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 4, "Consecration", new Effect()
522, CardSet.Basic, Rarity.Free, HeroClass.Mage, 4, "Fireball", new Effect()
350, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 4, "Hammer of Wrath", new Effect()
270, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 4, "Hex", new Effect()
407, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 4, "Multi-Shot", new Effect()
595, CardSet.Basic, Rarity.Free, HeroClass.Mage, 4, "Polymorph", new Effect()
620, CardSet.Basic, Rarity.Free, HeroClass.Druid, 4, "Swipe", new Effect()
472, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 4, "Dragonling Mechanic", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(4)
246, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 4, "Gnomish Inventor", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(4)
225, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 4, "Houndmaster", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(3)
274, CardSet.Basic, Rarity.Free, HeroClass.Mage, 4, "Water Elemental", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(6)
151, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 4, "Windspeaker", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
77495, CardSet.Basic, Rarity.Free, HeroClass.Warrior, 3, "Blazing Longsword", new WeaponFactory().setAttack(2).setDurability(3)
632, CardSet.Basic, Rarity.Free, HeroClass.Warrior, 3, "Fiery War Axe", new WeaponFactory().setAttack(3).setDurability(2)
578, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 3, "Animal Companion", new Effect()
489, CardSet.Basic, Rarity.Free, HeroClass.Mage, 3, "Arcane Intellect", new Effect()
332, CardSet.Basic, Rarity.Free, HeroClass.Warlock, 3, "Drain Life", new Effect()
378, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 3, "Fan of Knives", new Effect()
49, CardSet.Basic, Rarity.Free, HeroClass.Mage, 3, "Frost Nova", new Effect()
258, CardSet.Basic, Rarity.Free, HeroClass.Druid, 3, "Healing Touch", new Effect()
488, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 3, "Kill Command", new Effect()
77487, CardSet.Basic, Rarity.Free, HeroClass.Druid, 3, "Nature's Champion", new Effect()
329, CardSet.Basic, Rarity.Free, HeroClass.Druid, 3, "Savage Roar", new Effect()
647, CardSet.Basic, Rarity.Free, HeroClass.Warlock, 3, "Shadow Bolt", new Effect()
547, CardSet.Basic, Rarity.Free, HeroClass.Priest, 3, "Shadow Word: Death", new Effect()
493, CardSet.Basic, Rarity.Free, HeroClass.Warrior, 3, "Shield Block", new Effect()
41, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 3, "Ironforge Rifleman", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
502, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 3, "Raid Leader", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
47, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 3, "Razorfen Hunter", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
434, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 3, "Shattered Sun Cleric", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(2)
193, CardSet.Basic, Rarity.Free, HeroClass.Warrior, 3, "Warsong Commander", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
56, CardSet.Basic, Rarity.Free, HeroClass.Mage, 2, "Arcane Explosion", new Effect()
81, CardSet.Basic, Rarity.Free, HeroClass.Warrior, 2, "Cleave", new Effect()
77484, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 2, "Deadeye", new Effect()
554, CardSet.Basic, Rarity.Free, HeroClass.Priest, 2, "Divine Spirit", new Effect()
227, CardSet.Basic, Rarity.Free, HeroClass.Warrior, 2, "Execute", new Effect()
177, CardSet.Basic, Rarity.Free, HeroClass.Mage, 2, "Frostbolt", new Effect()
77490, CardSet.Basic, Rarity.Free, HeroClass.Priest, 2, "Generous Spirit", new Effect()
1, CardSet.Basic, Rarity.Free, HeroClass.Warrior, 2, "Heroic Strike", new Effect()
108, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 2, "Holy Light", new Effect()
480, CardSet.Basic, Rarity.Free, HeroClass.Druid, 2, "Mark of the Wild", new Effect()
415, CardSet.Basic, Rarity.Free, HeroClass.Priest, 2, "Mind Blast", new Effect()
491, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 2, "Rockbiter Weapon", new Effect()
385, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 2, "Sap", new Effect()
315, CardSet.Basic, Rarity.Free, HeroClass.Priest, 2, "Shadow Word: Pain", new Effect()
164, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 2, "Shiv", new Effect()
282, CardSet.Basic, Rarity.Free, HeroClass.Druid, 2, "Wild Growth", new Effect()
146, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 2, "Windfury", new Effect()
74, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 2, "Acidic Swamp Ooze", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(2)
390, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 2, "Flametongue Totem", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(3)
357, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 2, "Murloc Tidehunter", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(1)
435, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 2, "Novice Engineer", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
208, CardSet.Basic, Rarity.Free, HeroClass.Warlock, 2, "Succubus", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(3)
250, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 1, "Light's Justice", new WeaponFactory().setAttack(1).setDurability(4)
183, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 1, "Wicked Knife", new WeaponFactory().setAttack(1).setDurability(2)
589, CardSet.Basic, Rarity.Free, HeroClass.Mage, 1, "Arcane Missiles", new Effect()
167, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 1, "Arcane Shot", new Effect()
394, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 1, "Blessing of Might", new Effect()
646, CardSet.Basic, Rarity.Free, HeroClass.Warrior, 1, "Charge", new Effect()
532, CardSet.Basic, Rarity.Free, HeroClass.Druid, 1, "Claw", new Effect()
252, CardSet.Basic, Rarity.Free, HeroClass.Warlock, 1, "Corruption", new Effect()
87, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 1, "Deadly Poison", new Effect()
233, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 1, "Frost Shock", new Effect()
499, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 1, "Hand of Protection", new Effect()
77489, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 1, "Hand of Salvation", new Effect()
409, CardSet.Basic, Rarity.Free, HeroClass.Priest, 1, "Holy Smite", new Effect()
189, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 1, "Humility", new Effect()
22, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 1, "Hunter's Mark", new Effect()
438, CardSet.Basic, Rarity.Free, HeroClass.Priest, 1, "Mind Vision", new Effect()
30, CardSet.Basic, Rarity.Free, HeroClass.Mage, 1, "Mirror Image", new Effect()
43, CardSet.Basic, Rarity.Free, HeroClass.Warlock, 1, "Mortal Coil", new Effect()
431, CardSet.Basic, Rarity.Free, HeroClass.Priest, 1, "Power Word: Shield", new Effect()
205, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 1, "Sinister Strike", new Effect()
77491, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 1, "Smoke Bomb", new Effect()
529, CardSet.Basic, Rarity.Free, HeroClass.Warlock, 1, "Soulfire", new Effect()
163, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 1, "Tracking", new Effect()
161, CardSet.Basic, Rarity.Free, HeroClass.Warrior, 1, "Whirlwind", new Effect()
356, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 1, "Elven Archer", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
510, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 1, "Grimscale Oracle", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
275, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 1, "Healing Totem", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(2)
600, CardSet.Basic, Rarity.Free, HeroClass.Priest, 1, "Northshire Cleric", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(3)
98, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 1, "Searing Totem", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
268, CardSet.Basic, Rarity.Free, HeroClass.Paladin, 1, "Silver Hand Recruit", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
298, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 1, "Stoneclaw Totem", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(2)
86, CardSet.Basic, Rarity.Free, HeroClass.Hunter, 1, "Timber Wolf", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
410, CardSet.Basic, Rarity.Free, HeroClass.Neutral, 1, "Voodoo Doctor", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(1)
365, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 1, "Wrath of Air Totem", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(2)
216, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 0, "Ancestral Healing", new Effect()
471, CardSet.Basic, Rarity.Free, HeroClass.Rogue, 0, "Backstab", new Effect()
77494, CardSet.Basic, Rarity.Free, HeroClass.Warlock, 0, "Bottled Madness", new Effect()
77493, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 0, "Crackling Doom", new Effect()
548, CardSet.Basic, Rarity.Free, HeroClass.Druid, 0, "Innervate", new Effect()
619, CardSet.Basic, Rarity.Free, HeroClass.Druid, 0, "Moonfire", new Effect()
348, CardSet.Basic, Rarity.Free, HeroClass.Warlock, 0, "Sacrificial Pact", new Effect()
367, CardSet.Basic, Rarity.Free, HeroClass.Shaman, 0, "Totemic Might", new Effect()
264, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 12, "Mountain Giant", CardType.Minion, new MinionFactory().setPower(8).setHitPoints(8) "Costs (1) less for each other card in your hand."
496, CardSet.Classic, Rarity.Epic, HeroClass.Mage, 10, "Pyroblast", new Effect() "Deal 10 damage."
474, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 10, "Deathwing", CardType.Minion, new MinionFactory().setPower(12).setHitPoints(12) "Battlecry: Destroy all other minions and discard your hand."
614, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 10, "Sea Giant", CardType.Minion, new MinionFactory().setPower(8).setHitPoints(8) "Costs (1) less for each other minion on the battlefield."
303, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 9, "Alexstrasza", CardType.Minion, new MinionFactory().setPower(8).setHitPoints(8) "Battlecry: Set a hero's remaining health to 15."
605, CardSet.Classic, Rarity.Legendary, HeroClass.Druid, 9, "Cenarius", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(8) "Choose one: Give your other minions +2/+2; or summon two 2/2 Treants with taunt."
482, CardSet.Classic, Rarity.Legendary, HeroClass.Warlock, 9, "Lord Jaraxxus", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(15) "Battlecry: Destroy your hero and replace it with Lord Jaraxxus."
241, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 9, "Malygos", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(12) "Spell Damage +5"
285, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 9, "Nozdormu", CardType.Minion, new MinionFactory().setPower(8).setHitPoints(8)
432, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 9, "Onyxia", CardType.Minion, new MinionFactory().setPower(8).setHitPoints(8)
495, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 9, "Ysera", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(12)
506, CardSet.Classic, Rarity.Epic, HeroClass.Paladin, 8, "Lay on Hands", new Effect()
398, CardSet.Classic, Rarity.Epic, HeroClass.Warlock, 8, "Twisting Nether", new Effect()
335, CardSet.Classic, Rarity.Legendary, HeroClass.Shaman, 8, "Al'Akir the Windlord", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(5) "Windfury, Charge, Divine Shield, Taunt"
643, CardSet.Classic, Rarity.Legendary, HeroClass.Warrior, 8, "Grommash Hellscream", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(9) "Charge; Enrage: +6 Attack"
18, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 8, "Gruul", CardType.Minion, new MinionFactory().setPower(7).setHitPoints(7)
391, CardSet.Classic, Rarity.Legendary, HeroClass.Paladin, 8, "Tirion Fordring", CardType.Minion, new MinionFactory().setPower(6).setHitPoints(6)
278, CardSet.Classic, Rarity.Epic, HeroClass.Hunter, 7, "Gladiator's Longbow", new WeaponFactory.setAttack(5).setDurability(2)
96, CardSet.Classic, Rarity.Epic, HeroClass.Warrior, 7, "Gorehowl", new WeaponFactory.setAttack(7).setDurability(1)
34, CardSet.Classic, Rarity.Epic, HeroClass.Druid, 7, "Ancient of Lore", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(5)
242, CardSet.Classic, Rarity.Epic, HeroClass.Druid, 7, "Ancient of War", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(5)
220, CardSet.Classic, Rarity.Legendary, HeroClass.Mage, 7, "Archmage Antonidas", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(7)
539, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 7, "Baron Geddon", CardType.Minion, new MinionFactory().setPower(7).setHitPoints(5)
228, CardSet.Classic, Rarity.Legendary, HeroClass.Priest, 7, "Prophet Velen", CardType.Minion, new MinionFactory().setPower(7).setHitPoints(7)
518, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 7, "Ravenholdt Assassin", CardType.Minion, new MinionFactory().setPower(7).setHitPoints(5) "Stealth"
142, CardSet.Classic, Rarity.Epic, HeroClass.Paladin, 6, "Avenging Wrath", new Effect()
276, CardSet.Classic, Rarity.Rare, HeroClass.Mage, 6, "Blizzard", new Effect()
457, CardSet.Classic, Rarity.Rare, HeroClass.Priest, 6, "Holy Fire", new Effect()
573, CardSet.Classic, Rarity.Rare, HeroClass.Warlock, 6, "Siphon Soul", new Effect()
463, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 6, "Argent Commander", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(2) "Charge; Divine Shield"
147, CardSet.Classic, Rarity.Epic, HeroClass.Priest, 6, "Cabal Shadow Priest", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(5)
498, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 6, "Cairne Bloodhoof", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(5)
598, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 6, "Frost Elemental", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(5)
131, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 6, "Gadgetzan Auctioneer", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
39, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 6, "Hogger", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
203, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 6, "Illidan Stormrage", CardType.Minion, new MinionFactory().setPower(7).setHitPoints(5)
562, CardSet.Classic, Rarity.Epic, HeroClass.Rogue, 6, "Kidnapper", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(3)
138, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 6, "Priestess of Elune", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(4)
8, CardSet.Classic, Rarity.Rare, HeroClass.Hunter, 6, "Savannah Highmane", CardType.Minion, new MinionFactory().setPower(6).setHitPoints(5)
221, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 6, "Sunwalker", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(5)
232, CardSet.Classic, Rarity.Common, HeroClass.Priest, 6, "Temple Enforcer", CardType.Minion, new MinionFactory().setPower(6).setHitPoints(6)
179, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 6, "The Beast", CardType.Minion, new MinionFactory().setPower(9).setHitPoints(7)
396, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 6, "The Black Knight", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(5)
675, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 6, "Windfury Harpy", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(5)
172, CardSet.Classic, Rarity.Epic, HeroClass.Shaman, 5, "Doomhammer", new WeaponFactory.setAttack(2).setDurability(8)
670, CardSet.Classic, Rarity.Epic, HeroClass.Warlock, 5, "Bane of Doom", new Effect()
7, CardSet.Classic, Rarity.Rare, HeroClass.Paladin, 5, "Blessed Champion", new Effect()
297, CardSet.Classic, Rarity.Epic, HeroClass.Warrior, 5, "Brawl", new Effect()
114, CardSet.Classic, Rarity.Rare, HeroClass.Hunter, 5, "Explosive Shot", new Effect()
237, CardSet.Classic, Rarity.Epic, HeroClass.Druid, 5, "Force of Nature", new Effect()
355, CardSet.Classic, Rarity.Rare, HeroClass.Paladin, 5, "Holy Wrath", new Effect()
120, CardSet.Classic, Rarity.Rare, HeroClass.Druid, 5, "Nourish", new Effect()
464, CardSet.Classic, Rarity.Rare, HeroClass.Druid, 5, "Starfall", new Effect()
597, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 5, "Abomination", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
73, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 5, "Big Game Hunter", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(2)
267, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 5, "Captain Greenskin", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(4)
507, CardSet.Classic, Rarity.Rare, HeroClass.Warlock, 5, "Doomguard", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(7)
587, CardSet.Classic, Rarity.Common, HeroClass.Druid, 5, "Druid of the Claw", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
450, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 5, "Faceless Manipulator", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
602, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 5, "Harrison Jones", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(4)
674, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 5, "Leeroy Jenkins", CardType.Minion, new MinionFactory().setPower(6).setHitPoints(2)
648, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 5, "Silver Hand Knight", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
627, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 5, "Spiteful Smith", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(6)
389, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 5, "Stampeding Kodo", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(5)
338, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 5, "Stranglethorn Tiger", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(5)
509, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 5, "Venture Co. Mercenary", CardType.Minion, new MinionFactory().setPower(7).setHitPoints(6)
266, CardSet.Classic, Rarity.Rare, HeroClass.Druid, 4, "Bite", new Effect()
244, CardSet.Classic, Rarity.Rare, HeroClass.Rogue, 4, "Blade Flurry", new Effect()
26, CardSet.Classic, Rarity.Common, HeroClass.Mage, 4, "Cone of Cold", new Effect()
249, CardSet.Classic, Rarity.Rare, HeroClass.Priest, 4, "Mass Dispel", new Effect()
301, CardSet.Classic, Rarity.Epic, HeroClass.Priest, 4, "Mindgames", new Effect()
345, CardSet.Classic, Rarity.Rare, HeroClass.Warrior, 4, "Mortal Strike", new Effect()
442, CardSet.Classic, Rarity.Rare, HeroClass.Priest, 4, "Shadow Madness", new Effect()
673, CardSet.Classic, Rarity.Rare, HeroClass.Warlock, 4, "Shadowflame", new Effect()
311, CardSet.Classic, Rarity.Common, HeroClass.Druid, 4, "Soul of the Forest", new Effect()
572, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Ancient Brewmaster", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(4)
176, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 4, "Ancient Mage", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(5)
504, CardSet.Classic, Rarity.Common, HeroClass.Warrior, 4, "Arathi Weaponsmith", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
656, CardSet.Classic, Rarity.Rare, HeroClass.Priest, 4, "Auchenai Soulpriest", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(5)
140, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Cult Master", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(2)
128, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Dark Iron Dwarf", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
542, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 4, "Defender of Argus", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
261, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Dread Corsair", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
125, CardSet.Classic, Rarity.Rare, HeroClass.Mage, 4, "Ethereal Arcanist", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
459, CardSet.Classic, Rarity.Rare, HeroClass.Druid, 4, "Keeper of the Grove", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
192, CardSet.Classic, Rarity.Common, HeroClass.Priest, 4, "Lightspawn", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(5)
127, CardSet.Classic, Rarity.Rare, HeroClass.Rogue, 4, "Master of Disguise", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
346, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Mogu'shan Warden", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(7)
402, CardSet.Classic, Rarity.Epic, HeroClass.Warlock, 4, "Pit Lord", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(6)
634, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Silvermoon Guardian", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
42, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Spellbreaker", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(3)
566, CardSet.Classic, Rarity.Common, HeroClass.Warlock, 4, "Summoning Portal", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(4)
360, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 4, "Twilight Drake", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(1)
523, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 4, "Violet Teacher", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(5)
363, CardSet.Classic, Rarity.Rare, HeroClass.Hunter, 3, "Eaglehorn Bow", new WeaponFactory.setAttack(3).setDurability(2)
82, CardSet.Classic, Rarity.Rare, HeroClass.Rogue, 3, "Perdition's Blade", new WeaponFactory.setAttack(2).setDurability(2)
567, CardSet.Classic, Rarity.Epic, HeroClass.Paladin, 3, "Sword of Justice", new WeaponFactory.setAttack(1).setDurability(5)
531, CardSet.Classic, Rarity.Rare, HeroClass.Mage, 3, "Counterspell", new Effect()
239, CardSet.Classic, Rarity.Common, HeroClass.Hunter, 3, "Deadly Shot", new Effect()
581, CardSet.Classic, Rarity.Rare, HeroClass.Paladin, 3, "Divine Favor", new Effect()
107, CardSet.Classic, Rarity.Epic, HeroClass.Shaman, 3, "Far Sight", new Effect()
214, CardSet.Classic, Rarity.Rare, HeroClass.Shaman, 3, "Feral Spirit", new Effect()
135, CardSet.Classic, Rarity.Rare, HeroClass.Rogue, 3, "Headcrack", new Effect()
672, CardSet.Classic, Rarity.Common, HeroClass.Mage, 3, "Ice Barrier", new Effect()
679, CardSet.Classic, Rarity.Rare, HeroClass.Shaman, 3, "Lava Burst", new Effect()
676, CardSet.Classic, Rarity.Rare, HeroClass.Shaman, 3, "Lightning Storm", new Effect()
149, CardSet.Classic, Rarity.Common, HeroClass.Druid, 3, "Mark of Nature", new Effect()
569, CardSet.Classic, Rarity.Common, HeroClass.Mage, 3, "Mirror Entity", new Effect()
327, CardSet.Classic, Rarity.Common, HeroClass.Warlock, 3, "Sense Demons", new Effect()
421, CardSet.Classic, Rarity.Epic, HeroClass.Priest, 3, "Shadowform", new Effect()
309, CardSet.Classic, Rarity.Epic, HeroClass.Mage, 3, "Spellbender", new Effect()
62, CardSet.Classic, Rarity.Common, HeroClass.Priest, 3, "Thoughtsteal", new Effect()
317, CardSet.Classic, Rarity.Common, HeroClass.Hunter, 3, "Unleash the Hounds", new Effect()
160, CardSet.Classic, Rarity.Rare, HeroClass.Mage, 3, "Vaporize", new Effect()
425, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Alarm-o-Bot", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(3)
23, CardSet.Classic, Rarity.Rare, HeroClass.Paladin, 3, "Aldor Peacekeeper", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
97, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Arcane Golem", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
75, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 3, "Blood Knight", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
424, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Coldlight Seer", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
212, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Demolisher", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(4)
557, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Earthen Ring Farseer", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
3, CardSet.Classic, Rarity.Legendary, HeroClass.Rogue, 3, "Edwin VanCleef", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
625, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Emperor Cobra", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
236, CardSet.Classic, Rarity.Rare, HeroClass.Warlock, 3, "Felguard", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(5)
610, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Flesheating Ghoul", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
69, CardSet.Classic, Rarity.Rare, HeroClass.Warrior, 3, "Frothing Berserker", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(4)
386, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Harvest Golem", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
178, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Imp Master", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(5)
209, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Injured Blademaster", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(7)
500, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Ironbeak Owl", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(1)
392, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Jungle Panther", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(2)
373, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 3, "King Mukla", CardType.Minion, new MinionFactory().setPower(5).setHitPoints(5)
411, CardSet.Classic, Rarity.Rare, HeroClass.Mage, 3, "Kirin Tor Mage", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(3)
613, CardSet.Classic, Rarity.Rare, HeroClass.Shaman, 3, "Mana Tide Totem", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(3)
368, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Mind Control Tech", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
222, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 3, "Murloc Warleader", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
157, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Questing Adventurer", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
95, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Raging Worgen", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
475, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Scarlet Crusader", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(1)
286, CardSet.Classic, Rarity.Rare, HeroClass.Rogue, 3, "SI:7 Agent", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
324, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 3, "Southsea Captain", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
477, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Tauren Warrior", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
265, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Thrallmar Farseer", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
245, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 3, "Tinkmaster Overspark", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
51, CardSet.Classic, Rarity.Common, HeroClass.Shaman, 3, "Unbound Elemental", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(4)
119, CardSet.Classic, Rarity.Rare, HeroClass.Warlock, 3, "Void Terror", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(3)
152, CardSet.Classic, Rarity.Common, HeroClass.Shaman, 2, "Stormforged Axe", new WeaponFactory.setAttack(2).setDurability(3)
526, CardSet.Classic, Rarity.Rare, HeroClass.Shaman, 2, "Ancestral Spirit", new Effect()
664, CardSet.Classic, Rarity.Common, HeroClass.Warrior, 2, "Battle Rage", new Effect()
198, CardSet.Classic, Rarity.Common, HeroClass.Rogue, 2, "Betrayal", new Effect()
166, CardSet.Classic, Rarity.Rare, HeroClass.Warrior, 2, "Commanding Shout", new Effect()
452, CardSet.Classic, Rarity.Common, HeroClass.Warlock, 2, "Demonfire", new Effect()
383, CardSet.Classic, Rarity.Rare, HeroClass.Paladin, 2, "Equality", new Effect()
382, CardSet.Classic, Rarity.Common, HeroClass.Rogue, 2, "Eviscerate", new Effect()
344, CardSet.Classic, Rarity.Common, HeroClass.Hunter, 2, "Explosive Trap", new Effect()
630, CardSet.Classic, Rarity.Rare, HeroClass.Hunter, 2, "Flare", new Effect()
99, CardSet.Classic, Rarity.Common, HeroClass.Hunter, 2, "Freezing Trap", new Effect()
447, CardSet.Classic, Rarity.Rare, HeroClass.Hunter, 2, "Misdirection", new Effect()
165, CardSet.Classic, Rarity.Common, HeroClass.Druid, 2, "Power of the Wild", new Effect()
454, CardSet.Classic, Rarity.Common, HeroClass.Warrior, 2, "Rampage", new Effect()
215, CardSet.Classic, Rarity.Common, HeroClass.Warrior, 2, "Slam", new Effect()
210, CardSet.Classic, Rarity.Epic, HeroClass.Hunter, 2, "Snake Trap", new Effect()
553, CardSet.Classic, Rarity.Common, HeroClass.Hunter, 2, "Snipe", new Effect()
633, CardSet.Classic, Rarity.Common, HeroClass.Druid, 2, "Wrath", new Effect()
641, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Amani Berserker", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
153, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Ancient Watcher", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(5)
191, CardSet.Classic, Rarity.Common, HeroClass.Paladin, 2, "Argent Protector", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
644, CardSet.Classic, Rarity.Rare, HeroClass.Warrior, 2, "Armorsmith", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(4)
525, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 2, "Bloodmage Thalnos", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
637, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Bloodsail Raider", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
612, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Crazed Alchemist", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
328, CardSet.Classic, Rarity.Common, HeroClass.Warrior, 2, "Cruel Taskmaster", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
417, CardSet.Classic, Rarity.Common, HeroClass.Rogue, 2, "Defias Ringleader", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
305, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Dire Wolf Alpha", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
467, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 2, "Doomsayer", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(7)
213, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Faerie Dragon", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(2)
117, CardSet.Classic, Rarity.Rare, HeroClass.Priest, 2, "Lightwell", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(5)
395, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Loot Hoarder", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(1)
456, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 2, "Lorewalker Cho", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(4)
80, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Mad Bomber", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(2)
67, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Mana Addict", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(3)
197, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Mana Wraith", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
584, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Master Swordsmith", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(3)
339, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 2, "Millhouse Manastorm", CardType.Minion, new MinionFactory().setPower(4).setHitPoints(4)
19, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 2, "Nat Pagle", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(4)
14, CardSet.Classic, Rarity.Epic, HeroClass.Rogue, 2, "Patient Assassin", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
54, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Pint-Sized Summoner", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
279, CardSet.Classic, Rarity.Common, HeroClass.Hunter, 2, "Scavenging Hyena", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(2)
4, CardSet.Classic, Rarity.Common, HeroClass.Mage, 2, "Sorcerer's Apprentice", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(2)
372, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Sunfury Protector", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(3)
25, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Wild Pyromancer", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(2)
247, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Youthful Brewmaster", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(2)
304, CardSet.Classic, Rarity.Epic, HeroClass.Hunter, 1, "Bestial Wrath", new Effect()
100, CardSet.Classic, Rarity.Common, HeroClass.Paladin, 1, "Blessing of Wisdom", new Effect()
92, CardSet.Classic, Rarity.Common, HeroClass.Rogue, 1, "Cold Blood", new Effect()
77, CardSet.Classic, Rarity.Common, HeroClass.Shaman, 1, "Earth Shock", new Effect()
206, CardSet.Classic, Rarity.Common, HeroClass.Paladin, 1, "Eye for an Eye", new Effect()
530, CardSet.Classic, Rarity.Common, HeroClass.Shaman, 1, "Forked Lightning", new Effect()
207, CardSet.Classic, Rarity.Common, HeroClass.Priest, 1, "Inner Fire", new Effect()
10, CardSet.Classic, Rarity.Common, HeroClass.Shaman, 1, "Lightning Bolt", new Effect()
154, CardSet.Classic, Rarity.Common, HeroClass.Druid, 1, "Naturalize", new Effect()
158, CardSet.Classic, Rarity.Common, HeroClass.Paladin, 1, "Noble Sacrifice", new Effect()
657, CardSet.Classic, Rarity.Common, HeroClass.Paladin, 1, "Redemption", new Effect()
642, CardSet.Classic, Rarity.Common, HeroClass.Paladin, 1, "Repentance", new Effect()
148, CardSet.Classic, Rarity.Rare, HeroClass.Druid, 1, "Savagery", new Effect()
50, CardSet.Classic, Rarity.Epic, HeroClass.Warrior, 1, "Shield Slam", new Effect()
638, CardSet.Classic, Rarity.Rare, HeroClass.Warrior, 1, "Upgrade!", new Effect()
577, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Abusive Sergeant", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
57, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Angry Chicken", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
473, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Argent Squire", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
196, CardSet.Classic, Rarity.Common, HeroClass.Warlock, 1, "Blood Imp", CardType.Minion, new MinionFactory().setPower(0).setHitPoints(1)
453, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Bloodsail Corsair", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(2)
129, CardSet.Classic, Rarity.Common, HeroClass.Shaman, 1, "Dust Devil", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(1)
85, CardSet.Classic, Rarity.Common, HeroClass.Warlock, 1, "Flame Imp", CardType.Minion, new MinionFactory().setPower(3).setHitPoints(2)
660, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 1, "Hungry Crab", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(2)
513, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Leper Gnome", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
436, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Lightwarden", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(2)
263, CardSet.Classic, Rarity.Common, HeroClass.Mage, 1, "Mana Wyrm", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(3)
420, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Murloc Tidecaller", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(2)
483, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Secretkeeper", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(2)
103, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Southsea Deckhand", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(1)
112, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Worgen Infiltrator", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(1)
629, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Young Dragonhawk", CardType.Minion, new MinionFactory().setPower(1).setHitPoints(1)
123, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Young Priestess", CardType.Minion, new MinionFactory().setPower(2).setHitPoints(1)
366, CardSet.Classic, Rarity.Common, HeroClass.Warrior, 0, "Inner Rage", new Effect()
364, CardSet.Classic, Rarity.Epic, HeroClass.Rogue, 0, "Preparation", new Effect()
550, CardSet.Classic, Rarity.Common, HeroClass.Rogue, 0, "Shadowstep", new Effect()
544, CardSet.Classic, Rarity.Common, HeroClass.Priest, 0, "Silence", new Effect()
*/
	);

	private CardRepository() { /* no instantiation necessary */ }

	private static Optional<CardEntry<? extends CardEntry, ? extends Card>> find(Predicate<CardEntry<? extends CardEntry, ? extends Card>> predicate) {
		return cards.stream().filter(predicate).findFirst();
	}

	public static Card createById(int id) {
		return find(entry -> entry.id == id)
			.map(CardEntry::create)
			.orElseThrow(() -> new CardNotFoundException("card with id=" + id + " could not be found"));
	}

	public static Card createByName(final String name) {
		return find(entry -> entry.name.equals(name))
			.map(CardEntry::create)
			.orElseThrow(() -> new CardNotFoundException("card with name='" + name + "' could not be found"));
	}

	public static Minion createTokenByName(String name) {
		/* The token should be created without creating an instance of a card,
		 * because token are not created by playing a card in the first place.
		 * None the less if a token is bounced from the battlefield to hand, a
		 * cardSupplier must exist to create a card instance. */
		return find(cardEntry -> cardEntry.name.equals(name) && cardEntry instanceof MinionCardEntry)
			.map(cardEntry -> ((MinionCardEntry)cardEntry).minionFactory
				.create()
				.setCardSupplier(((MinionCardEntry)cardEntry)::create))
			.orElseThrow(() -> new CardNotFoundException("token with name='" + name + "' could not be found"));
	}

	/**
	 * This is the super-class for all card entries in this repository.
	 * @param <Self> return type for fluent methods.
	 * @param <Card> the card type this card entry will produce.
	 */
	private abstract static class CardEntry<Self extends CardEntry<Self,Card>, Card extends de.ralfhergert.hearthstone.game.model.Card> {

		private final int id;
		private final CardSet cardSet;
		private final Rarity rarity;
		private final HeroClass heroClass;
		private final int manaCost;
		private final String name;
		private final CardType cardType;
		private final ActionDiscovery<Card> actionDiscovery;

		private int overloadCost = 0;

		public CardEntry(int id, CardSet cardSet, Rarity rarity, HeroClass heroClass, int manaCost, String name, CardType cardType, ActionDiscovery<Card> actionDiscovery) {
			this.id = id;
			this.cardSet = cardSet;
			this.rarity = rarity;
			this.heroClass = heroClass;
			this.manaCost = manaCost;
			this.name = name;
			this.cardType = cardType;
			this.actionDiscovery = actionDiscovery;
		}

		public int getId() {
			return id;
		}

		public Self setOverloadCost(int overloadCost) {
			this.overloadCost = overloadCost;
			return (Self)this;
		}

		public final Card create() {
			return (Card)createInstance()
				.setManaCost(manaCost)
				.setName(name)
				.setCardType(cardType)
				.setActionDiscovery(actionDiscovery)
				.setOverloadCost(overloadCost);
		}

		protected abstract Card createInstance();

		@Override
		public String toString() {
			return "CardEntry{" +
				"id=" + id +
				", cardSet=" + cardSet +
				", rarity=" + rarity +
				", heroClass=" + heroClass +
				", manaCost=" + manaCost +
				", name='" + name + '\'' +
				", actionDiscovery=" + actionDiscovery +
				", overloadCost=" + overloadCost +
				'}';
		}
	}

	/**
	 * This is a card entry for a minion card.
	 */
	private static class MinionCardEntry extends CardEntry<MinionCardEntry,MinionCard> {

		private final MinionFactory minionFactory;

		public MinionCardEntry(int id, CardSet cardSet, Rarity rarity, HeroClass heroClass, int manaCost, String name, CardType cardType, MinionFactory minionFactory) {
			this(id, cardSet, rarity, heroClass, manaCost, name, cardType, minionFactory, new DefaultMinionCardActionDiscovery());
		}

		public MinionCardEntry(int id, CardSet cardSet, Rarity rarity, HeroClass heroClass, int manaCost, String name, CardType cardType, MinionFactory minionFactory, ActionDiscovery<MinionCard> actionDiscovery) {
			super(id, cardSet, rarity, heroClass, manaCost, name, cardType, actionDiscovery);
			this.minionFactory = minionFactory.setManaCost(manaCost).setMinionName(name);
		}

		@Override
		public MinionCard createInstance() {
			return new MinionCard(minionFactory).setId(getId());
		}
	}

	/**
	 * This is a card entry for an ability card.
	 */
	private static class AbilityCardEntry extends CardEntry<AbilityCardEntry,AbilityCard> {

		private final Effect effect;

		public AbilityCardEntry(int id, CardSet cardSet, Rarity rarity, HeroClass heroClass, int manaCost, String name, Effect effect) {
			this(id, cardSet, rarity, heroClass, manaCost, name, effect, new DefaultAbilityCardActionDiscovery());
		}

		public AbilityCardEntry(int id, CardSet cardSet, Rarity rarity, HeroClass heroClass, int manaCost, String name, Effect effect, ActionDiscovery<AbilityCard> actionDiscovery) {
			super(id, cardSet, rarity, heroClass, manaCost, name, CardType.Ability, actionDiscovery);
			this.effect = effect;
		}

		@Override
		public AbilityCard createInstance() {
			return new AbilityCard(effect).setId(getId());
		}
	}
}
