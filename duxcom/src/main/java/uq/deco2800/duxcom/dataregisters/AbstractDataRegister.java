package uq.deco2800.duxcom.dataregisters;

import uq.deco2800.duxcom.annotation.MethodContract;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This class handles the abstraction of object data.
 * Classes extending this will provide a register that links an enumeration of types to their data.
 * This class allows for conformity across registers and provides the base methods that all registers will use.
 *
 * T represents an extension of Enum that includes all of the types to be stored in the register.
 * C is an extension of AbstractDataClass that represents the structure of the data to be linked
 * with each type in T.
 *
 * @author liamdm
 */
public abstract class AbstractDataRegister<T extends Enum, C extends AbstractDataClass> {
    // As we're a static instance we use a ConcurrentHashMap to prevent
    // potential threading issues
    protected ConcurrentHashMap<T, C> entityDataTypeClassMap;

    /**
     * Class constructor creates a ConcurrentHashMap to register the data in
     */
    public AbstractDataRegister() {
        entityDataTypeClassMap = new ConcurrentHashMap<>();
    }

    /**
     * Adds data regarding a specific data type to the data type register.
     * @param type
     *          The type to be linked
     * @param data
     *          The data to be linked
     */
    @MethodContract(precondition = {"type != null", "data != null"})
    public void linkDataToType(T type, C data){
        entityDataTypeClassMap.put(type, data);
    }

    /**
     * Gets the data for a given type
     * @param type
     *          The type whose data is to be accessed
     * @return The data stored for type on success or null on failure
     */
    @MethodContract(precondition = {"type != null", "entityDataTypeClassMap.containsKey(type)"}, postcondition = {"return != null"})
    public C getData(T type){
        return entityDataTypeClassMap.getOrDefault(type, null);
    }
}
