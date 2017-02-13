package uq.deco2800.duxcom.entities.heros;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameRenderer;
import uq.deco2800.duxcom.achievements.AchievementStatistics;
import uq.deco2800.duxcom.coop.exceptions.UnexpectedIncorrectGameState;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.enemies.listeners.DeathListener;
import uq.deco2800.duxcom.entities.heros.listeners.ActionPointListener;
import uq.deco2800.duxcom.entities.heros.listeners.HealthListener;
import uq.deco2800.duxcom.entities.heros.listeners.MovementListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wondertroy on 4/08/2016.
 */
public class HeroManager implements DeathListener {

    private static Logger logger = LoggerFactory.getLogger(HeroManager.class);

    private ArrayList<AbstractHero> heros = new ArrayList<>();
    private AbstractHero currentHero;
    private int heroIndex = 0;
    private boolean gameOver = false;
    
    /**
     * Returns the current active Hero.
     * @return currentHero.
     */
    public AbstractHero getCurrentHero() {
        return currentHero;
    }

    /**
     * Returns a list of all active Heroes.
     * @return List of all Heroes.
     */
    public List<AbstractHero> getHeroList() {
        return new ArrayList<>(heros);
    }

    /**
     * Returns the next active hero.
     * @return The next hero.
     */
    public AbstractHero getNextHero() {
        int nextIndex = heroIndex;
        if (currentHero != null) {
            currentHero.disableListeners();
        }
        nextIndex++;
        if (nextIndex >= heros.size()) {
            nextIndex = 0;
        }
        return heros.get(nextIndex);
    }

    /**
     * Sets the current active hero to be that with the given name.
     * @param name The name of the hero that will be made the current hero.
     */
    public void setHero(String name){
        if (currentHero != null) {
            currentHero.disableListeners();
        }
        for(AbstractHero hero : heros){
            if(hero.getName().equals(name)){
                currentHero = hero;
                currentHero.enableListeners();
                return;
            }
        }
        throw new UnexpectedIncorrectGameState("Could not switch to the hero you requested, did not exist!");
    }

    /**
     * Set the current active hero given an index.
     * @param i Index of hero.
     */
    public void setHero(int i) {
        if (currentHero != null) {
            currentHero.disableListeners();
        }
        heroIndex = i;
        currentHero = heros.get(i);
        currentHero.enableListeners();
    }

    /**
     * sets the hero who's turn it is to be "current"
     */
    public void nextHero() {
        if (gameOver) {
            return;
        }
        if (currentHero != null) {
            currentHero.disableListeners();
        }
        heroIndex++;
        if (heroIndex >= heros.size()) {
            heroIndex = 0;
        }
        currentHero = heros.get(heroIndex);
        currentHero.onTurn();
    }

    /**
     * Adds a hero to be active in the list.
     *
     * @param hero
     */
    public void addHero(AbstractHero hero) {
        heros.add(hero);
    }

    /**
     * Adds healthListener as a listener for all heroes.
     *
     * @param healthListener the health listener to be added.
     */
    public void addHealthListenerAllHeroes(HealthListener healthListener) {
        for (AbstractHero hero : heros) {
            hero.addHealthListener(healthListener);
        }
    }

    /**
     * Adds actionPointListener as a listener for all heroes.
     *
     * @param actionPointListener the action point listener to be added.
     */
    public void addActionPointListenerAllHeroes(ActionPointListener actionPointListener) {
        for (AbstractHero hero : heros) {
            hero.addActionPointListener(actionPointListener);
        }
    }

    /**
     * Adds deathListener as a listener for all heroes.
     *
     * @param deathListener the death listener to be added.
     */
    public void addDeathListenerAllHeroes(DeathListener deathListener) {
        for (AbstractHero hero : heros) {
            hero.addDeathListener(deathListener);
        }
    }

    public void addMovementListenerAllHeroes(MovementListener movementListener) {
        for (AbstractHero hero : heros) {
            hero.addMovementListener(movementListener);
        }    }

    /**
     * Enables the listeners for the first hero to move.
     * <p>
     * The current turn hero's listeners are usually enabled on turn change, so the very first hero needs to have their
     * listeners manually enabled. This allows listeners (such as the UI health bar) to do an initial update.
     */
    public void initialiseStatusListeners() {
        if (currentHero != null) {
            currentHero.enableListeners(); //Sends notification to listeners to do an initial update.
        }
    }

    /**
     * Checks to see if a hero of a given type is present
     *
     * @param type
     *            - hero type being looked for
     * @return true if at least one hero of the specified type exists, else
     *         false
     * @require type != null
     * Thomas Bricknell - 16/10/16
     */
    public boolean hasType(HeroType type) {
        for (AbstractHero h : this.heros) {
            if (h.getHeroType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * On the death of the Hero.
     * Hero will be removed. DeathListeners will be disabled. 
     * Game Over set if no heroes are left.
     */
    @Override
    public void onDeath(AbstractCharacter character) {
        AbstractHero hero = (AbstractHero) character;
        AchievementStatistics.addDeaths(1);
        AchievementStatistics.save();
        hero.disableListeners();
        heros.remove(hero);
        if(heros.isEmpty()) {
            gameOver = true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Delay interrupted");
                Platform.exit();
                Thread.currentThread().interrupt();
            }
            GameRenderer.setGameOver(true);
        }
    }

    /**
     * Get the hero from a UUID
     * @param heroUUID the heroes UUID
     */
    public AbstractHero getHero(String heroUUID) {
        for(AbstractHero hero : heros){
            if(hero.getName().equals(heroUUID)){
                return hero;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param character
     */
    public void removeHero(AbstractCharacter character){
    	AbstractHero hero = (AbstractHero) character;
    	hero.disableListeners();
    	heros.remove(hero);
    }

    public int getHeroIndex() {
        return heroIndex;
    }
}
