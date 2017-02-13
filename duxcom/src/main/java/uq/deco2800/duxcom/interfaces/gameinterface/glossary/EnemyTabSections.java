package uq.deco2800.duxcom.interfaces.gameinterface.glossary;


/**
 * Ticket #61 - Story/Lore and In Game glossary
 *
 * An enumeration that holds all of the Enemy sections for the glossary
 *
 * @author rhysmckenzie
 */
public enum EnemyTabSections {
    TANK("Tank", "Knights are often seen " +
            "The tank is notorious for its large battle-axe and even " +
            "larger build. They are recorded as being very protective " +
            "of friendly support units or units who are vulnerable to " +
            "enemy attacks.", "tankSection", "/enemies/tank.png"),

    SUPERTANK("Supertank", "This is a rare variety of the tank unit who " +
            "not only is stronger than the average tank but also much," +
            " much larger. Able to take a ridiculous amount of punishment," +
            " this unit is infamous for disrupting even the strongest " +
            "battle lines.", "supertankSection", "/enemies/super_tank.png"),

    DARKKNIGHT("Dark Knight", "The dark knight is recorded as a very " +
            "well-rounded unit, able to take and deliver reasonable " +
            "amounts of damage. This unit is known for its adaptability" +
            " within the heat of battle.", "darkKnightSection",
            "/enemies/dark_knight.png"),

