package mod.jd.botapi.Bot;

/**
 * Lists the basic actions a Bot and its bodies should be able to perform.
 */
public interface BasicActions {

    /**
     * Makes the bot/body start moving forward.
     */
    void moveForward();

    /**
     * Makes the bot/body jump.
     */
    void jump();

    /**
     * Makes the bot/body hold the jump key.
     * Used in Double Jumping.
     */
    void jumpHold();

    /**
     * Makes the bot/body double jump.
     * TODO fix doubleJump()
     */
    //void doubleJump();

    /**
     * Makes the bot/body start sneaking.
     */
    void startSneaking();

    /**
     * Makes the bot/body stop sneaking.
     */
    void stopSneaking();

    /**
     * Makes the bot/body start moving backward.
     */
    void moveBackward();

    /**
     * Sets the motion for the bot/body.
     * One can use this to move in two directions eg. Forward and Right.
     * @param forward : If true the bot/body starts moving forward.
     * @param backward : If true the bot/body starts moving backward.
     * @param left : If true the bot/body starts strafing left.
     * @param right : If true the bot/body starts strafing right.
     * @param sneak : If true the bot/body starts sneaking.
     */
    void setMotion(boolean forward, boolean backward, boolean left, boolean right, boolean sneak);

    /**
     * Makes the bot/body stop moving.
     */
    void stopMoving();

    /**
     * Makes the bot/body start moving left.
     */
    void strafeLeft();

    /**
     * Makes the bot/body start moving right.
     */
    void strafeRight();

    /**
     * Turns the bot/body's head towards left by the given angle in degrees.
     * Even if the angle is -ve or greater than 180 degrees the head should turn from the Left side.
     * @param degrees : The angle to turn head by.
     */
    void lookLeft(double degrees);

    /**
     * Turns the bot/body's head towards right by the given angle in degrees.
     * Even if the angle is -ve or greater than 180 degrees the head should turn from the Right side.
     * @param degrees : The angle to turn head by.
     */
    void lookRight(double degrees);

    /**
     * Turns the bot/body's head upwards by the given angle in degrees.
     * If -ve degrees, looks down.
     * @param degrees : The angle to turn head by.
     */
    void lookUp(double degrees);

    /**
     * Turns the bot/body's head downwards by the given angle in degrees.
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
     * Turns the bot/body's head vertically to match the given angle.
     * Basically sets the Pitch.
     * NOTE : Should also clamp the input.
     * @param degree : Ranges from -90 to 90 degrees.
     *               : -90 degrees is Up
     *               : 0 degrees is Front
     *               : +90 degrees is Down
     */
    void turnToPitch(double degree);

    /**
     * Turns the bot/body's head horizontally to match the given angle.
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
     * Performs the Right-Click action for the block the bot/body is facing towards.
     * @return boolean : Returns weather the action was successful or not.
     *                 : The action will fail if the bot/body is not capable of performing such an action ...!
     */
    boolean interactFacingBlock();

    /**
     * Performs the Right-Click action for the item in hand.
     * @return boolean : Returns weather the action was successful or not.
     *                 : The action will fail if the bot/body is not capable of performing such an action ...!
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
     * Performs the Left-Click held action for the bot/body.
     */
    void startBreakingBlock();
    /**
     * Stops the above action.
     */
    void stopBreakingBlock();

    /**
     * Makes the bot/body hit in looking direction with the thing in hand.
     * Performs the Left-Click action for the bot/body.
     */
    void hit();
}
