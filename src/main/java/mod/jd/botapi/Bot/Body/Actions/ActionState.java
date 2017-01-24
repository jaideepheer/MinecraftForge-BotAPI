package mod.jd.botapi.Bot.Body.Actions;

/**
 * Represents the state of an Action.
 */
public enum  ActionState {
    // TODO consider paused and ready states
    FIRST_RUN,
    RUNNING,
    COMPLETED,
    FAILED
}
