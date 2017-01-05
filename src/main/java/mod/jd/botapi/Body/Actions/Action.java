package mod.jd.botapi.Body.Actions;

import mod.jd.botapi.Body.Body;

import java.util.HashMap;

/**
 * This class represents an action taken by a body.
 * Signifies instantaneous action.
 * Visit <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-12.html#jls-12.5">Anonymous Class Creation</a>
 */
public abstract class Action {

    // The number of ticks to skip before each update.
    int tickUpdateInterval;
    // The number of ticks to skip before the next updateTick.
    int currentTick;
    // Provides memory for the action
    private HashMap<String,Object> actionMemory = new HashMap<String, Object>();
    // Stores the action state
    private ActionState state;

    // TODO get rid of this from Body.

    /**
     * Is called every tick to run the action.
     * Handles action states like completed, failed etc.
     * Also handles checking if this is the updateTick.
     * An updateTick should occur after every tickUpdateInterval.
     * @param body : Receives the body on which the action is to be performed.
     */
    public void run(Body body)
    {
        switch (state)
        {
            // TODO do initial setup before first tick, in the setter's thread.
            case FIRST_RUN:
                actionMemory.clear();
                firstTickSetup();
                state=ActionState.RUNNING;
                break;
            case RUNNING:
                performTickAction(body);
                break;
            case COMPLETED:
                break;
            case FAILED:
                break;
        }
    }

    /**
     * Is called on the first tick for initial setup.
     * Must be used for initial memory setup to keep the action fast ...!
     */
    public abstract void firstTickSetup();

    public HashMap<String,Object> getActionMemory()
    {
        return actionMemory;
    }

    /**
     * Returns a String as a name for the action.
     * @return name : The String name for the action.
     */
    public abstract String getActionName();

    /**
     * Should be called every tick and should perform tick based actions.
     *
     * One can use the actionMemory variable to store data between tick calls.
     *  eg: to store coordinates to walk to.
     *  eg: to store the error when the action fails.
     *
     *  TODO make state update changes and effects between current and next tick. This might save a tick ?
     * State update is reflected in next tick.
     *
     * @param body : Receives the body on which the action is to be performed.
     * @return  ActionState : ActionState.COMPLETED if the action is done and successful.
     *                      : ActionState.RUNNING if the action was not completed and needs to be executed in further tick calls ...!
     *                      : ActionState.FAILED if the action failed.
     *                      : ActionState.FIRST_RUN if the first run func. should be run. -- This clears actionMemory
     */
    public abstract ActionState performTickAction(Body body);

    /**
     * Returns the state of the action.
     * @see ActionState
     * @return successful : True if the action was successful.
     */
    public ActionState getActionState(){return state;}
    // TODO we'd probably need to set action states too. Make it pause, ready, running. Or just use functions for that.

    public Action getAction(){return this;}
}
