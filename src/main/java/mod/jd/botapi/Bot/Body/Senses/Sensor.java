package mod.jd.botapi.Bot.Body.Senses;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * Sensor interface implements functions an entity's senses should have.
 */
public interface Sensor {

    /**
     * Un-Binds the sensor.
     * This should do everything for a neat unbind.
     */
    void unbindSensor();

    /**
     * Returns the entity's health.
     * @return health
     */
    float getHealth();

    /**
     * Returns the entity's max health
     * @return maxHealth
     */
    float getMaxHealth();

    /**
     * Binds the sensor to the given entity.
     * @param entity : The entity to bind to.
     */
    void bindEntity(Entity entity);

    /**
     * Returns the binded entity.
     * @return entity
     */
    Entity getEntity();

    /**
     * Returns the yaw of the entity's head.
     * i.e. The yaw of the entity's looking direction.
     *
     * Follows the Minecraft style co-ordinate system.
     *
     *                                  (-ve Z-Axis)
     *                                      North
     *                                        ^
     *                                 : +-180 degree :
     *
     *(-ve X-Axis) West < +90 degree :       +       : -90 degree > East  (+ve X-Axis)
     *
     *                                  : 0 degree :
     *                                       v
     *                                     South
     *                                 (+ve Z-Axis)
     *
     *  +ve Yaw = Clockwise ...!
     *  -ve Yaw = Anti-Clockwise ...!
     *
     * This all stems from the fact that in Minecraft North = z- instead of z+.
     *
     * @return yaw : Ranges from 0(+z,South) to 360(+z,South) degrees.
     */
    float getYaw();

    /**
     * Returns the pitch of the entity's head.
     * i.e. The pitch of the entity's looking direction.
     *
     * Follows the Minecraft style co-ordinate system.
     *      Up   = -90 degrees
     *      Down = +90 degrees
     * @return yaw : Ranges from +90(down) to -90(up).
     */
    float getPitch();

    /**
     * Returns the reach distance this entity has for blocks.
     * Non-Player entities do not have any reach and hence should use reach as one(1).
     * Player reach can be read from getBlockReachDistance() in {@link net.minecraft.client.multiplayer.PlayerControllerMP}
     *
     * NOTE : Find a way to get the reach compatible with both client and server.
     *      : PlayerControllerMP is Client Only class.
     *
     * @see net.minecraft.client.multiplayer.PlayerControllerMP
     * @return distance : the reach distance this entity has for blocks.
     */
    float getBlockReachDistance();

    /**
     * Returns the entity's current movement speed.
     * @return speed
     */
    double getMovementSpeed();

    /**
     * Returns the value of armour on he entity.
     * @return armour
     */
    double getArmour();

    /**
     * Returns the toughness of the specified piece of armour on the entity.
     * @return
     *  TODO : do something about getArmourToughness().
    double getArmourToughness();
     */

    /**
     * Returns the Luck value of the entity.
     * @return luck
     */
    double getLuck();

    /**
     * Returns the current max attack damage of the entity.
     * @return attackDamage
     */
    double getAttackDamage();

    /**
     * Returns the current max attack speed of the entity.
     * @return attackSpeed
     */
    double getAttackSpeed();

    /**
     * Returns a unit(normalised) vector of the entity's looking direction.
     * @return lookVector : a unit(normalised) vector of the entity's looking direction.
     */
    Vec3d getLookVector();

    /**
     * Returns the position of the entity as a BlockPos Object.
     * @see BlockPos
     * @return position : the position coordinates of the entity.
     */
    Vec3d getPosition();

    /**
     * Returns the block at a position relative to the entity's position.
     * @see Block
     * @return block : the block at a position relative to the entity's position.
     */
    Block getRelativeBlockAt(int x,int y,int z);

    /**
     * Returns the block the entity is facing at.
     * If no block in range of looking direction, must return null.
     * @param stopOnLiquid : Also detects Liquid blocks.
     * @param ignoreBlockWithoutBoundingBox : If true moves past(through) blocks without a bounding box, like water.
     * @param returnLastUncollidableBlock : If true returns the last block in a particular range, different from player's range.
     * @return block : Returns the block the entity is facing at.
     *               : Returns null if no block in reach.
     */
    Block getFacingBlock(boolean stopOnLiquid,boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock);

    /**
     * Performs a ray trace from the entity's eye till its block range.
     * @param stopOnLiquid : Also detects Liquid blocks.
     * @param ignoreBlockWithoutBoundingBox : If true moves past(through) blocks without a bounding box, like water.
     * @param returnLastUncollidableBlock : If true returns the last block in a particular range, different from player's range.
     * @return rayTraceResult : the result of the ray trace.
     */
    RayTraceResult getFacingRayTrace(boolean stopOnLiquid,boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock);
}