    PALADIN("Paladin", "The paladin is a Knight who has specialised in" +
            " the removal of mages. Able to withstand magic from all but" +
            " the most experienced of mages, this unit is has been seen" +
            " charging into the thickest of fights, seeking out any and " +
            "all mages..", "paladinSection", "/enemies/paladin.png"),

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
            , "/enemies/archer.png"),

    MOUNTEDDARKKNIGHT("Mounted Dark Knight", "The Mounted Dark Knight has " +
            "spent countless days training and perfecting the art of " +
            "horseback combat. Seen atop demonic steeds this unit is often " +
            "the first in the fray, charging in with devastating effect.",
            "mountedDarkKnightSection", "/enemies/mounted_knight.png"),

    SUPPORT("Support", "The Support lives only to serve. Often shying away" +
            " from conflict of any kind, this unit will seek out and assist " +
            "any friendly units it can.", "supportSection",
            "/enemies/support.png"),

    BARD("Bard", "When the Bard enters the battle, all other units fight " +
            "with increased vigour at the sweet sound of the Bard’s " +
            "instruments. Seeking only to buff their fellow units the bard" +
            " is a notorious unsung hero of the battlefield.", "bardSection"
            , "/enemies/bard.png"),

    APOTHECARY("Apothecary", "The Apothecary has studied the arts of healing" +
            " their whole life and use their skills in the heat of combat. " +
            "Sworn to never harm any living thing the Apothecary will stride" +
            " into battle armed only with their salves and healing abilities."
            , "apothecarySection", "/enemies/apothecary.png"),

    ROGUE("Rogue", "The Rogue is the ultimate disruptive unit, able to sneak" +
            " behind enemies and strike at the weakest points of any battle" +
            " formation.", "RogueSection", "/enemies/rogue.png"),

    ASSASSIN("Assassin", "The Assassin lives only to kill. Seeking out " +
            "vulnerable targets the Assassin will strike before the enemy " +
            "even knows it is there.", "assassinSection",
            "/enemies/assassin.png"),

    THIEF("Thief", "The Thief is the living embodiment of Kleptomania. Able" +
            " to creep up on almost any unit and ‘retrieve’ what is most " +
            "valuable to them the Thief is often referred to as " +
            "“The Silent Inconvenience", "thiefSection", "/enemies/thief.png"),

    ELEMENTALMAGE("Elemental Mage", "The Elemental Mage is arguably the " +
            "most dangerous opponent on the battlefield as they can " +
            "literally control the battlefield. Able to call upon and " +
            "manipulate the elements the Elemental Mage can call upon their" +
            " powers with devastating results.", "elementalMageSection",
            "/enemies/elemental_mage.png"),

    SUMMONER("Summoner", "The Summoner has blurred the lines of reality " +
            "through decades of intensive studying of the arcane arts. This" +
            " has lead to Summoners being able to phase-shift though the " +
            "barriers between worlds and bring forth warriors from other " +
            "realities, making them one of the most dangerous units on any" +
            " battlefield.", "summonerSection", "/enemies/summoner.png"),

    DARKMAGE("Dark Mage", "The Dark Mage was once an Elemental Mage who, on " +
            "their own, wield incredible power. Such power however can " +
            "corrupt even the strongest of minds. This corruption lead the" +
            " Dark Mages down a terrible path, that left their bodies " +
            "twisted and their minds corrupt. Through this corruption though" +
            ", the Dark Mage can wield magic that is so dark and so terrible" +
            " that it can only be described as a power over death itself."
            , "darkMageSection", "/enemies/dark_mage.png"),

    RANGER("Ranger", "The Ranger is known for its patience. On its own, " +
            "the Ranger can lie in wait for months before it strikes, " +
            "waiting for the perfect opportunity to single out and eliminate" +
            " the weakest enemy of a group. It is this ability that makes " +
            "the Ranger such a valuable asset on the field.", "rangerSection"
            , "/enemies/ranger.png"),

    CROSSBOWMEN("Crossbowmen", "The Crossbowmen are a heavy hitting ranged " +
            "unit that is trained purely for war. Armed with deadly " +
            "crossbows they are able to take out even the toughest of foes " +
            "with only a few hits.", "crossbowmenSection",
            "/enemies/crossbowmen.png"),

    GRUNT("Grunt", "The Grunt is the bulk of any fighting force. Small and " +
            "cheap they are often used as the meat shield for any " +
            "self-respecting Knight.", "gruntSection", "/enemies/grunt.png"),

    BRUTE("Brute", "The Brute has an incomparable bloodlust that drives them" +
            " to charge headlong into battle with no regard for their own " +
            "well-being. This shockingly violent charge has been the " +
            "downfall of many Brute and their enemies alike.", "bruteSection"
            , "/enemies/brute.png"),

    FOX("Fox", "In times of peace the fox is often the pray for hunters and " +
            "foraging rangers, however in times of war the fox turns into a " +
            "bloodthirsty hunter/scavenger itself, able to kill weaker men.",
            "foxSection", "/enemies/fox.png"),

    WOLF("Wolf", "Even during times of peace wolves are formidable. If not " +
            "kept in check the population will boom and a large pack of " +
            "wolves can wipe out entire villages over time, slowly picking " +
            "of stragglers one by one. This is also the case on the field of" +
            " battle, one careless knight or archer who thinks themselves " +
            "safe is all the motivation a pack needs to descend on the field" +
            " and feast.", "wolfSection", "/enemies/wolf.png"),

    BEAR("Bear", "Bears are not often seen in times of peace, and are known " +
            "to attack humans even less. However when the land is ravaged by" +
            " war, the sounds and smells of battle are enough to drive bears" +
            " to madness, luring them out of their caves and turning them " +
            "into nigh unstoppable killing machines", "bearSection",
            "/enemies/bear.png"),

    LION("Lion", "The Lion was not documented until after the dark forces " +
            "came over the land, since then there have been numerous " +
            "accounts of lions killing and being killed. Lion are like " +
            "wolves in that they often hunt in packs, but Lions are also " +
            "quicker, smarter, and larger than wolves making them a more " +
            "formidable opponent on the battlefield and off.", "lionSection"
            , "/enemies/lion.png");

    GlossarySection glossarySection;

    /**
     * Constructor to retrieve inputs, stores a glossarySection
     *
     * @param name Name given to the section
     * @param text The Text of the section
     * @param id ID given to the section (Buttons will have same ID)
     * @param image The image that will be displayed for this section
     */
    EnemyTabSections(String name, String text, String id, String image) {
        this.glossarySection = new GlossarySection(name, text, id, image);
    }
}
