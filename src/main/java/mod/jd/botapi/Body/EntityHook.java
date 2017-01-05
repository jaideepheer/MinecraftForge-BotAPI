package mod.jd.botapi.Body;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class hooks to the given entity and sets it up as a Body.
 * This can only be done on the server side as the server handles all Entity Movements and actions ...!
 * Although I have not yet tested it on the Client Side.
 * @see Body
 */
@SideOnly(Side.SERVER)
public class EntityHook {

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

}
