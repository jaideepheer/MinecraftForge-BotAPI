package mod.jd.botapi.Bot.Body;

import mod.jd.botapi.Bot.Body.Senses.PlayerSensor;
import mod.jd.botapi.Bot.Body.Senses.Sensor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * This class hooks to the given {@link net.minecraft.client.entity.EntityPlayerSP}.
 * It provides functions to control the player and a sensor to sense its surroundings.
 *
 * NOTE : This class is only to be used on the Client Side.
 *
 * @see EntityPlayerSP
 * @see PlayerSensor for documentation on the {@link PlayerSensor}
 * @see EmptyBody for functions common to most Bodies.
 * @see Body for all the documentation not found here.
 */
@SuppressWarnings("ALL")
@BotAPIBody
public class PlayerBody extends EmptyBody {

    // The PlayerSensor to sense the player's surroundings.
    private PlayerSensor sensor;
    // The Player Object this object is hooked to.
    private EntityPlayerSP player;

    // A MovementInput object reserved for user input.
    private MovementInputFromOptions playerMovementInput;
    // The PlayerBody's custom MovementInput object.
    private MovementInput customMovementInput;
    // True if the user took over controls.
    private boolean userTookOver;

    // Used to hold the jump key in the JumpSafeMovementInput.
    private boolean holdJump = false;
    // Synchronised jump flag.
    private boolean toJump = false;
    // Used to count the ticks for which jump is pressed.
    private short jumpTicks = 0;

    // Used for synchronous hit func. and to prevent mouse blocking after GUI screens.
    private boolean toHit,keephit;
    // Used for synchronous interact func. and to prevent mouse blocking after GUI screens.
    private boolean toInteract,toKeepInteract;

    // The pitch and yaw this body must turn to.
    private double toTurnPitch,toTurnYaw;
    private double turnSpeed;
    // Speed in degrees per second the player can turn at max.
    private static final double MAX_TURN_FACTOR = 100;
    private static final double MIN_TURN_FACTOR = 0.2d;

    // Max instant step distance.
    public static final double MAX_STEP_DISTANCE = 10;

    // Controllable key bindings
    private static ControllableKeyBinding hitKey;
    private static ControllableKeyBinding useItemKey;

    // True if global keyBindings replaced with the static {@Link ControllableKeyBinding} above.
    private static boolean controlsTaken = false;

    /**
     * Constructor which sets the default values, creates the sensor and binds the given {@link EntityPlayerSP}.
     * It raises an {@link UnsupportedOperationException} if called at {@link net.minecraftforge.fml.relauncher.Side#SERVER}, i.e. Server Side.
     * It also replaces keyBindings at first run.
     */
    public PlayerBody(EntityPlayerSP pl)
    {
        if(!Minecraft.getMinecraft().world.isRemote)throw (new UnsupportedOperationException("Cannot Instantiate PlayerBody at Server Side."));
        sensor = new PlayerSensor();
        customMovementInput = getCustomMovementInput();
        setDefaults();
        bindEntity(pl);

        if(controlsTaken)return;
        hitKey = new ControllableKeyBinding(Minecraft.getMinecraft().gameSettings.keyBindAttack);
        useItemKey = new ControllableKeyBinding(Minecraft.getMinecraft().gameSettings.keyBindUseItem);
        Minecraft.getMinecraft().gameSettings.keyBindAttack = hitKey;
        Minecraft.getMinecraft().gameSettings.keyBindUseItem = useItemKey;
        controlsTaken = true;
    }

    /**
     * Sets the default values for the PlayerBody.
     * */
    public void setDefaults()
    {
        toTurnPitch=0;
        toTurnYaw=0;
        turnSpeed = 1;

        toJump = false;
        holdJump = false;
        jumpTicks = 0;

        toKeepInteract = false;
        toInteract = false;
        toHit = false;
        keephit = false;
        userTookOver = false;
    }

    @Override
    public void unbindEntity()
    {
        player.movementInput = new MovementInputFromOptions(Minecraft.getMinecraft().gameSettings);
        Minecraft.getMinecraft().gameSettings.keyBindAttack = hitKey.originalKeyBinding;
        Minecraft.getMinecraft().gameSettings.keyBindUseItem = useItemKey.originalKeyBinding;
        controlsTaken = false;
        setDefaults();
        super.unbindEntity();
    }

