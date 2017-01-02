package mod.jd.botapi.Body;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class hooks to the given entity and sets it up as a Body.
 * This can only be done on the server side as the server handles all Entity Movements and actions ...!
 * Although I have not yet tested it on the Client Side.
 * @see Body
 */
@SideOnly(Side.SERVER)
public class EntityHook implements Body {

    // The entity this class hooked to.
    Entity entity;

    /**
     * Constructor which receives the entity to hook onto.
     * @param targetEntity : Entity to hook to.
     */
    public EntityHook(Entity targetEntity)
    {
        entity = targetEntity;
    }

    /**
     * Returns the entity this object is hooked to.
     * @return entity
     */
    public Entity getEntity()
    {
        return entity;
    }

    /**
     * Moves the entity to the specified BlockPos.
     * @see BlockPos
     * @param position : BlockPos
     */
    public void moveToPosition(BlockPos position)
    {
        moveToPosition(position.getX(),position.getY(),position.getZ());
    }
    /**
     * Moves the entity to the specified coordinates.
     * @param x,y,z : coordinates
     */
    public void moveToPosition(double x,double y,double z)
    {
        // currently teleports the thingy !!
        entity.move(MoverType.SELF,x,y,z);
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
