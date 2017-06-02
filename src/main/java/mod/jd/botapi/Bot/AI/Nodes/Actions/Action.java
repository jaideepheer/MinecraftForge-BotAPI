package mod.jd.botapi.Bot.AI.Nodes.Actions;

import mod.jd.botapi.Bot.AI.Nodes.Node;
import mod.jd.botapi.Bot.Bot;

/**
 * An action is a set of instructions applicable on a {@link mod.jd.botapi.Bot.Bot}.
 */
public interface Action extends Node {


    enum State{Not_Initialised, Never_Performed,Is_Being_Performed, Successful,Failed}

    /**
     * Returns the current State of the Action to check if it needs execution in the next tick/iteration.
     * Most actions need to be executed in multiple ticks/iterations to check for completion or simply perform the necessary changes.
     *
     * @return State of the Action.
     *
     * */
    State getState();

    /**
     * Used to initialise the Action.
     */
    void initialise(Bot bot);

    /**
     * Performs the action for the current tick/iteration. The action must be initialised before calling this..!
     * Every action must be initialised in its constructor or some other function with the data it might require.
     * */
    void perform(Bot bot);

    /**
     * @see mod.jd.botapi.Bot.AI.Nodes.Node.NodeType
     * @see Node
     * */
    static NodeType getType(){
        return NodeType.ACTION;
    }

}
