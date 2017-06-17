package mod.jd.botapi.Bot.AI.Algorithms;

import mod.jd.botapi.Bot.AI.Nodes.Actions.Action;
import mod.jd.botapi.Bot.AI.Nodes.Actions.MoveStraightToPosAction;
import mod.jd.botapi.Bot.Bot;
import net.minecraft.util.math.BlockPos;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.function.Function;

/**
 * A BotScript is an algorithm that follows a Script using the Nashorn Javascript Engine.
 */
public class BotScript extends Algorithm implements Runnable {

    private Thread scriptThread;
    private Reader scriptReader;
    private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");


    /**
     * Constructor, sets the algorithm's name and initialises its values.
     *
     * @param algorithmName the algorithm's name.
     */
    public BotScript(String algorithmName, File script) throws FileNotFoundException {
            this(algorithmName,new FileReader(script));
    }
    public BotScript(String algorithmName,String script)
    {
        this(algorithmName,new StringReader(script));
    }
    public BotScript(String algorithmName, Reader script)
    {
        super(algorithmName);

        // Initialise the script reader.
        scriptReader = script;

        // Initialise scriptThread.
        scriptThread = new Thread(this,name+" ScriptThread");
    }

    @Override
    public void init(Bot bot) {
        // Create a variable bot for the script pointing to the current bot.
        scriptEngine.put("bot",bot);

        // TODO: provide the script proper classes and objects.
        // Provide this algorithm.
        scriptEngine.put("Algorithm",this);

        // Add the waitForActionCompletion() function.
        // This function pauses the script thread till the currentAction has ended.
        scriptEngine.put("waitForActionCompletion", (Runnable)()->{
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Add the getAction() function.
        scriptEngine.put("getActionType",(Function<String,Object>)(name)->{
            // Get the required action class by parsing the given name.
            Class action = Bot.getActionByName(name);
            try {
                // get the Java.type object for the class and return it.
                return scriptEngine.eval("Java.type(\'"+action.getName()+"\')");
            } catch (ScriptException e) {
                e.printStackTrace();
                return null;
            }
        });

        // Start tne ScriptThread so it waits for scheduling the next action.
        scriptThread.start();
    }

    /**
     * This function resumes the scriptThread when the currentAction has completed.
     */
    @Override
    public synchronized void scheduleNextAction() {
        // Resume the scriptThread so it can set the next action.
        notify();
    }

    /**
     * This is called by the script whenever a new action is to be performed.
     * This function sets the currentAction or makes the thread wait if previous action is incomplete.
     * NOTE that if currentAction had ended it sets the new action and resumes script execution till next setAction() call.
     *      This only pauses the script thread if called before previous action completion.
     *      Hence, the script can set an action and monitor the Bot while it performs the action.
     * @param action is the action to be performed.
     */
    @Override
    public synchronized void setAction(Action action){

        // If no current action then perform given action.
        // Cancel currentAction if action sent was null.
        if(currentAction == null || action == null)
            currentAction = action;
        // else wait for currentAction to end.
        else
            try {
                // Wait till invoked after action completion.
                wait();
                currentAction = action;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public void finalize()
    {
        synchronized (this)
        {
            // TODO: find a better way to stop the script
            scriptThread.stop();
        }
    }

    @Override
    public void run() {
        try {
            // Execute javascript.
            scriptEngine.eval(scriptReader);
        } catch (ScriptException e) {
            // TODO: parse the script file to catch unknown method errors.
            // Exception in Script.
            e.printStackTrace();
        }
    }
}

