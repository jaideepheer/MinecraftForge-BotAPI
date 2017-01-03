package mod.jd.botapi.Body;

import mod.jd.botapi.Body.Actions.Action;
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
     * Sets the currentAction to be performed.
     * Can be used to set custom actions ...!
     * NOTE : The currentAction is replaced even if it is incomplete.
     *      : If the action is not completed it will never stop ...!
     * @see Action
     * @param action : The Action to be performed.
     */
    void setAction(Action action);

    /**
     * Returns the currentAction being performed.
     * @return Action : Object instanceof interface Action.
     */
    Action getCurrentAction();

    /**
     * Called every tick.
     * Performs all tick operations in proper order.
     * Executes the Action.performTickAction()
     */
    void onTickUpdate();
    // ==========================================================================================================
    //
    //
    //
    // ==========================================================================================================

    /**
     * Makes the body move forward by the given distance.
     * @param distance : Distance to move.
     */
    void moveForward(double distance);

    /**
     * Makes the body move backward by the given distance.
     * @param distance : Distance to move.
     */
    void moveBackward(double distance);

    /**
     * Makes the body strafe left by the given distance.
     * @param distance : Distance to move.
     */
    void strafeLeft(double distance);

    /**
     * Makes the body strafe right by the given distance.
     * @param distance : Distance to move.
     */
    void strafeRight(double distance);

    /**
     * Turns the body's head vertically to match the given angle.
     * Basically sets the Pitch.
     * @param degree : Ranges from -90 to 90 degrees.
     *               : -90 degrees is Down
     *               : +90 degrees is Up
     */
    void lookVertical(double degree);

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
