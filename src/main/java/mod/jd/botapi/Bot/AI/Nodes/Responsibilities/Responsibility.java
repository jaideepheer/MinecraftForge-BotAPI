package mod.jd.botapi.Bot.AI.Nodes.Responsibilities;

import mod.jd.botapi.Bot.AI.Algorithms.Algorithm;
import mod.jd.botapi.Bot.Bot;

/**
 * A Responsibility is that component of an algorithm which manages all the constrains/situations that occur during execution.
 */
public interface Responsibility{

    enum ResponsibilityState{FULFILLED,NOT_FULFILLED,IS_BEING_FULFILLED,CANNOT_FULFILL}

    /**
     * Returns the state of the Responsibility.
     */
    ResponsibilityState getState();

    /**
     * Returns the name of the responsibility.
     */
    String getName();

    /**
     * Updates the responsibility's State for the current tick/iteration by checking everything needed to fulfill the responsibility.
     *
     * NOTE : This function must set the responsibility's internal data every time it sets a State as the responsibility can be overridden by other responsibilities at any time.
     *
     * If State is
     *          FULFILLED            : the responsibility does not need any execution.
     *          NOT_FULFILLED        : the responsibility needs to be fulfilled, i.e. call the fulfill() func.
     *          IS_BEING_FULFILLED   : the responsibility needs the fulfill() function to be called next tick/iteration too.
     *          CANNOT_FULFILL       : the responsibility cannot be fulfilled.
     * */
    void updateState(Bot b);

    /**
     * Returns an {@link Algorithm} which on execution must fulfill/satisfy this {@link Responsibility} on the given Bot.
     *
     * NOTE : This function must change the responsibility's state accordingly after every iteration.
     *
     * @param bot is the bot on which the responsibility applies.
     * @param algorithm is the algorithm that is trying to fulfill this responsibility.
     * */
    void fulfill(Bot bot,Algorithm algorithm);

}
