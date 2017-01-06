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
// TODO communicate errors and events with callers, use custom eventBus in sensors.
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
     * Un-Binds the entity.
     * This should do everything to make a neat unbind by setting the entity back to its original state.
     */
    void unbindEntity();

    /**
     * Returns the hooked Entity Object.
     * One can use instanceof to check for its class.
     * Or one can even use getClass() to get its Class.
     * @return Entity : The entity hooked to this body
     */
    Entity getEntity();

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
     * @param degrees : The angle to turn head by.
     */
    void lookLeft(float degrees);

    /**
     * Turns the body's head towards right by the given angle in degrees.
     * @param degrees : The angle to turn head by.
     */
    void lookRight(float degrees);

    /**
     * Turns the body's head upwards by the given angle in degrees.
     * @param degrees : The angle to turn head by.
     */
    void lookUp(float degrees);

    /**
     * Turns the body's head downwards by the given angle in degrees.
     * @param degrees : The angle to turn head by.
     */
    void lookDown(float degrees);

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
