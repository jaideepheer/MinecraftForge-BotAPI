/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package mod.jd.botapi.Bot.AI.Algorithms;

import mod.jd.botapi.Bot.AI.Nodes.Actions.Action;
import mod.jd.botapi.Bot.AI.Nodes.Responsibilities.Responsibility;
import mod.jd.botapi.Bot.Bot;

import java.util.*;

/**
 * An algorithm manages all the Actions and Responsibilities and can be assigned to a Bot.
 */
public abstract class Algorithm {

    // Stores the name of the Algorithm.
    String name;

    // Stores the current action to be or being performed.
    Action currentAction;

    // Stores the current responsibility being fulfilled if any.
    private Responsibility currentResponsibility;

    // Stores the list of Responsibilities to be maintained.
    private List<Responsibility> responsibilityList = Collections.synchronizedList(new ArrayList<Responsibility>());

    /**
     * Constructor, sets the algorithm's name and initialises its values.
     * @param algorithmName the algorithm's name.
     */
    public Algorithm(String algorithmName)
    {
        name = algorithmName;
        currentResponsibility = null;
        currentAction = null;
    }

    /**
     * Returns the name of the algorithm.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Used to initialize the algorithm. Called whenever the algorithm is set to a bot.
     */
    abstract public void init(Bot bot);

    /**
     * Schedules the next action to be performed in the next execution/tick/iteration to be the currentAction.
     * Is called when an action completes successfully or the work with it is done.
     *
     * Should be public so that it can be explicitly called to force to next action.
     *
     * */
    abstract public void scheduleNextAction();

    /**
     * Sets the currentAction as the action passed and performs all necessary operations.
     * @param action is the action to be performed
     */
    public abstract void setAction(Action action);

    /**
     * Sets the currentAction to null.
     */
    public synchronized void cancelCurrentAction()
    {
        currentAction = null;
    }

    /**
     * Sets the currentResponsibility to null.
     */
    public synchronized void cancelCurrentResponsibility()
    {
        currentResponsibility = null;
    }

    /**
     * Executes the algorithm.
     * This does all the work of performing actions and checking responsibilities.
     * It also handles fulfilling of responsibilities.
     *
     * It is called every tick.
     * */
    public synchronized void execute(Bot bot)
    {
        // Check All Responsibilities.
        for (Responsibility responsibility : responsibilityList)
        {
            // Skip if the responsibilityList has a null element.
            if(responsibility == null)continue;

            // Check if it is the current responsibility being fulfilled.
            if(responsibility == currentResponsibility)
            {
                // Try to fulfill the responsibility.
                currentResponsibility.fulfill(bot,this);

                // Check the responsibility's state.
                if(currentResponsibility.getState() == Responsibility.ResponsibilityState.FULFILLED)
                {
                    currentResponsibility = null;
                    // Continue to next responsibility.
                    continue;
                }
                else if(currentResponsibility.getState() == Responsibility.ResponsibilityState.CANNOT_FULFILL)
                {
                    throw new RuntimeException("The responsibility \""+currentResponsibility.getName()+"\" returned Cannot Fulfill status.");
                }
                else if (currentResponsibility.getState() == Responsibility.ResponsibilityState.NOT_FULFILLED)
                {
                    throw new RuntimeException("The responsibility \""+currentResponsibility.getName()+"\" returned Not Fulfilled status even after execution.");
                }
                // Else the responsibility has IS_BEING_FULFILLED status and needs more execution.
                else return;
            }

            // Update the responsibility's state.
            responsibility.updateState(bot);

            // Check the responsibility's state.
            if(responsibility.getState() == Responsibility.ResponsibilityState.NOT_FULFILLED)
            {
                // Try to fulfill the responsibility.
                responsibility.fulfill(bot,this);

                // Check the responsibility's state.
                if(responsibility.getState() == Responsibility.ResponsibilityState.IS_BEING_FULFILLED)
                {
                    currentResponsibility = responsibility;
                    return;
                }
                else if(responsibility.getState() == Responsibility.ResponsibilityState.CANNOT_FULFILL)
                {
                    throw new RuntimeException("The responsibility \""+responsibility.getName()+"\" returned Cannot Fulfill status.");
                }
                else if (responsibility.getState() == Responsibility.ResponsibilityState.NOT_FULFILLED)
                {
                    throw new RuntimeException("The responsibility \""+responsibility.getName()+"\" returned Not Fulfilled status even after execution.");
                }
            }
        }

        // Schedule next action if there is no action to be performed.
        if(currentAction == null)
        {
            scheduleNextAction();
            return;
        }

        // Check the action's State.
        if(currentAction.getState() == Action.State.Not_Initialised)
        {
            // Initialise the currentAction.
            currentAction.initialise(bot);
        }

        // Perform actions.
        currentAction.perform(bot);

        // Check the action's State.
        if(currentAction.getState() == Action.State.Successful)
        {
            currentAction = null;
            // Schedule the next action if the currentAction has completed successfully.
            scheduleNextAction();
        }
        else if (currentAction.getState() == Action.State.Failed)
        {
            throw new RuntimeException("Action \""+currentAction.getMetaData().getName()+"\" returned Failed Status.");
        }
        else if(currentAction.getState() == Action.State.Never_Performed)
        {
            throw new RuntimeException("Action \""+currentAction.getMetaData().getName()+"\" returned Not Yet Performed state even after execution.");
        }
        else if (currentAction.getState() == Action.State.Not_Initialised)
        {
            throw new RuntimeException("Action \""+currentAction.getMetaData().getName()+"\" returned Not Initialised state even after initialisation.");
        }
    }
}
