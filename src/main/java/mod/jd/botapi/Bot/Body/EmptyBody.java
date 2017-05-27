package mod.jd.botapi.Bot.Body;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Base class which implements interface Body and sets up basic working, common for all functions.
 */
public abstract class EmptyBody implements Body {
    // Stores if the entity is binded.
    protected boolean isBinded;

    @Override
    public void unbindEntity()
    {
        stopMoving();
        stopInteractItemInHand();
        stopBreakingBlock();
        getSensor().unbindSensor();
        isBinded = false;
    }

    @Override
    public void finalize()
    {
        unbindEntity();
    }

    /**
     * Fired on every update.
     * @see LivingEvent.LivingUpdateEvent
     * @param e : receives and stores the LivingUpdateEvent
     */
    @SubscribeEvent
    public boolean onLivingUpdate(PlayerEvent.LivingUpdateEvent e)
    {
        // Check for proper binding and event invoker.
        // Returns true only if everything is OK.
        return isBinded && e.getEntity().equals(getBindedObject());
    }
}
