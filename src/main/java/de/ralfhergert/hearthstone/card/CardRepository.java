package de.ralfhergert.hearthstone.card;

import de.ralfhergert.hearthstone.game.minion.MinionFactory;
import de.ralfhergert.hearthstone.game.model.Card;
import de.ralfhergert.hearthstone.game.model.CardSet;
import de.ralfhergert.hearthstone.game.model.HeroClass;
import de.ralfhergert.hearthstone.game.model.MinionCard;
import de.ralfhergert.hearthstone.game.model.Rarity;

import java.util.Arrays;
import java.util.List;

/**
 * This repository hold all known cards.
 */
public final class CardRepository {

	private static List<CardEntry<? extends Card>> cards = Arrays.asList(
		new MinionCardEntry(273, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 0, "Wisp", new MinionFactory().setPower(1).setHitPoints(1)),
		new MinionCardEntry(346, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Mogu'shan Warden", new MinionFactory().setPower(1).setHitPoints(7).setHasTaunt(true))
/* Cards which effects are not yet implemented.
264, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 12, "Mountain Giant", new MinionFactory().setPower(8).setHitPoints(8) "Costs (1) less for each other card in your hand."
496, CardSet.Classic, Rarity.Epic, HeroClass.Mage, 10, "Pyroblast", new Effect() "Deal 10 damage."
474, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 10, "Deathwing", new MinionFactory().setPower(12).setHitPoints(12) "Battlecry: Destroy all other minions and discard your hand."
614, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 10, "Sea Giant", new MinionFactory().setPower(8).setHitPoints(8) "Costs (1) less for each other minion on the battlefield."
303, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 9, "Alexstrasza", new MinionFactory().setPower(8).setHitPoints(8) "Battlecry: Set a hero's remaining health to 15."
605, CardSet.Classic, Rarity.Legendary, HeroClass.Druid, 9, "Cenarius", new MinionFactory().setPower(5).setHitPoints(8) "Choose one: Give your other minions +2/+2; or summon two 2/2 Treants with taunt."
194, CardSet.Classic, Rarity.Legendary, HeroClass.Hunter, 9, "King Krush", new MinionFactory().setPower(8).setHitPoints(8) "Charge"
482, CardSet.Classic, Rarity.Legendary, HeroClass.Warlock, 9, "Lord Jaraxxus", new MinionFactory().setPower(3).setHitPoints(15)
241, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 9, "Malygos", new MinionFactory().setPower(4).setHitPoints(12)
285, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 9, "Nozdormu", new MinionFactory().setPower(8).setHitPoints(8)
432, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 9, "Onyxia", new MinionFactory().setPower(8).setHitPoints(8)
495, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 9, "Ysera", new MinionFactory().setPower(4).setHitPoints(12)
506, CardSet.Classic, Rarity.Epic, HeroClass.Paladin, 8, "Lay on Hands", new Effect()
398, CardSet.Classic, Rarity.Epic, HeroClass.Warlock, 8, "Twisting Nether", new Effect()
335, CardSet.Classic, Rarity.Legendary, HeroClass.Shaman, 8, "Al'Akir the Windlord", new MinionFactory().setPower(3).setHitPoints(5)
643, CardSet.Classic, Rarity.Legendary, HeroClass.Warrior, 8, "Grommash Hellscream", new MinionFactory().setPower(4).setHitPoints(9)
18, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 8, "Gruul", new MinionFactory().setPower(7).setHitPoints(7)
391, CardSet.Classic, Rarity.Legendary, HeroClass.Paladin, 8, "Tirion Fordring", new MinionFactory().setPower(6).setHitPoints(6)
278, CardSet.Classic, Rarity.Epic, HeroClass.Hunter, 7, "Gladiator's Longbow", new WeaponFactory.setAttack(5).setDurability(2)
96, CardSet.Classic, Rarity.Epic, HeroClass.Warrior, 7, "Gorehowl", new WeaponFactory.setAttack(7).setDurability(1)
34, CardSet.Classic, Rarity.Epic, HeroClass.Druid, 7, "Ancient of Lore", new MinionFactory().setPower(5).setHitPoints(5)
242, CardSet.Classic, Rarity.Epic, HeroClass.Druid, 7, "Ancient of War", new MinionFactory().setPower(5).setHitPoints(5)
220, CardSet.Classic, Rarity.Legendary, HeroClass.Mage, 7, "Archmage Antonidas", new MinionFactory().setPower(5).setHitPoints(7)
539, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 7, "Baron Geddon", new MinionFactory().setPower(7).setHitPoints(5)
228, CardSet.Classic, Rarity.Legendary, HeroClass.Priest, 7, "Prophet Velen", new MinionFactory().setPower(7).setHitPoints(7)
518, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 7, "Ravenholdt Assassin", new MinionFactory().setPower(7).setHitPoints(5)
142, CardSet.Classic, Rarity.Epic, HeroClass.Paladin, 6, "Avenging Wrath", new Effect()
276, CardSet.Classic, Rarity.Rare, HeroClass.Mage, 6, "Blizzard", new Effect()
457, CardSet.Classic, Rarity.Rare, HeroClass.Priest, 6, "Holy Fire", new Effect()
573, CardSet.Classic, Rarity.Rare, HeroClass.Warlock, 6, "Siphon Soul", new Effect()
463, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 6, "Argent Commander", new MinionFactory().setPower(4).setHitPoints(2)
147, CardSet.Classic, Rarity.Epic, HeroClass.Priest, 6, "Cabal Shadow Priest", new MinionFactory().setPower(4).setHitPoints(5)
498, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 6, "Cairne Bloodhoof", new MinionFactory().setPower(4).setHitPoints(5)
598, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 6, "Frost Elemental", new MinionFactory().setPower(5).setHitPoints(5)
131, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 6, "Gadgetzan Auctioneer", new MinionFactory().setPower(4).setHitPoints(4)
39, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 6, "Hogger", new MinionFactory().setPower(4).setHitPoints(4)
203, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 6, "Illidan Stormrage", new MinionFactory().setPower(7).setHitPoints(5)
562, CardSet.Classic, Rarity.Epic, HeroClass.Rogue, 6, "Kidnapper", new MinionFactory().setPower(5).setHitPoints(3)
138, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 6, "Priestess of Elune", new MinionFactory().setPower(5).setHitPoints(4)
8, CardSet.Classic, Rarity.Rare, HeroClass.Hunter, 6, "Savannah Highmane", new MinionFactory().setPower(6).setHitPoints(5)
221, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 6, "Sunwalker", new MinionFactory().setPower(4).setHitPoints(5)
232, CardSet.Classic, Rarity.Common, HeroClass.Priest, 6, "Temple Enforcer", new MinionFactory().setPower(6).setHitPoints(6)
179, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 6, "The Beast", new MinionFactory().setPower(9).setHitPoints(7)
396, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 6, "The Black Knight", new MinionFactory().setPower(4).setHitPoints(5)
675, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 6, "Windfury Harpy", new MinionFactory().setPower(4).setHitPoints(5)
172, CardSet.Classic, Rarity.Epic, HeroClass.Shaman, 5, "Doomhammer", new WeaponFactory.setAttack(2).setDurability(8)
670, CardSet.Classic, Rarity.Epic, HeroClass.Warlock, 5, "Bane of Doom", new Effect()
7, CardSet.Classic, Rarity.Rare, HeroClass.Paladin, 5, "Blessed Champion", new Effect()
297, CardSet.Classic, Rarity.Epic, HeroClass.Warrior, 5, "Brawl", new Effect()
114, CardSet.Classic, Rarity.Rare, HeroClass.Hunter, 5, "Explosive Shot", new Effect()
237, CardSet.Classic, Rarity.Epic, HeroClass.Druid, 5, "Force of Nature", new Effect()
355, CardSet.Classic, Rarity.Rare, HeroClass.Paladin, 5, "Holy Wrath", new Effect()
120, CardSet.Classic, Rarity.Rare, HeroClass.Druid, 5, "Nourish", new Effect()
464, CardSet.Classic, Rarity.Rare, HeroClass.Druid, 5, "Starfall", new Effect()
597, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 5, "Abomination", new MinionFactory().setPower(4).setHitPoints(4)
73, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 5, "Big Game Hunter", new MinionFactory().setPower(4).setHitPoints(2)
267, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 5, "Captain Greenskin", new MinionFactory().setPower(5).setHitPoints(4)
507, CardSet.Classic, Rarity.Rare, HeroClass.Warlock, 5, "Doomguard", new MinionFactory().setPower(5).setHitPoints(7)
587, CardSet.Classic, Rarity.Common, HeroClass.Druid, 5, "Druid of the Claw", new MinionFactory().setPower(4).setHitPoints(4)
124, CardSet.Classic, Rarity.Epic, HeroClass.Shaman, 5, "Earth Elemental", new MinionFactory().setPower(7).setHitPoints(8)
450, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 5, "Faceless Manipulator", new MinionFactory().setPower(3).setHitPoints(3)
476, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 5, "Fen Creeper", new MinionFactory().setPower(3).setHitPoints(6)
602, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 5, "Harrison Jones", new MinionFactory().setPower(5).setHitPoints(4)
674, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 5, "Leeroy Jenkins", new MinionFactory().setPower(6).setHitPoints(2)
648, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 5, "Silver Hand Knight", new MinionFactory().setPower(4).setHitPoints(4)
627, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 5, "Spiteful Smith", new MinionFactory().setPower(4).setHitPoints(6)
389, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 5, "Stampeding Kodo", new MinionFactory().setPower(3).setHitPoints(5)
338, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 5, "Stranglethorn Tiger", new MinionFactory().setPower(5).setHitPoints(5)
509, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 5, "Venture Co. Mercenary", new MinionFactory().setPower(7).setHitPoints(6)
266, CardSet.Classic, Rarity.Rare, HeroClass.Druid, 4, "Bite", new Effect()
244, CardSet.Classic, Rarity.Rare, HeroClass.Rogue, 4, "Blade Flurry", new Effect()
26, CardSet.Classic, Rarity.Common, HeroClass.Mage, 4, "Cone of Cold", new Effect()
249, CardSet.Classic, Rarity.Rare, HeroClass.Priest, 4, "Mass Dispel", new Effect()
301, CardSet.Classic, Rarity.Epic, HeroClass.Priest, 4, "Mindgames", new Effect()
345, CardSet.Classic, Rarity.Rare, HeroClass.Warrior, 4, "Mortal Strike", new Effect()
442, CardSet.Classic, Rarity.Rare, HeroClass.Priest, 4, "Shadow Madness", new Effect()
673, CardSet.Classic, Rarity.Rare, HeroClass.Warlock, 4, "Shadowflame", new Effect()
311, CardSet.Classic, Rarity.Common, HeroClass.Druid, 4, "Soul of the Forest", new Effect()
572, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Ancient Brewmaster", new MinionFactory().setPower(5).setHitPoints(4)
176, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 4, "Ancient Mage", new MinionFactory().setPower(2).setHitPoints(5)
504, CardSet.Classic, Rarity.Common, HeroClass.Warrior, 4, "Arathi Weaponsmith", new MinionFactory().setPower(3).setHitPoints(3)
656, CardSet.Classic, Rarity.Rare, HeroClass.Priest, 4, "Auchenai Soulpriest", new MinionFactory().setPower(3).setHitPoints(5)
140, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Cult Master", new MinionFactory().setPower(4).setHitPoints(2)
128, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Dark Iron Dwarf", new MinionFactory().setPower(4).setHitPoints(4)
542, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 4, "Defender of Argus", new MinionFactory().setPower(2).setHitPoints(3)
261, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Dread Corsair", new MinionFactory().setPower(3).setHitPoints(3)
125, CardSet.Classic, Rarity.Rare, HeroClass.Mage, 4, "Ethereal Arcanist", new MinionFactory().setPower(3).setHitPoints(3)
459, CardSet.Classic, Rarity.Rare, HeroClass.Druid, 4, "Keeper of the Grove", new MinionFactory().setPower(2).setHitPoints(2)
192, CardSet.Classic, Rarity.Common, HeroClass.Priest, 4, "Lightspawn", new MinionFactory().setPower(0).setHitPoints(5)
127, CardSet.Classic, Rarity.Rare, HeroClass.Rogue, 4, "Master of Disguise", new MinionFactory().setPower(4).setHitPoints(4)
346, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Mogu'shan Warden", new MinionFactory().setPower(1).setHitPoints(7)
402, CardSet.Classic, Rarity.Epic, HeroClass.Warlock, 4, "Pit Lord", new MinionFactory().setPower(5).setHitPoints(6)
634, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Silvermoon Guardian", new MinionFactory().setPower(3).setHitPoints(3)
42, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 4, "Spellbreaker", new MinionFactory().setPower(4).setHitPoints(3)
566, CardSet.Classic, Rarity.Common, HeroClass.Warlock, 4, "Summoning Portal", new MinionFactory().setPower(0).setHitPoints(4)
360, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 4, "Twilight Drake", new MinionFactory().setPower(4).setHitPoints(1)
523, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 4, "Violet Teacher", new MinionFactory().setPower(3).setHitPoints(5)
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
428, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Acolyte of Pain", new MinionFactory().setPower(1).setHitPoints(3)
425, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Alarm-o-Bot", new MinionFactory().setPower(0).setHitPoints(3)
23, CardSet.Classic, Rarity.Rare, HeroClass.Paladin, 3, "Aldor Peacekeeper", new MinionFactory().setPower(3).setHitPoints(3)
97, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Arcane Golem", new MinionFactory().setPower(4).setHitPoints(4)
75, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 3, "Blood Knight", new MinionFactory().setPower(3).setHitPoints(3)
424, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Coldlight Seer", new MinionFactory().setPower(2).setHitPoints(3)
212, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Demolisher", new MinionFactory().setPower(1).setHitPoints(4)
557, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Earthen Ring Farseer", new MinionFactory().setPower(3).setHitPoints(3)
3, CardSet.Classic, Rarity.Legendary, HeroClass.Rogue, 3, "Edwin VanCleef", new MinionFactory().setPower(2).setHitPoints(2)
625, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Emperor Cobra", new MinionFactory().setPower(2).setHitPoints(3)
236, CardSet.Classic, Rarity.Rare, HeroClass.Warlock, 3, "Felguard", new MinionFactory().setPower(3).setHitPoints(5)
610, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Flesheating Ghoul", new MinionFactory().setPower(2).setHitPoints(3)
69, CardSet.Classic, Rarity.Rare, HeroClass.Warrior, 3, "Frothing Berserker", new MinionFactory().setPower(2).setHitPoints(4)
386, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Harvest Golem", new MinionFactory().setPower(2).setHitPoints(3)
178, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Imp Master", new MinionFactory().setPower(1).setHitPoints(5)
209, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Injured Blademaster", new MinionFactory().setPower(4).setHitPoints(7)
500, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Ironbeak Owl", new MinionFactory().setPower(2).setHitPoints(1)
392, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Jungle Panther", new MinionFactory().setPower(4).setHitPoints(2)
373, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 3, "King Mukla", new MinionFactory().setPower(5).setHitPoints(5)
411, CardSet.Classic, Rarity.Rare, HeroClass.Mage, 3, "Kirin Tor Mage", new MinionFactory().setPower(4).setHitPoints(3)
613, CardSet.Classic, Rarity.Rare, HeroClass.Shaman, 3, "Mana Tide Totem", new MinionFactory().setPower(0).setHitPoints(3)
368, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Mind Control Tech", new MinionFactory().setPower(3).setHitPoints(3)
222, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 3, "Murloc Warleader", new MinionFactory().setPower(3).setHitPoints(3)
157, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 3, "Questing Adventurer", new MinionFactory().setPower(2).setHitPoints(2)
95, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Raging Worgen", new MinionFactory().setPower(3).setHitPoints(3)
475, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Scarlet Crusader", new MinionFactory().setPower(3).setHitPoints(1)
286, CardSet.Classic, Rarity.Rare, HeroClass.Rogue, 3, "SI:7 Agent", new MinionFactory().setPower(3).setHitPoints(3)
324, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 3, "Southsea Captain", new MinionFactory().setPower(3).setHitPoints(3)
477, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Tauren Warrior", new MinionFactory().setPower(2).setHitPoints(3)
265, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 3, "Thrallmar Farseer", new MinionFactory().setPower(2).setHitPoints(3)
245, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 3, "Tinkmaster Overspark", new MinionFactory().setPower(3).setHitPoints(3)
51, CardSet.Classic, Rarity.Common, HeroClass.Shaman, 3, "Unbound Elemental", new MinionFactory().setPower(2).setHitPoints(4)
119, CardSet.Classic, Rarity.Rare, HeroClass.Warlock, 3, "Void Terror", new MinionFactory().setPower(3).setHitPoints(3)
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
641, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Amani Berserker", new MinionFactory().setPower(2).setHitPoints(3)
153, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Ancient Watcher", new MinionFactory().setPower(4).setHitPoints(5)
191, CardSet.Classic, Rarity.Common, HeroClass.Paladin, 2, "Argent Protector", new MinionFactory().setPower(2).setHitPoints(2)
644, CardSet.Classic, Rarity.Rare, HeroClass.Warrior, 2, "Armorsmith", new MinionFactory().setPower(1).setHitPoints(4)
525, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 2, "Bloodmage Thalnos", new MinionFactory().setPower(1).setHitPoints(1)
637, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Bloodsail Raider", new MinionFactory().setPower(2).setHitPoints(3)
612, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Crazed Alchemist", new MinionFactory().setPower(2).setHitPoints(2)
328, CardSet.Classic, Rarity.Common, HeroClass.Warrior, 2, "Cruel Taskmaster", new MinionFactory().setPower(2).setHitPoints(2)
417, CardSet.Classic, Rarity.Common, HeroClass.Rogue, 2, "Defias Ringleader", new MinionFactory().setPower(2).setHitPoints(2)
305, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Dire Wolf Alpha", new MinionFactory().setPower(2).setHitPoints(2)
467, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 2, "Doomsayer", new MinionFactory().setPower(0).setHitPoints(7)
213, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Faerie Dragon", new MinionFactory().setPower(3).setHitPoints(2)
422, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Knife Juggler", new MinionFactory().setPower(2).setHitPoints(2)
117, CardSet.Classic, Rarity.Rare, HeroClass.Priest, 2, "Lightwell", new MinionFactory().setPower(0).setHitPoints(5)
395, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Loot Hoarder", new MinionFactory().setPower(2).setHitPoints(1)
456, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 2, "Lorewalker Cho", new MinionFactory().setPower(0).setHitPoints(4)
80, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Mad Bomber", new MinionFactory().setPower(3).setHitPoints(2)
67, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Mana Addict", new MinionFactory().setPower(1).setHitPoints(3)
197, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Mana Wraith", new MinionFactory().setPower(2).setHitPoints(2)
584, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Master Swordsmith", new MinionFactory().setPower(1).setHitPoints(3)
339, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 2, "Millhouse Manastorm", new MinionFactory().setPower(4).setHitPoints(4)
19, CardSet.Classic, Rarity.Legendary, HeroClass.Neutral, 2, "Nat Pagle", new MinionFactory().setPower(0).setHitPoints(4)
14, CardSet.Classic, Rarity.Epic, HeroClass.Rogue, 2, "Patient Assassin", new MinionFactory().setPower(1).setHitPoints(1)
54, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Pint-Sized Summoner", new MinionFactory().setPower(2).setHitPoints(2)
279, CardSet.Classic, Rarity.Common, HeroClass.Hunter, 2, "Scavenging Hyena", new MinionFactory().setPower(2).setHitPoints(2)
4, CardSet.Classic, Rarity.Common, HeroClass.Mage, 2, "Sorcerer's Apprentice", new MinionFactory().setPower(3).setHitPoints(2)
372, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Sunfury Protector", new MinionFactory().setPower(2).setHitPoints(3)
25, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 2, "Wild Pyromancer", new MinionFactory().setPower(3).setHitPoints(2)
247, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 2, "Youthful Brewmaster", new MinionFactory().setPower(3).setHitPoints(2)
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
577, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Abusive Sergeant", new MinionFactory().setPower(1).setHitPoints(1)
57, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Angry Chicken", new MinionFactory().setPower(1).setHitPoints(1)
473, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Argent Squire", new MinionFactory().setPower(1).setHitPoints(1)
196, CardSet.Classic, Rarity.Common, HeroClass.Warlock, 1, "Blood Imp", new MinionFactory().setPower(0).setHitPoints(1)
453, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Bloodsail Corsair", new MinionFactory().setPower(1).setHitPoints(2)
129, CardSet.Classic, Rarity.Common, HeroClass.Shaman, 1, "Dust Devil", new MinionFactory().setPower(3).setHitPoints(1)
85, CardSet.Classic, Rarity.Common, HeroClass.Warlock, 1, "Flame Imp", new MinionFactory().setPower(3).setHitPoints(2)
660, CardSet.Classic, Rarity.Epic, HeroClass.Neutral, 1, "Hungry Crab", new MinionFactory().setPower(1).setHitPoints(2)
513, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Leper Gnome", new MinionFactory().setPower(1).setHitPoints(1)
436, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Lightwarden", new MinionFactory().setPower(1).setHitPoints(2)
263, CardSet.Classic, Rarity.Common, HeroClass.Mage, 1, "Mana Wyrm", new MinionFactory().setPower(1).setHitPoints(3)
420, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Murloc Tidecaller", new MinionFactory().setPower(1).setHitPoints(2)
483, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Secretkeeper", new MinionFactory().setPower(1).setHitPoints(2)
24, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Shieldbearer", new MinionFactory().setPower(0).setHitPoints(4)
103, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Southsea Deckhand", new MinionFactory().setPower(2).setHitPoints(1)
112, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Worgen Infiltrator", new MinionFactory().setPower(2).setHitPoints(1)
629, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 1, "Young Dragonhawk", new MinionFactory().setPower(1).setHitPoints(1)
123, CardSet.Classic, Rarity.Rare, HeroClass.Neutral, 1, "Young Priestess", new MinionFactory().setPower(2).setHitPoints(1)
38, CardSet.Classic, Rarity.Common, HeroClass.Priest, 0, "Circle of Healing", new Effect()
366, CardSet.Classic, Rarity.Common, HeroClass.Warrior, 0, "Inner Rage", new Effect()
364, CardSet.Classic, Rarity.Epic, HeroClass.Rogue, 0, "Preparation", new Effect()
550, CardSet.Classic, Rarity.Common, HeroClass.Rogue, 0, "Shadowstep", new Effect()
544, CardSet.Classic, Rarity.Common, HeroClass.Priest, 0, "Silence", new Effect()
273, CardSet.Classic, Rarity.Common, HeroClass.Neutral, 0, "Wisp", new MinionFactory().setPower(1).setHitPoints(1)
*/
	);

	private CardRepository() { /* no instantiation necessary */ }

	public static Card createById(int id) {
		for (CardEntry<? extends Card> cardEntry : cards) {
			if (cardEntry.id == id) {
				return cardEntry.create();
			}
		}
		return null;
	}

	/**
	 * This is the super-class for all card entries in this repository.
	 * @param <Card> the card type this card entry will produce.
	 */
	private abstract static class CardEntry<Card> {

		private final int id;
		private final CardSet cardSet;
		private final Rarity rarity;
		private final HeroClass heroClass;
		private final int manaCost;
		private final String name;

		public CardEntry(int id, CardSet cardSet, Rarity rarity, HeroClass heroClass, int manaCost, String name) {
			this.id = id;
			this.cardSet = cardSet;
			this.rarity = rarity;
			this.heroClass = heroClass;
			this.manaCost = manaCost;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public abstract Card create();
	}

	/**
	 * This is a card entry for a minion card.
	 */
	private static class MinionCardEntry extends CardEntry<MinionCard> {

		private final MinionFactory minionFactory;

		public MinionCardEntry(int id, CardSet cardSet, Rarity rarity, HeroClass heroClass, int manaCost, String name, MinionFactory minionFactory) {
			super(id, cardSet, rarity, heroClass, manaCost, name);
			this.minionFactory = minionFactory.setManaCost(manaCost).setMinionName(name);
		}

		@Override
		public MinionCard create() {
			return new MinionCard(minionFactory).setId(getId());
		}
	}
}