    @Override
    public Set<Class<?>> getCompatibleClassList() {
        HashSet<Class<?>> l = new HashSet<Class<?>>();
        l.add(EntityPlayerSP.class);
        return l;
    }

    @Override
    public Sensor getSensor() {
        return sensor;
    }

    @Override
    public <T> void bindEntity(T object) {
        if(object instanceof EntityPlayerSP)
        {
            player = (EntityPlayerSP) object;
            sensor.bindEntity(player);
            playerMovementInput = new MovementInputFromOptions(Minecraft.getMinecraft().gameSettings)
            {
              @Override
              public void updatePlayerMoveState()
              {
                  maintainMovementControl();
              }
            };
            player.movementInput = playerMovementInput;
            isBinded = true;
        }
    }

    /**
     * Returns the hooked Player Object.
     * One can use instanceof to check for its class.
     * Or one can even use getClass() to get its Class.
     * @return player : The player hooked to this body
     */
    @Override
    public Object getBindedObject() {
        return player;
    }

    /**
     * Returns a MovementInput which responds to the PlayerBody's variables.
     * @return MovementInput
     */
    private MovementInput getCustomMovementInput()
    {
        return new MovementInput(){
            @Override
            public void updatePlayerMoveState()
            {

                // Maintain proper User and PlayerBody simultaneous control.
                maintainMovementControl();
                // If user took over do not proceed. Let the user work. Resume after user input ends.
                if(userTookOver)return;
                
                // Hit update
                if(toHit)
                {
                    hitKey.pressed = true;
                    hitKey.pressTime = 1;
                    toHit = false;
                }
                else if(keephit){hitKey.pressed = true;++hitKey.pressTime;}
                else hitKey.pressed = false;
                // Interact update
                if(toInteract)
                {
                    useItemKey.pressed = true;
                    useItemKey.pressTime = 1;
                    toInteract = false;
                }
                else if(toKeepInteract){useItemKey.pressed = true;++useItemKey.pressTime;}
                else useItemKey.pressed = false;

                // Jump Update
                if(toJump || holdJump)
                {
                    this.jump = true;
                    toJump = false;
                }
                else
                {
                    if(jumpTicks==0)
                    {
                        this.jump = false;
                    }
                    else --jumpTicks;
                }

                // Key update
                this.forwardKeyDown = this.moveForward > 0;
                this.backKeyDown = this.moveForward < 0;
                this.leftKeyDown = this.moveStrafe > 0;
                this.rightKeyDown = this.moveStrafe < 0;

                // Facing direction update.
                if(toTurnYaw!=0) {
                    double diff;
                    diff = toTurnYaw * turnSpeed;
                    double d2 = Math.abs(diff);
                    if(d2 > MAX_TURN_FACTOR)diff = MAX_TURN_FACTOR*diff/d2;
                    else if(d2<MIN_TURN_FACTOR)diff = MIN_TURN_FACTOR*diff/d2;
                    if (Math.abs(toTurnYaw)<=diff) diff = toTurnYaw;
                    //player.rotationYawHead += diff;
                    player.rotationYaw += diff;
                    toTurnYaw -= diff;
                }
                if(toTurnPitch!=0){
                    double diff;
                    diff = toTurnPitch * turnSpeed;
                    double d2 = Math.abs(diff);
                    if(d2 > MAX_TURN_FACTOR)diff = MAX_TURN_FACTOR*diff/d2;
                    else if(d2<MIN_TURN_FACTOR)diff = MIN_TURN_FACTOR*diff/d2;
                    if (Math.abs(toTurnPitch)<=diff) diff = toTurnPitch;
                    player.rotationPitch += diff;
                    toTurnPitch -= diff;
                }

            }
        };
    }

