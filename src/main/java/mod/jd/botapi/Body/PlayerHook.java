package mod.jd.botapi.Body;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

/**
 * This class hooks to the given {@link net.minecraft.client.entity.EntityPlayerSP}
 * @see EntityPlayerSP
 * @see Body
 */
public class PlayerHook implements Body {

    // The Player Object this object is hooked to.
    EntityPlayerSP player;

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

    @Override
    public void moveForward(int distance) {

    }

    @Override
    public void moveBackward(int distance) {

    }

    @Override
    public void strafeLeft(int distance) {

    }

    @Override
    public void strafeRight(int distance) {

    }

    @Override
    public void lookVertical(int degree) {

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
