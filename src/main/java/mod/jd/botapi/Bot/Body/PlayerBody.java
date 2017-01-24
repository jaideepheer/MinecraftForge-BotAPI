package mod.jd.botapi.Bot.Body;

import mod.jd.botapi.Bot.Body.Senses.PlayerSensor;
import mod.jd.botapi.Bot.Body.Senses.Sensor;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovementInput;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class hooks to the given {@link net.minecraft.client.entity.EntityPlayerSP}.
 * It provides functions to control the player and a sensor to sense its surroundings.
 * @see EntityPlayerSP
 * @see PlayerSensor
 * @see EmptyBody
 * @see Body
 */
public class PlayerBody extends EmptyBody {

    // The PlayerSensor to sense the player's surroundings.
    private PlayerSensor sensor;
    // The Player Object this object is hooked to.
    private EntityPlayerSP player;
    // The Player's previous(original) MovementInput object.
    private MovementInput previousMovementInput;

    // The pitch and yaw this body must turn to.
    private float toTurnPitch,toTurnYaw;
    private double turnSpeed;

    // Speed in degrees per second the player can turn at max.
    private static final int MAX_TURN_PER_SEC = 180;

    /**
     * Constructor which receives the player to hook onto.
     * @param targetPlayer : Player to hook to.
     */
    public PlayerBody(EntityPlayerSP targetPlayer)
    {
        player = targetPlayer;
        sensor = new PlayerSensor();
        sensor.bindEntity(player);
        isBinded = true;
        toTurnPitch=0;
        toTurnYaw=0;
        turnSpeed = 0.15364d;

        // Register this class to the EVENT_BUS for updateEvents.
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public Class getEntityClass() {
        return EntityPlayerSP.class;
    }

    @Override
    public Sensor getSensor() {
        return sensor;
    }

    @Override
    public void bindEntity(Entity entity) {
        if(entity instanceof EntityPlayerSP && player == null)
        {
            player = (EntityPlayerSP) entity;
            previousMovementInput = player.movementInput;
            isBinded = true;
        }
    }

    /**
     * Returns the hooked Entity Object.
     * One can use instanceof to check for its class.
     * Or one can even use getClass() to get its Class.
     * @return player : The entity hooked to this body
     */
    @Override
    public Entity getEntity() {
        if(!isBinded)return null;
        return player;
    }

    /**
     * Sets a new jump safe MovementInput and backs up the original one.
     */
    private void takeMovementControl()
    {
        takeMovementControl(getJumpSafeMovementInput());
    }

    /**
     * Returns a MovementInput which does not cause continuous jumping as it is not watched by keyboard updates.
     * @return MovementInput
     */
    private MovementInput getJumpSafeMovementInput()
    {
        return new MovementInput(){
            @Override
            public void updatePlayerMoveState()
            {
                if(getEntity().onGround)this.jump=false;
            }
        };
    }

    /**
     * Sets a new MovementInput and backs up the original one.
     * @param i : the new MovementInput to set.
     */
    private void takeMovementControl(MovementInput i)
    {
        if(isBinded)
        {
            if(previousMovementInput==null)previousMovementInput = player.movementInput;
            player.movementInput = i;
        }
    }
    /**
     * The default speed for players is 1 ...!
     * @see net.minecraft.util.MovementInputFromOptions
     */
    @Override
    public void moveForward() {
        if(!isBinded)return;
        takeMovementControl();
        player.movementInput.forwardKeyDown = true;
        player.movementInput.moveForward = 1;
        if(player.isSneaking())player.movementInput.moveForward = (float)((double)player.movementInput.moveForward*0.3D);
    }

    @Override
    public void jump() {
        if(!isBinded)return;
        player.jump();
    }

    /**
     * Sneak reduces speed to 30% ...!
     * @see net.minecraft.util.MovementInputFromOptions
     */
    @Override
    public void startSneaking() {
        if(!isBinded)return;
        takeMovementControl();
        player.movementInput.sneak = true;
        player.movementInput.moveStrafe = (float)((double)player.movementInput.moveStrafe*0.3D);
        player.movementInput.moveForward = (float)((double)player.movementInput.moveForward*0.3D);
    }

    /**
     * Sneak reduces speed to 30% ...!
     * @see net.minecraft.util.MovementInputFromOptions
     */
    @Override
    public void stopSneaking() {
        if(!isBinded)return;
        takeMovementControl();
        player.movementInput.sneak = false;
        player.movementInput.moveStrafe = player.movementInput.moveStrafe==0?0:(float)((double)player.movementInput.moveStrafe/0.3D);
        player.movementInput.moveForward = player.movementInput.moveForward==0?0:(float)((double)player.movementInput.moveForward/0.3D);
    }

    /**
     * The default speed for players is 1 ...!
     * @see net.minecraft.util.MovementInputFromOptions
     */
    @Override
    public void moveBackward() {
        if(!isBinded)return;
        takeMovementControl();
        player.movementInput.backKeyDown = true;
        player.movementInput.moveForward = -1;
        if(player.isSneaking())player.movementInput.moveForward = (float)((double)player.movementInput.moveForward*0.3D);
    }

    /**
     * The default speed for players is 1 ...!
     * @see net.minecraft.util.MovementInputFromOptions
     */
    @Override
    public void setMotion(boolean forward, boolean backward, boolean left, boolean right, boolean sneak) {
        MovementInput i = getJumpSafeMovementInput();
        if(forward){++i.moveForward;i.forwardKeyDown=true;}
        else if(backward){--i.moveForward;i.backKeyDown=true;}
        if(left){++i.moveStrafe;i.leftKeyDown=true;}
        else if(right){--i.moveStrafe;i.rightKeyDown=true;}
        if(sneak)
        {
            i.moveStrafe = (float)((double)i.moveStrafe*0.3D);
            i.moveForward = (float)((double)i.moveForward*0.3D);
        }
        if(previousMovementInput==null)previousMovementInput = player.movementInput;
        player.movementInput = i;
    }

    @Override
    public void stopMoving() {
        if(!isBinded||(previousMovementInput==null))return;
        player.movementInput = previousMovementInput;
        previousMovementInput = null;
    }

    /**
     * The default speed for players is 1 ...!
     * @see net.minecraft.util.MovementInputFromOptions
     */
    @Override
    public void strafeLeft() {
        if(!isBinded)return;
        takeMovementControl();
        player.movementInput.leftKeyDown = true;
        player.movementInput.moveStrafe = 1;
        if(player.isSneaking())player.movementInput.moveStrafe = (float)((double)player.movementInput.moveStrafe*0.3D);
    }

    /**
     * The default speed for players is 1 ...!
     * @see net.minecraft.util.MovementInputFromOptions
     */
    @Override
    public void strafeRight() {
        if(!isBinded)return;
        takeMovementControl();
        player.movementInput.rightKeyDown = true;
        player.movementInput.moveStrafe = -1;
        if(player.isSneaking())player.movementInput.moveStrafe = (float)((double)player.movementInput.moveStrafe*0.3D);
    }

    // TODO everything down here...

    /**
     * Fired on every update.
     * @see LivingEvent.LivingUpdateEvent
     * @param e
     */
    @SubscribeEvent
    public void onUpdate(PlayerEvent.LivingUpdateEvent e)
    {
        if(!e.getEntity().equals(player))return;

        float diff=0.0f;

            diff = Math.min((float) ((double)toTurnYaw * turnSpeed),MAX_TURN_PER_SEC/20);
            if((int)toTurnYaw*10==0)diff=toTurnYaw;
            //player.rotationYawHead += diff;
            player.rotationYaw += diff;
            toTurnYaw -= diff;

            diff = Math.min((float) ((double)toTurnPitch * turnSpeed),MAX_TURN_PER_SEC/20);
            if((int)toTurnPitch*10==0)diff=toTurnPitch;
            player.rotationPitch += diff;
            toTurnPitch -= diff;
    }

    @Override
    public void lookLeft(float degrees) {
        if(!isBinded)return;
        if(degrees<0)degrees = 360 - (-degrees - 360 * (int)(-degrees)/360);
        else if(degrees>360)degrees -= 360 * (int)(degrees/360);
        toTurnYaw = -degrees;
    }

    @Override
    public void lookRight(float degrees) {
        if(!isBinded)return;
        if(degrees<0)degrees = 360 - (-degrees - 360 * (int)(-degrees)/360);
        else if(degrees>360)degrees -= 360 * (int)(degrees/360);
        toTurnYaw = degrees;
    }

    @Override
    public void lookUp(float degrees) {
        if(!isBinded)return;
        if(degrees<0)lookDown(degrees);
        else toTurnPitch = -degrees;
    }

    @Override
    public void lookDown(float degrees) {
        if(!isBinded)return;
        if(degrees<0)lookUp(degrees);
        else toTurnPitch = degrees;
    }

    @Override
    public void setTurnSpeed(double turnSpeed) {
        this.turnSpeed = turnSpeed;
    }

    @Override
    public void turnToPitch(double degree) {
        if(!isBinded)return;
    }

    @Override
    public void turnToYaw(double degree) {
        if(!isBinded)return;
    }

    @Override
    public boolean interactItemInHand() {
        if(!isBinded)return false;
        return false;
    }

    @Override
    public void startInteractItemInHand() {
        if(!isBinded)return;
    }

    @Override
    public void stopInteractItemInHand() {
        if(!isBinded)return;
    }

    @Override
    public boolean interactFacingBlock() {
        if(!isBinded)return false;
        return false;
    }

    @Override
    public void startBreakingBlock() {
        if(!isBinded)return;
    }

    @Override
    public void stopBreakingBlock() {
        if(!isBinded)return;
    }

    @Override
    public void hit() {
        if(!isBinded)return;
    }

}