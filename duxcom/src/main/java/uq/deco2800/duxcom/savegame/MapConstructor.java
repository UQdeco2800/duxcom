package uq.deco2800.duxcom.savegame;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import uq.deco2800.duxcom.maps.*;

public class MapConstructor {
	private static HashMap<MapType, Class<?>> mapRegister = new HashMap();
	
	public static void registerMap(MapType mapType, AbstractGameMap map){
		mapRegister.put(mapType, ((Object) map).getClass());
	}
	
	/**
	 * 
	 * @param mapType
	 * @return
	 * @throws Exception
	 */
	public static AbstractGameMap getMap(MapType mapType, boolean isBeta) {		
        // Get the type
        Class<?> map = mapRegister.get(mapType);

        // Get the constructor
        Constructor<?> constructor;
        
        try {
            constructor = map.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
        	try{
        		constructor = map.getConstructor(String.class, boolean.class);
        	} catch (NoSuchMethodException noMatch){
        		throw new IllegalArgumentException("No such Constructor, needs Constrictor(String)");
        	}

        }

        // Get the entity itself
        Object generatedEntity;
        try {
            generatedEntity = constructor.newInstance("");
        } catch (Exception e) {
        	try{
        		generatedEntity = constructor.newInstance("", isBeta);
        	} catch (Exception noInstance){
        		throw new IllegalArgumentException(noInstance.getMessage());
        	}
        }

        return (AbstractGameMap) generatedEntity;
	}
	
	public static AbstractGameMap getMap(){
		return new Falador("",false);
	}
	
	/**
     * This block must occur at end!
     */
    static {
        registerMap(MapType.DEMO, new DemoMap("Demo"));
        registerMap(MapType.DYNAMIC_ENTITY_TEST, new DynamicEntityTestMap("Dynamic Test"));
        registerMap(MapType.EMPTY, new EmptyMap("Empty"));
        registerMap(MapType.ENEMY_TEST, new EnemyTestMap("Enemy Test"));
        registerMap(MapType.FALADOR, new Falador("Falador", false));
        registerMap(MapType.KARAMJA, new Karamja("Karmja"));
        registerMap(MapType.MAP001, new Map001("Map001", false));
        registerMap(MapType.MAP002, new Map002("Map002", false));
        registerMap(MapType.MAP003, new Map003("Map003", false));
        registerMap(MapType.MAP004, new Map004("Map004", false));
        registerMap(MapType.MAP005, new Map005("Map005", false));
        registerMap(MapType.MULTIPLAYER, new MultiplayerMap());
        registerMap(MapType.OPTIMISE_DEMO, new OptimisedDemo());
        registerMap(MapType.SAVED, new SavedMap("", 50, 50));
        registerMap(MapType.TEST_MOVEMENT_RANGE, new TestMovementRangeMap(""));
        registerMap(MapType.TUTORIAL, new TutorialMap(""));
    }
}



