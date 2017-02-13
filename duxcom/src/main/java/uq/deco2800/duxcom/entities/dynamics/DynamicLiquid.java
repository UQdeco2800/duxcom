package uq.deco2800.duxcom.entities.dynamics;

import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.tiles.PuddleTile;
import uq.deco2800.duxcom.util.AnimationTickable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class effectively uses the Factory design pattern to allow for users to easily create dynamic liquids. All
 * the user needs to do is to specify an entity type (it is assumed that this entity type is a) registered and
 * b) follows a naming convention (discussed below). Once this is done, a dynamic entity is dynamically created (woah
 * so dynamic) with all the properties of a liquid.
 *
 * Created by tiget 20/10/16
 */
public class DynamicLiquid extends DynamicEntity implements AnimationTickable {

    private int remainingRange = 0;
    private boolean infinite = false;

    /**
     * Constructs an instance of DynamicLiquid at the given coordinates with specified type.
     * @param entityType the type of the dynamic liquid. It is assumed that this type follows the naming convention
     *                   that is discussed above.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param startingRange the starting range (in # of tiles in each direction) of the liquid. For a liquid of
     *                      unlimited range (i.e. it just keeps flowing), set startingRange to -1.
     */
    public DynamicLiquid(EntityType entityType, int x, int y, int startingRange) {
        super(entityType, x, y, 1, 1);

        setPhases(makePhases(entityType));
        if (startingRange < 0) {
            infinite = true;
            this.remainingRange = -1;
        } else {
            this.remainingRange = startingRange;
        }
    }

    private List<Phase> makePhases(EntityType entityType) {

        String[] entityName = entityType.toString().split("_");
        ArrayList<String> entityNameArrayList = new ArrayList<>(Arrays.asList(entityName));
        entityNameArrayList.remove(entityNameArrayList.size() - 1);


        List<Phase> phases = new ArrayList<>();

        for (EntityType entity : EntityType.values()) {
            String[] enumEntityName = entity.toString().split("_");

            ArrayList<String> enumEntityNameArrayList = new ArrayList<>(Arrays.asList(enumEntityName));
            String lastElement = enumEntityNameArrayList.get(enumEntityNameArrayList.size() - 1);

            if (lastElement.matches("^-?\\d+$")) {
                enumEntityNameArrayList.remove(enumEntityNameArrayList.size() - 1);

                if (entityNameArrayList.toString().equals(enumEntityNameArrayList.toString())) {
                    phases.add(new Phase(entity).transmissionRate(1.0));
                }
            }
        }


        return phases;
    }

    /**
     * Returns the sync string (for animation tick) for the DynamicLiquid dynamic entity.
     *
     * @return the sync string, represented as a string (what a surprise)
     */
    @Override
    public String getSyncString() {
        return "LIQUID";
    }

    /**
     * Returns the tick interval (for animation tick) for the DynamicLiquid dynamic entity.
     *
     * @return tick interval, represented as a long
     */
    @Override
    public long getTickInterval() {
        return 149L;
    }

    /**
     * Determines how the DynamicLiquid dynamic entity behaves for each animation tick. Specifically,
     * the water entity will duplicate itself on surrounding tiles (whilst it still has
     * phases to go)
     */
    @Override
    public void animationTick() {
        dynamicsManager.forEachSurrounding(getX(), getY(), 1, false,
                (Integer x, Integer y) -> {
                    if (dynamicsManager.isEmpty(x, y) && getCurrentPhase().transmits() && (infinite || remainingRange > 0) &&
                            dynamicsManager.getMap().getTile(x, y).getStackableEntityHeight() <=
                                    dynamicsManager.getMap().getTile(getX(), getY()).getStackableEntityHeight()) {
                        DynamicLiquid liquid = new DynamicLiquid(this.getEntityType(), x, y, remainingRange - 1);
                        liquid.setHidden(dynamicsManager.getMap().getTile(x, y).isHidden());
                        liquid.setCurrentPhaseIndex(Math.min(getCurrentPhaseIndex() + 1, getPhases().size() - 1));
                        dynamicsManager.addEntity(liquid);
                    }
                });

        boolean advanceCurrentPhase = advanceCurrentPhase();

        if (!advanceCurrentPhase && !infinite) {
            /* this allows for custom behaviour upon tile destruction */
            if ("WATER".equals(getEntityType().toString().split("_")[0]) && getEntityType().toString().split("_").length == 2) {
                /* not that this condition is very messy. this is purely because it is accounting for some legacy (lol) code.
                   i.e. do NOT follow this format for new additions!
                */

                dynamicsManager.getMap().getTile(getX(), getY()).addLiveTile(new PuddleTile(getX(), getY()));
                destroy();
            }
            else {
               /* this is a placeholder */
            }
        }
    }

    /**
     * Moves the current phase of the DynamicLiquid dynamic entity to the next one.
     * @return returns true if succesful in moving to the next phase
     */
    @Override
    public boolean advanceCurrentPhase() {
        currentPhaseIndex++;
            if (phases.size() <= currentPhaseIndex) { // cheeki
                currentPhaseIndex = 0;
                return infinite;
        }

        return true;
    }
}
