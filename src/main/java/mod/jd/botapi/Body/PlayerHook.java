package mod.jd.botapi.Body;

import mod.jd.botapi.Body.Actions.Action;
import mod.jd.botapi.Body.Actions.ActionState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

/**
 * This class hooks to the given {@link net.minecraft.client.entity.EntityPlayerSP}
 * @see EntityPlayerSP
 * @see Body
 */
public class PlayerHook extends EmptyBody {

    // The Player Object this object is hooked to.
    private EntityPlayerSP player;

    /**
     * Constructor which receives the player to hook onto.
     * @param targetPlayer : Player to hook to.
     */
    public PlayerHook(EntityPlayerSP targetPlayer)
    {
        player = targetPlayer;
    }

    /**
     * Returns the hooked Entity Object.
     * One can use instanceof to check for its class.
     * Or one can even use getClass() to get its Class.
     * @return player : The entity hooked to this body
     */
    @Override
    public Entity getEntity() {
        return player;
    }

    /**
     * Called every tick.
     * Performs all tick operations in proper order.
     * Executes the Action.performTickAction()
     */
    @Override
    public void onTickUpdate() {
        super.onTickUpdate();
    }

    /**
     * Makes the body move forward by the given distance.
     * @see Body
     * @param distance : Distance to move.
     */
    @Override
    public void moveForward(final double distance) {
        setAction(new Action() {
            @Override
            public void firstTickSetup() {
                getActionMemory().put("distance",distance);
            }

            @Override
            public String getActionName() {
                return "Move Forward";
            }

            @Override
            public ActionState performTickAction(Body body) {
                // Player entity as this is PlayerHook ...!
                // And performTickAction is passed this body in the EmptyBody class ...!
                EntityPlayerSP player = (EntityPlayerSP)body.getEntity();
                return ActionState.RUNNING; //WIP
            }
        });
    }

    @Override
    public void moveBackward(double distance) {

    }

    @Override
    public void strafeLeft(double distance) {

    }

    @Override
    public void strafeRight(double distance) {

    }

    @Override
    public void lookVertical(double degree) {

    }

    @Override
    public boolean interactItemInHand() {
        return false;
    }

    @Override
    public boolean interactFacingBlock() {
        return false;
    }
}
