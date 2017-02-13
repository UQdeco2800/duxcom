package uq.deco2800.duxcom.savegame;

import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.WoodStack;
import uq.deco2800.duxcom.entities.enemies.enemychars.*;
import uq.deco2800.duxcom.entities.heros.*;


import java.lang.reflect.Constructor;
import java.util.HashMap;


/**
 * Links entity types to their respective entities and allows for re-generation.
 * <p>
 * Created by liamdm on 24/08/2016.
 */
public class EntityConstructor {
    private static HashMap<EntityType, Class<?>> typeMap = new HashMap<>();

    /**
     * Links the given entity type to the requested entity
     *
     * @param type   the type of the entity (can be used to re-generate the entity)
     * @param entity the entity representation
     */
    public static void registerEntity(EntityType type, Entity entity) {
        typeMap.put(type, ((Object) entity).getClass());
    }
    
    /**
     * Regenerates an entity given the type, and a generic set of arguments.
     * Will search for a matching constructor, and if that failes will throw
     * a runtime exception.
     *
     * @param type the type to re-generate
     * @param arguments the arguments to use
     */
    public static <T> T generateEntityGenericConstructor(EntityType type, Object[] arguments) {
        // Get the type
        Class<?> entity = typeMap.get(type);

        // Get the constructor
        Constructor<?> constructor;
        gotConstructor:
        {
            for (Constructor<?> ctr : entity.getConstructors()) {

                if (ctr.getParameterCount() != arguments.length) {
                    continue;
                }

                notFound:
                {
                    for (int i = 0; i < arguments.length; ++i) {
                        if (ctr.getParameterTypes()[i].toString().equals(arguments[i].getClass().toString())) {
                            break notFound;
                        }
                    }
                    // Found the correct constructor
                    constructor = ctr;
                    break gotConstructor;
                }
                // Failed to find constructor, continue
            }
            // failed to find matching constructor
            throw new IllegalArgumentException("Failed to find matching constructor for type " + type + "! Required int, int!");
        }

        // Get the entity itself
        Object generatedEntity;
        try {
            generatedEntity = constructor.newInstance(arguments);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return (T) generatedEntity;
    }

    /**
     * Regenerates an entity given the type
     *
     * @param type the type to re-generate
     * @param x    the x coordinate
     * @param y    the y coordinate
     */
    public static <T> T generateEntity(EntityType type, int x, int y) {
        // Get the type
        Class<?> entity = typeMap.get(type);

        // Get the constructor
        Constructor<?> constructor;
        try {
            constructor = entity.getConstructor(int.class, int.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Failed to find matching constructor for type " + type + "! Required int, int!");

        }

        // Get the entity itself
        Object generatedEntity;
        try {
            generatedEntity = constructor.newInstance(x, y);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return (T) generatedEntity;
    }
    
    /**
     * This block must occur at end!
     */
    static {
    	// Heroes
        registerEntity(EntityType.DUCK, new Duck(1,1));
        registerEntity(EntityType.PRIEST, new Priest(1,1));
        registerEntity(EntityType.WARLOCK, new Warlock(1,1));
        registerEntity(EntityType.KNIGHT, new Knight(1,1));
        registerEntity(EntityType.CAVALIER, new Cavalier(1,1));
        registerEntity(EntityType.ARCHER, new Archer(1,1));
        registerEntity(EntityType.ROGUE, new Rogue(1,1));
        registerEntity(EntityType.REAL_IBIS, new Ibis(1,1));
       
        // Enemies
        registerEntity(EntityType.ENEMY_ARCHER, new EnemyArcher(1,1));
        registerEntity(EntityType.ENEMY_TANK, new EnemyTank(1,1));
        registerEntity(EntityType.ENEMY_WOLF, new EnemyWolf(1,1));
        registerEntity(EntityType.ENEMY_KNIGHT, new EnemyKnight(1,1));
        registerEntity(EntityType.ENEMY_BEAR, new EnemyBear(1,1));
        registerEntity(EntityType.ENEMY_BRUTE, new EnemyBrute(1,1));
        registerEntity(EntityType.ENEMY_SUPPORT, new EnemySupport(1,1));
        registerEntity(EntityType.ENEMY_DARK_MAGE, new EnemyDarkMage(1,1));
        registerEntity(EntityType.ENEMY_GRUNT, new EnemyGrunt(1,1));
        registerEntity(EntityType.ENEMY_ROGUE, new EnemyRogue(1,1));
        
        // Objects
        registerEntity(EntityType.WOOD_STACK, new WoodStack(1,1));

    }
}
