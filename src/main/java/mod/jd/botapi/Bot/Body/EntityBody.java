package mod.jd.botapi.Bot.Body;

import net.minecraft.entity.Entity;

/**
 * This class hooks to the given entity and sets it up as a Body.
 * This can only be done on the server side as the server handles all Entity Movements and actions ...!
 * Although I have not yet tested it on the Client Side.
 * @see Body
 */
public class EntityBody {

    // The entity this class hooked to.
    private Entity entity;

    /**
     * Constructor which receives the entity to hook onto.
     * @param targetEntity : Entity to hook to.
     */
    public EntityBody(Entity targetEntity)
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
