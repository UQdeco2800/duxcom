package uq.deco2800.duxcom.coop;

import uq.deco2800.duxcom.coop.exceptions.SquadException;
import uq.deco2800.duxcom.entities.heros.AbstractHero;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Represents a single in game squad.
 *
 * Created by liamdm on 19/10/2016.
 */
public class Squad {
    private int currentHero = -1;
    private String squadOwner;
    private HashMap<String, AbstractHero> addable;
    private LinkedList<String> dead;
    private LinkedList<String> heroes;

    public Squad(String squadOwner) {
        this.squadOwner = squadOwner;
        dead = new LinkedList<>();
        heroes = new LinkedList<>();
        addable = new HashMap<>();
    }

    /**
     * Reset to the squad start index
     */
    public String seekStart(){
        if(allHeroKill()){
            throw new SquadException("All heroes in the squad owned by "+squadOwner+" are dead!");
        }

        currentHero = 0;

        if(dead.contains(heroes.get(0))) {
            return nextHero();
        }

        return heroes.get(0);
    }

    /**
     * Get the heroes to add to the map
     */
    public LinkedList<AbstractHero> getHeroesToAdd(){
        LinkedList<AbstractHero> ordered = new LinkedList<>();
        for(String hero : heroes){
            ordered.add(addable.get(hero));
        }
        return ordered;
    }

    /**
     * Adds a hero to this squad
     * @param heroName the hero name to add
     */
    public void addHero(String heroName, AbstractHero add){
        if(heroes.size() == 0){
            currentHero = 0;
        }
        heroes.add(heroName);
        add.setName(heroName);

        addable.put(heroName, add);
    }


    /**
     * Returns the next hero's name as a string
     * or null if there is not next.
     */
    public String nextHero(){
        if(allHeroKill()){
            throw new SquadException("All heroes in the squad owned by "+squadOwner+" are dead!");
        }

        String target;
        do {
            ++currentHero;
            if(currentHero >= heroes.size()){
                resetNext();
                return null;
            }

            target = heroes.get(currentHero);
        } while (dead.contains(target));

        return target;
    }

    /**
     * Reset the next squad hero selector
     */
    private void resetNext(){
        currentHero = 0;
    }

    /**
     * Returns true if the given hero is in this squad
     * @param name the name of the hero
     */
    public boolean isInSquad(String name){
        return heroes.contains(name);
    }

    /**
     * Marks the given hero as dead
     */
    public void killHero(String name){
        if(dead.contains(name)){
            throw new SquadException("Hero with name " + name + " was already dead!");
        }
        dead.add(name);
    }

    /**
     * Get the current hero
     * @return
     */
    public String getCurrentHero(){
        return heroes.get(currentHero);
    }

    /**
     * Returns true if all the heroes are kill
     */
    public boolean allHeroKill(){
        return heroes.size() == dead.size();
    }

}
