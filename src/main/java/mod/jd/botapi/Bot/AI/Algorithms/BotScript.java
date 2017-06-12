package mod.jd.botapi.Bot.AI.Algorithms;

import mod.jd.botapi.Bot.AI.Nodes.Actions.Action;
import mod.jd.botapi.Bot.AI.Nodes.Actions.MoveStraightToPosAction;
import mod.jd.botapi.Bot.Bot;
import net.minecraft.util.math.BlockPos;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;

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
        scriptEngine.put("MoveStraightToPosAction",MoveStraightToPosAction.class);
        scriptEngine.put("BlockPos",BlockPos.class);

        // Start tne ScriptThread so it waits for scheduling the next action.
        scriptThread.start();
    }

    /**
     * This function resumes the scriptThread when the currentAction has completed.
     */
    @Override
    public void scheduleNextAction() {
        // Resume the scriptThread so it can set the next action.
        notify();
    }

    /**
     * This is called by the script whenever a new action is to be performed.
     * This function sets the currentAction and makes the thread wait.
     * @param action is the action to be performed.
     */
    @Override
    public synchronized void setAction(Action action){
        // Do not wait for completion if action sent was null.
        if(action == null)return;

        currentAction = action;

        try {
            // Wait till invoked after action completion.
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void finalize()
    {
        synchronized (this)
        {
            // TODO: find a better way to stop the script
            Thread.currentThread().stop();
        }
    }

    @Override
    public void run() {

        /*synchronized (this){
            // Wait till invoked for first action.
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        try {
            // Execute javascript.
            scriptEngine.eval(scriptReader);
        } catch (ScriptException e) {
            // Exception in Script.
            e.printStackTrace();
        }
    }
}