    /**
     * Maintains simultaneous control of the player entity so the both the PlayerBody and the player can control it.
     * @see MovementInputFromOptions#updatePlayerMoveState() for normal keyboard movement update code.
     * @see MovementInput for basic movement data storage code.
     */
    public void maintainMovementControl()
    {
        if(isBinded)
        {
            userTookOver = false;
            playerMovementInput.moveStrafe = 0.0F;
            playerMovementInput.moveForward = 0.0F;

            if (Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown())
            {
                ++playerMovementInput.moveForward;
                playerMovementInput.forwardKeyDown = true;
                player.movementInput = playerMovementInput;
                userTookOver = true;
            }
            else
            {
                playerMovementInput.forwardKeyDown = false;
            }

            if (Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown())
            {
                --playerMovementInput.moveForward;
                playerMovementInput.backKeyDown = true;
                player.movementInput = playerMovementInput;
                userTookOver = true;
            }
            else
            {
                playerMovementInput.backKeyDown = false;
            }

            if (Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown())
            {
                ++playerMovementInput.moveStrafe;
                playerMovementInput.leftKeyDown = true;
                player.movementInput = playerMovementInput;
                userTookOver = true;
            }
            else
            {
                playerMovementInput.leftKeyDown = false;
            }

            if (Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown())
            {
                --playerMovementInput.moveStrafe;
                playerMovementInput.rightKeyDown = true;
                player.movementInput = playerMovementInput;
                userTookOver = true;
            }
            else
            {
                playerMovementInput.rightKeyDown = false;
            }

            playerMovementInput.jump = Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
            playerMovementInput.sneak = Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown();

            if(playerMovementInput.jump || playerMovementInput.sneak)
            {
                player.movementInput = playerMovementInput;
                userTookOver = true;
            }

            if (playerMovementInput.sneak)
            {
                playerMovementInput.moveStrafe = (float)((double) playerMovementInput.moveStrafe * 0.3D);
                playerMovementInput.moveForward = (float)((double) playerMovementInput.moveForward * 0.3D);
                player.movementInput = playerMovementInput;
                userTookOver = true;
            }

            // Else let the PlayerBody control movement.
            if(!userTookOver)player.movementInput = customMovementInput;
        }
    }

    @Override
    public void jump() {
        toJump = true;
    }

    @Override
    public void jumpHold() {
        toJump = true;
        holdJump = true;
    }

    @Override
    public void jumpRelease() {
        toJump = false;
        holdJump = false;
        jumpTicks = 0;
    }

    @Override
    public void doubleJump() {
        // TODO : fix double jump
        toJump = true;
        jumpTicks = 2;
    }

    /**
     * Sneak reduces player speed to 30% ...!
     * @see MovementInputFromOptions#updatePlayerMoveState() for movement computation code.
     */
    @Override
    public void startSneaking() {
        customMovementInput.sneak = true;
        customMovementInput.moveStrafe = (float)((double)customMovementInput.moveStrafe*0.3D);
        customMovementInput.moveForward = (float)((double)customMovementInput.moveForward*0.3D);
    }

    /**
     * Sneak reduces player speed to 30% ...!
     * @see MovementInputFromOptions#updatePlayerMoveState() for movement computation code.
     */
    @Override
    public void stopSneaking() {
        customMovementInput.sneak = false;
        customMovementInput.moveStrafe = customMovementInput.moveStrafe==0?0:(float)((double)customMovementInput.moveStrafe/0.3D);
        customMovementInput.moveForward = customMovementInput.moveForward==0?0:(float)((double)customMovementInput.moveForward/0.3D);
    }

    /**
     * The default speed for players is 1 ...!
     * @see MovementInputFromOptions#updatePlayerMoveState() for movement computation code.
     * @see MovementInput
     * @see EntityPlayerSP
     */
    @Override
    public void moveForward() {
        if(player.isSneaking())customMovementInput.moveForward = 0.3f;
        else customMovementInput.moveForward = 1.0f;
    }

    /**
     * The default speed for players is 1 ...!
     * @see MovementInputFromOptions#updatePlayerMoveState() for movement computation code.
     * @see MovementInput
     * @see EntityPlayerSP
     */
    @Override
    public void moveBackward() {
        if(player.isSneaking())customMovementInput.moveForward = -0.3f;
        else customMovementInput.moveForward = -1.0f;
    }

    /**
     * The default speed for players is 1 ...!
     * @see MovementInputFromOptions#updatePlayerMoveState() for movement computation code.
     * @see MovementInput
     * @see EntityPlayerSP
     */
    @Override
    public void strafeLeft() {
        if(player.isSneaking())customMovementInput.moveStrafe = 0.3f;
        else customMovementInput.moveStrafe = 1.0f;
    }

    /**
     * The default speed for players is 1 ...!
     * @see MovementInputFromOptions#updatePlayerMoveState() for movement computation code.
     * @see MovementInput
     * @see EntityPlayerSP
     */
    @Override
    public void strafeRight() {
        if(player.isSneaking())customMovementInput.moveStrafe = -0.3f;
        else customMovementInput.moveStrafe = -1.0f;
    }

