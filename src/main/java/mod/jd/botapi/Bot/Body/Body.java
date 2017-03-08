package mod.jd.botapi.Bot.Body;

import mod.jd.botapi.Bot.BasicActions;
import mod.jd.botapi.Bot.Body.Senses.Sensor;

import java.util.Set;

/**
 * This class handles all the IO of the Bot.
 * It signifies Physical Existence.
 *
 * Do not forget to apply smoothing to all movement and actions.
 * It will make the Bot more natural.
 */
// TODO communicate errors and events with callers.
public interface Body extends BasicActions{

    /**
     * Returns a set of the classes compatible with this body.
     * Can be used to support custom bodies for entities.
     * // TODO support dynamic addition of custom bodies in the compatible bodies list.
     * eg : PlayerHook returns a HashSet with only EntityPlayerSP.class in it, as it is meant only for that class.
     *
     * NOTE : The constructor of every body should accept only the classes it supports ...!
     *      : Or there should be some check for compatibility to prevent crashes.
     *
     * @return ClassName of the entity which can be binded to this body.
     */
    Set<Class<?>> getCompatibleClassList();

    /**
     * Returns the body's sensor.
     * Every body should have a sensor.
     * It should normally be created in the constructor and stored in the body.
     * @return sensor : the body's sensor.
     */
    Sensor getSensor();

    /**
     * Binds the Body to the given object(generally an entity).
     * It should normally be done in the body's init function.
     * @param object : object to bind to.
     */
    <T>void bindEntity(T object);

    /**
     * Un-Binds the entity.
     * This should do everything to make a neat unbind by setting the entity back to its original state.
     */
    void unbindEntity();

    /**
     * Returns the hooked Object.
     * One can use instanceof to check for its class.
     * Or one can even use getClass() to get its Class.
     * @return Entity : The object hooked to this body
     */
    Object getBindedObject();
}
