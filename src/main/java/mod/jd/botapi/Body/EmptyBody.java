package mod.jd.botapi.Body;

/**
 * Base class which implements interface Body and sets up basic working, common for all functions.
 */
public abstract class EmptyBody implements Body {
    protected boolean canOtherSourcesControlEntity;

    @Override
    public boolean canOtherSourcesControlEntity() {
        return canOtherSourcesControlEntity;
    }
}
