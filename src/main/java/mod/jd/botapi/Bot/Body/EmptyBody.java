package mod.jd.botapi.Bot.Body;

/**
 * Base class which implements interface Body and sets up basic working, common for all functions.
 */
public abstract class EmptyBody implements Body {
    // Stores if entity is binded.
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
}
