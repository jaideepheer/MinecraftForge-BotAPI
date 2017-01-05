package mod.jd.botapi.Body.Senses;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

/**
 * A sensor for the EntityPlayerSP class.
 */
public class PlayerSensor extends BasicSensor {
    private EntityPlayerSP player;
    @Override
    public float getHealth() {
        return player.getHealth();
    }

    @Override
    public float getMaxHealth() {
        return player.getMaxHealth();
    }

    @Override
    public void bindEntity(Entity entity) {
        player = (EntityPlayerSP)entity;
    }

    @Override
    public Entity getEntity() {
        return player;
    }

    /**
     * See the getBlockReachDistance() in PlayerControllerMP.
     * Sadly it's a client only class.
     * @see net.minecraft.client.multiplayer.PlayerControllerMP
     * @return reach
     */
    @Override
    public float getBlockReachDistance() {
        return 4.5F;
    }
}
