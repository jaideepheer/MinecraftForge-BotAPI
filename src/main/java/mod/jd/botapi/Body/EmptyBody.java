package mod.jd.botapi.Body;

import mod.jd.botapi.Body.Actions.Action;
import mod.jd.botapi.Body.Actions.ActionState;

/**
 * Base class which implements interface Body and sets up basic working, common for all functions.
 */
public abstract class EmptyBody implements Body {
    // Stores the current action being performed.
    Action currentAction;

    /**
     * @see Body
     * @param action : The Action to be performed.
     */
    @Override
    public void setAction(Action action)
    {
        currentAction = action;
    }

    /**
     * @see Body
     * @return currentAction : The Action being performed.
     */
    @Override
    public Action getCurrentAction()
    {
        return currentAction;
    }

    /**
     * @see Body
     */
    @Override
    public void onTickUpdate()
    {
        // TODO find a better way for this, about where to handle action state, maybe events ?
        if(currentAction!=null && !(currentAction.getActionState()== ActionState.COMPLETED))
            currentAction.run(this);
    }
}
