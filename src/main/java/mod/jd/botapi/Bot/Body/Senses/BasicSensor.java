package mod.jd.botapi.Bot.Body.Senses;

import net.minecraft.block.Block;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * Monitors the entity and its surroundings.
 */
public abstract class BasicSensor implements Sensor {

    /**
     * This should do everything for a neat unbind.
     */
    @Override
    public void finalize()
    {
        unbindSensor();
        // TODO : finalise and unbind.
    }

    @Override
    public float getYaw() {
        return MathHelper.wrapDegrees(getEntity().rotationYaw);
    }

    @Override
    public float getPitch() {
        return getEntity().rotationPitch;
    }

    @Override
    public Vec3d getLookVector() {
        return getEntity().getLookVec();
    }

    @Override
    public Vec3d getPosition()
    {
        return new Vec3d(getEntity().posX,getEntity().posY,getEntity().posZ);
    }

    @Override
    public Block getRelativeBlockAt(int x,int y,int z) {
        return getEntity().getEntityWorld().getBlockState(getEntity().getPosition().add(x,y,z)).getBlock();
    }

    @Override
    public Block getFacingBlock(boolean stopOnLiquid,boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        RayTraceResult r = getFacingRayTrace(stopOnLiquid,ignoreBlockWithoutBoundingBox,returnLastUncollidableBlock);
        if(r==null)return null;
        if(r.typeOfHit == RayTraceResult.Type.BLOCK)return getEntity().getEntityWorld().getBlockState(r.getBlockPos()).getBlock();
        else return null;
    }

    @Override
    public RayTraceResult getFacingRayTrace(boolean stopOnLiquid,boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        Vec3d pos = getEntity().getPositionVector().addVector(0,(double) getEntity().getEyeHeight(),0);
        return getEntity().getEntityWorld().rayTraceBlocks(pos,pos.add(getLookVector().scale(getBlockReachDistance())),stopOnLiquid,ignoreBlockWithoutBoundingBox,returnLastUncollidableBlock);
    }
}