    /**
     * The default speed for players is 1 ...!
     * @see MovementInputFromOptions#updatePlayerMoveState() for movement computation code.
     * @see MovementInput
     * @see EntityPlayerSP
     *
     * @param front A {@link mod.jd.botapi.Bot.BasicActions.MovementFront} object fo Froward/Backward movement.
     * @param side A {@link mod.jd.botapi.Bot.BasicActions.MovementSide} object fo Left/Right movement.
     * @param sneak if true the entity starts sneaking.
     */
    @Override
    public void setMotion(MovementFront front, MovementSide side, boolean sneak) {
        customMovementInput.sneak = sneak;

        if(player.isSneaking())customMovementInput.moveForward = front.val*0.3f;
        else customMovementInput.moveForward = front.val;

        if(player.isSneaking())customMovementInput.moveStrafe = side.val*0.3f;
        else customMovementInput.moveStrafe = side.val;
    }

    @Override
    public void stopMoving() {
        customMovementInput.moveForward = customMovementInput.moveStrafe = 0;
        customMovementInput.jump = customMovementInput.sneak = false;
    }

    @Override
    public void lookLeft(double degrees) {
        if(degrees<0)degrees = 360 - (-degrees - 360 * (int)(-degrees)/360);
        else if(degrees>360)degrees -= 360 * (int)(degrees/360);
        toTurnYaw = -degrees;
    }

    @Override
    public void lookRight(double degrees) {
        if(degrees<0)degrees = 360 - (-degrees - 360 * (int)(-degrees)/360);
        else if(degrees>360)degrees -= 360 * (int)(degrees/360);
        toTurnYaw = degrees;
    }

    @Override
    public void lookUp(double degrees) {
        if(degrees<0)lookDown(degrees);
        else toTurnPitch = -degrees;
    }

    @Override
    public void lookDown(double degrees) {
        if(degrees<0)lookUp(degrees);
        else toTurnPitch = degrees;
    }

    @Override
    public void setTurnSpeed(double turnSpeed) {
        this.turnSpeed = turnSpeed;
    }

    @Override
    public void turnToPitch(double degree) {
        toTurnPitch = degree - getSensor().getPitch();
    }

    @Override
    public void turnToYaw(double degree) {
        toTurnYaw = degree - getSensor().getYaw();
        if(toTurnYaw > 180)toTurnYaw = toTurnYaw - 360;
    }

    @Override
    public void interactItemInHand() {
        toInteract = true;
    }

    @Override
    public void startInteractItemInHand() {
        toKeepInteract = true;
    }

    @Override
    public void stopInteractItemInHand() {
        toKeepInteract = false;
    }

    @Override
    public void interactFacingBlock() {
        toInteract = true;
    }

    @Override
    public void startBreakingBlock() {
        keephit = true;
    }

    @Override
    public void stopBreakingBlock() {
        keephit = false;
    }

    @Override
    public void hit() {
        toHit = true;
    }

}

/**
 * This class extends {@link KeyBinding} to provide control over its response and simulate key presses via code.
 * It mainly overrides {@link KeyBinding#isPressed()} and {@link KeyBinding#isKeyDown()} functions.
 *
 * NOTE : This class is only to be used on the Client Side.
 *
 * @see KeyBinding for the main keyBinding controller class.
 */
class ControllableKeyBinding extends KeyBinding
{
    public KeyBinding originalKeyBinding;
    public boolean pressed;
    public int pressTime;
    ControllableKeyBinding(KeyBinding k)
    {
        super(k.getKeyDescription(),k.getKeyConflictContext(),k.getKeyModifier(),k.getKeyCode(),k.getKeyCategory());
        originalKeyBinding = k;
    }

    /**
     * Returns true if the key is pressed fresh down.
     * */
    @Override
    public boolean isPressed()
    {
        if(originalKeyBinding.isPressed())
            return true;
        if(pressTime == 0)
            return false;
        else
        {
            --pressTime;
            return true;
        }
    }

    /**
     * Returns true if the key is held down.
     * */
    @Override
    public boolean isKeyDown()
    {
        return pressed || originalKeyBinding.isKeyDown();
    }
}
