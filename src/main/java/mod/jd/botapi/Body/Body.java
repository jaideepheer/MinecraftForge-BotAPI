package mod.jd.botapi.Body;

import net.minecraft.entity.Entity;

/**
 * This class handles all the IO of the Bot.
 * It signifies Existence.
 *
 * Do not forget to apply smoothing to all movement and actions.
 * It will make the Bot more natural.
 */
public interface Body {

    /**
     * Returns the hooked Entity Object.
     * One can use instanceof to check for its class.
     * Or one can even use getClass() to get its Class.
     * @return Entity : The entity hooked to this body
     */
    Entity getEntity();

    /**
     * Makes the body move forward by the given distance.
     * @param distance
     */
    void moveForward(int distance);

    /**
     * Makes the body move backward by the given distance.
     * @param distance
     */
    void moveBackward(int distance);

    /**
     * Makes the body strafe left by the given distance.
     * @param distance
     */
    void strafeLeft(int distance);

    /**
     * Makes the body strafe right by the given distance.
     * @param distance
     */
    void strafeRight(int distance);

    /**
     * Sets the Vertical facing of the body's head.
     * Do not forget to make the movement smooth.
     * @param degree : Ranges from -90 to 90 degrees.
     *               : -90 degrees is Left
     *               : +90 degrees is Right
     */
    void lookVertical(int degree);

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
}
