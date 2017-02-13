package uq.deco2800.duxcom.interfaces.gameinterface.glossary;

/**
 * Ticket #61 - Story/Lore and In Game glossary
 *
 * An enumeration that holds all of the Hero sections for the glossary
 *
 * @author rhysmckenzie
 */
public enum HeroTabSections {
    KNIGHT("Knight", "Knights are often seen " +
            "as the wandering heroes of the land, wielding a variety of " +
            "weapons for a variety of situations a knight is useful in " +
            "almost every situation. With heavy armour and a selection " +
            "of weapons knights are often seen committing themselves to " +
            "tougher foes, tying them down in skillful combat while their " +
            "allies strike from every angle. Knights often fight for their " +
            "houses, bearing its colours and badge on their armour and " +
            "shields as they fight for glory and honour. There are some " +
            "knights however who do not fight for their house, or for " +
            "glory and honour but purely for the rush of battle, these " +
            "knights quickly found there calling in the darkness and now " +
            "spend their life reeking havoc and spilling blood wherever " +
            "they go.", "knightSection", "/heroes/knight/knight.png"),

    CAVALIER("Cavalier", "The mounted Knight, or Cavalier as they are " +
            "often called, has earned their renown by being practiced " +
            "in hit & run tactics. Able to charge headlong into any foe " +
            "and strike down all who stand in their way, before " +
            "retreating to a safe distance, reeling around and charging " +
            "again. This method of combat, though considered cowardly " +
            "by some, has proven very effective in defeating much " +
            "larger armies and has become widely adopted by most " +
            "noble houses. Much like standard knights however, the " +
            "call of battle and blood has become to much for many " +
            "Cavaliers and there has been reports of Dark Knights " +
            "riding demonic steeds into battle.", "cavalierSection"
            , "/heroes/cavalier/cavalier_iron_lance.png"),

    PRIEST("Priest", "Priests have devoted themselves completely to " +
            "the gods, surrendering their life so that they may help " +
            "others live theirs to the fullest. Often keeping their " +
            "distance from the thick of battle, they spend most of " +
            "their lives helping the sick, wounded, and dying. Many " +
            "a time has a priest been called a miracle worker by a " +
            "dying knight. Most Priests stay with their order and " +
            "only go where needed, often being called upon by the " +
            "noble houses, however some priests have left the order " +
            "to help any and all. These priests believe that their " +
            "order was not helping all that they could and answered " +
            "only to the beck and call of the highest of noble houses.",
            "priestSection", "/heroes/Priest/priest_cloak_potiongreen.png"),

    ROGUE("Rogue", "Often considered a nuisance both on the battlefield " +
            "and off, rogues fight only for gold. Usually born and raised " +
            "on the streets, learning their unique set of skills through " +
            "thievery and deceit, rogues are used on the battlefield to " +
            "harass enemies, weakening the enemies resolve before " +
            "delivering a final, fatal blow. Rogues often make use of " +
            "daggers and poisons on the battlefield, allowing them to " +
            "strike quickly and leave before the enemy even knows they " +
            "were there.", "rogueSection",
            "/heroes/rogue/rogue_leather_dagger.png"),

    ARCHER("Archer", "Archers are praised for their eyesight, " +
            "with each archer being gifted with the ability to " +
            "see further than almost any man. Armed with a bow, " +
            "the archer is able to thin large hordes down to a single " +
            "man or even wiping them out completely. Despite this " +
            "deadly accuracy that archers are famous for, when it comes " +
            "to hand-to-hand combat the archer is not so adept with " +
            "many archers not carrying a sword or dagger to allow them " +
            "to be light on their feet. Archers are often looked down " +
            "upon by most knights within their noble houses as once " +
            "the battle turns into a bloody melee, an archers arrow " +
            "is as likely to hit a friend as it is a foe.", "archerSection"
            , "/heroes/archer.png"),

    WARLOCK("Warlock", "Warlocks are considered dangerous and untrustworthy" +
            " by most of the land, spending a good majority of their life" +
            " transforming the bodies and minds to become the powerful " +
            "forces of nature they are. With a lifespan 10 times that of " +
            "a normal human Warlocks spend most of their time expanding " +
            "their already immense knowledge through reading and " +
            "experimentation. This immense knowledge and power however is " +
            "often crucial on a battlefield where some Warlocks are found " +
            "on very rare occasions. Warlocks also fight to preserve " +
            "themselves, with their numbers declining with every passing " +
            "year it has been rumoured that they have begun performing" +
            " experiments on human subjects, dead or alive, willing or no.",
            "warlockSection", "/heroes/warlock/warlock_robe_staffyellow.png");

    GlossarySection glossarySection;

    /**
     * Constructor to retrieve inputs
     *
     * @param name Name given to the section
     * @param text The text to be displayed about the section
     * @param id ID given to the section (Buttons will have same ID)
     * @param image The image that will be displayed for this section
     */
    HeroTabSections(String name, String text, String id, String image) {
        glossarySection = new GlossarySection(name, text, id, image);
    }

}
