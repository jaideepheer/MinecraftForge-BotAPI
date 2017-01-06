package mod.jd.botapi.Body.Senses;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

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

    @Override
    public double getMovementSpeed() {
        // TODO fix this. The attribute stores default speed.
        return player.capabilities.getWalkSpeed();
    }

    @Override
    public double getArmour() {
        return getSharedAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue();
    }

    @Override
    public double getLuck() {
        return player.getLuck();
    }

    @Override
    public double getAttackDamage() {//TODO Fix this, always returns 1.0 with weapon.
        return getSharedAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    }

    @Override
    public double getAttackSpeed() {
        return getSharedAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
    }

    IAttributeInstance getSharedAttribute(IAttribute a)
    {
        return player.getEntityAttribute(a);
    }
}
