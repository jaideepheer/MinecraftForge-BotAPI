package mod.jd.botapi.Body;

import mod.jd.botapi.Body.Senses.Sensor;
import net.minecraft.entity.Entity;

/**
 * This class handles all the IO of the Bot.
 * It signifies Existence.
 *
 * Do not forget to apply smoothing to all movement and actions.
 * It will make the Bot more natural.
 */
// TODO To communicate errors and events with callers use custom eventBus in sensors.
public interface Body {

    /**
     * Returns the body's sensor.
     * Every body should have a sensor.
     * It should normally be created in the constructor and stored in the body.
     * @return sensor : the body's sensor.
     */
    Sensor getSensor();

    /**
     * Binds the Body to the given entity.
     * It should normally be done in the body's constructor.
     * @param entity : entity to bind to.
     */
    void bindEntity(Entity entity);

    /**
     * Returns the hooked Entity Object.
     * One can use instanceof to check for its class.
     * Or one can even use getClass() to get its Class.
     * @return Entity : The entity hooked to this body
     */
    Entity getEntity();

    /**
     * Is sent a boolean value to determine if the hooked entity should be allowed to be controlled by other sources.
     * eg. of other sources are Minecraft(the Game), Other Body Objects, etc.
     * If sent
     *          True - Other sources can also control the entity.
     *          False - Only this Body Object can control the Entity.
     * @param b : boolean, true/false
     */
    void letOtherSourcesControlEntity(boolean b);

    /**
     * Returns if the hooked entity is allowed to be controlled by other sources.
     * eg. of other sources are Minecraft(the Game), Other Body Objects, etc.
     * If sent
     *          True - Other sources can also control the entity.
     *          False - Only this Body Object can control the Entity.
     * @return boolean : true/false
     */
    boolean canOtherSourcesControlEntity();

    // ==========================================================================================================
    //
    //
    //
    // ==========================================================================================================

    /**
     * Makes the body start moving forward.
     */
    void moveForward();

    /**
     * Makes the body start moving backward.
     */
    void moveBackward();

    /**
     * Makes the body stop moving.
     */
    void stopMoving();

    /**
     * Makes the body start moving left.
     */
    void strafeLeft();

    /**
     * Makes the body start moving right.
     */
    void strafeRight();

    /**
     * Turns the body's head vertically to match the given angle.
     * Basically sets the Pitch.
     * @param degree : Ranges from -90 to 90 degrees.
     *               : -90 degrees is Down
     *               : 0 degrees is Front
     *               : +90 degrees is Up
     */
    void turnHeadToVertical(double degree);

    /**
     * Turns the body's head horizontally to match the given angle.
     * Basically sets the Pitch.
     * // TODO fix this.
     * @param degree : Ranges from -90 to 90 degrees.
     *               : -90 degrees is Left
     *               : 0 degrees is Front
     *               : +90 degrees is Right
     */
    void turnHeadToHorizontal(double degree);

    /**
     * Performs the Right-Click action for the item in hand.
     * @return boolean : Returns weather the action was successful or not.
     *                 : The action will fail if the body is not capable of performing such an action ...!
     */
    boolean interactItemInHand();

    /**
     * Performs the Right-Click action for the block the body is facing towards.
     * @return boolean : Returns weather the action was successful or not.
     *                 : The action will fail if the body is not capable of performing such an action ...!
     */
    boolean interactFacingBlock();

    /**
     * Performs the Left-Click held action for the body.
     */
    void startBreakingBlock();
    /**
     * Stops the above action.
     */
    void stopBreakingBlock();

    /**
     * Makes the body hit in looking direction with the thing in hand.
     * Performs the Left-Click action for the body.
     */
    void hit();
}
