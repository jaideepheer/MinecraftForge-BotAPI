package mod.jd.botapi.Bot.Body;

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
public interface Body {

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
     * Makes the body jump.
     */
    void jump();

    /**
     * Makes the body start sneaking.
     */
    void startSneaking();

    /**
     * Makes the body stop sneaking.
     */
    void stopSneaking();

    /**
     * Makes the body start moving backward.
     */
    void moveBackward();

    /**
     * Sets the motion for the entity.
     * One can use this to move in two directions eg. Forward and Right.
     * @param forward : If true the body starts moving forward.
     * @param backward : If true the body starts moving backward.
     * @param left : If true the body starts strafing left.
     * @param right : If true the body starts strafing right.
     * @param sneak : If true the body starts sneaking.
     */
    void setMotion(boolean forward, boolean backward, boolean left, boolean right, boolean sneak);

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
     * Turns the body's head towards left by the given angle in degrees.
     * Even if the angle is -ve or greater than 180 degrees the head should turn from the Left side.
     * @param degrees : The angle to turn head by.
     */
    void lookLeft(double degrees);

    /**
     * Turns the body's head towards right by the given angle in degrees.
     * Even if the angle is -ve or greater than 180 degrees the head should turn from the Right side.
     * @param degrees : The angle to turn head by.
     */
    void lookRight(double degrees);

    /**
     * Turns the body's head upwards by the given angle in degrees.
     * If -ve degrees, looks down.
     * @param degrees : The angle to turn head by.
     */
    void lookUp(double degrees);

    /**
     * Turns the body's head downwards by the given angle in degrees.
     * If -ve degrees, looks up.
     * @param degrees : The angle to turn head by.
     */
    void lookDown(double degrees);

    /**
     * Sets the speed the entity should turn its head with.
     * Is the multiple by which the turn per tick is calculated.
     * @param turnSpeed : speed the entity should turn its head with
     */
    void setTurnSpeed(double turnSpeed);

    /**
     * Turns the body's head vertically to match the given angle.
     * Basically sets the Pitch.
     * NOTE : Should also clamp the input.
     * @param degree : Ranges from -90 to 90 degrees.
     *               : -90 degrees is Up
     *               : 0 degrees is Front
     *               : +90 degrees is Down
     */
    void turnToPitch(double degree);

    /**
     * Turns the body's head horizontally to match the given angle.
     * Basically sets the Yaw.
     * NOTE : Should also clamp the input to support -ve Yaw.
     * Follows the Minecraft style co-ordinate system.
     *
     *                                 (-ve Z-Axis)
     *                                     North
     *                                       ^
     *                                : +-180 degree :
     *
     *(-ve X-Axis) West < 90 degree :       +       : -90 degree > East  (+ve X-Axis)
     *
     *                                 : 0 degree :
     *                                      v
     *                                    South
     *                                (+ve Z-Axis)
     *
     *  +ve Yaw = Clockwise ...!
     *  -ve Yaw = Anti-Clockwise ...!
     *
     * This all stems from the fact that in Minecraft North = z- instead of z+.
     *
     * @param degree : Ranges from 0(+z,South) to 360(+z,South) degrees.
     */
    void turnToYaw(double degree);

    /**
     * Performs the Right-Click action for the block the body is facing towards.
     * @return boolean : Returns weather the action was successful or not.
     *                 : The action will fail if the body is not capable of performing such an action ...!
     */
    boolean interactFacingBlock();

    /**
     * Performs the Right-Click action for the item in hand.
     * @return boolean : Returns weather the action was successful or not.
     *                 : The action will fail if the body is not capable of performing such an action ...!
     */
    boolean interactItemInHand();

    /**
     * Performs the Right-Click held action for the item in hand.
     */
    void startInteractItemInHand();
    /**
     * Stops the above action.
     */
    void stopInteractItemInHand();

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
