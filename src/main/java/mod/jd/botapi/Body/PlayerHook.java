package mod.jd.botapi.Body;

import mod.jd.botapi.Body.Senses.PlayerSensor;
import mod.jd.botapi.Body.Senses.Sensor;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

/**
 * This class hooks to the given {@link net.minecraft.client.entity.EntityPlayerSP}.
 * It provides functions to control the player and sense its surroundings.
 * @see EntityPlayerSP
 * @see PlayerSensor
 * @see Body
 */
public class PlayerHook extends EmptyBody {

    // The PlayerSensor to sense the player's surroundings.
    private PlayerSensor sensor;
    // The Player Object this object is hooked to.
    private EntityPlayerSP player;

    /**
     * Constructor which receives the player to hook onto.
     * @param targetPlayer : Player to hook to.
     */
    public PlayerHook(EntityPlayerSP targetPlayer)
    {
        player = targetPlayer;
        sensor = new PlayerSensor();
        sensor.bindEntity(player);
    }

    @Override
    public Sensor getSensor() {
        return sensor;
    }

    @Override
    public void bindEntity(Entity entity) {
        if(entity instanceof EntityPlayerSP)player = (EntityPlayerSP) entity;
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
    public void letOtherSourcesControlEntity(boolean b) {
        // TODO stuff here
    }

    @Override
    public void moveForward() {

    }

    @Override
    public void moveBackward() {

    }

    @Override
    public void stopMoving() {

    }

    @Override
    public void strafeLeft() {

    }

    @Override
    public void strafeRight() {

    }

    @Override
    public void turnHeadToVertical(double degree) {

    }

    @Override
    public void turnHeadToHorizontal(double degree) {

    }

    @Override
    public boolean interactItemInHand() {
        return false;
    }

    @Override
    public boolean interactFacingBlock() {
        return false;
    }

    @Override
    public void startBreakingBlock() {

    }

    @Override
    public void stopBreakingBlock() {

    }

    @Override
    public void hit() {

    }

}
