package mod.jd.botapi.Bot;

import mod.jd.botapi.Bot.AI.Algorithms.Algorithm;
import mod.jd.botapi.Bot.AI.Nodes.Actions.Action;
import mod.jd.botapi.Bot.AI.Nodes.NodeMetaData;
import mod.jd.botapi.Bot.Body.Body;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This is the main Bot class.
 * It manages the interaction between Body and the AI.
 * It runs in a separate thread and manages execution of its Algorithm.
 * @see mod.jd.botapi.Bot.Body.Body
 */
public class Bot extends Thread implements BasicActions{
    private static LinkedHashSet<Class<? extends Body>> BodyList = new LinkedHashSet<>();
    private static HashMap<String,HashMap<String,Class<? extends Action>>> ActionList = new HashMap<>();

    // Stores the Body binded to the Bot.
    private Body botBody ;
    // Stores the Algorithm followed by the Bot.
    private Algorithm currentAlgorithm;
    // Stores the Bot's name.
    public String name;

    // Keeps the Bot's thread running if true.
    private boolean isRunning;

    /**
     * Constructs the Bot and runs it in a new thread.
     * */
    public Bot(Body b,String name)
    {
        // Register this object to EVENT_BUS for TickEvent.
        MinecraftForge.EVENT_BUS.register(this);

        setName(name +" BotThread");

        // Bind the body.
        bind(b);

        // Initiate running state of Bot's thread.
        isRunning = true;

        // Start the Bot's Thread.
        start();
    }

    public synchronized void setAlgorithm(Algorithm algo)
    {
        currentAlgorithm = algo;
        currentAlgorithm.init(this);
    }

    public Algorithm getAlgorithm(){return currentAlgorithm;}

    /**
     * This method runs on the bot thread to update the currentAlgorithm and is resumed by tick events.
     * It updates the currentAlgorithm and then waits till the next invocation by the {@link #onTick(TickEvent)} function.
     */
    @Override
    public void run()
    {
        while (isRunning) {
            // Catch all exceptions to prevent stopping of the BotThread.
            try {
                // Check if binded to a Body.
                if (botBody != null && currentAlgorithm != null) {
                    // Execute current algorithm.
                    currentAlgorithm.execute(this);
                }

                // Wait till the next Tick.
                try {
                    // Lock this object to prevent other synchronized functions(like onTick()) from executing till finished.
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e)
            {
                if(currentAlgorithm!=null)System.err.println("Algorithm \""+currentAlgorithm.getName()+"\"");
                System.err.print(" In Bot \""+name+"\"");
                e.printStackTrace();
            }
        }
    }

    /**
     * This method resumes the bot thread at every tick.
     * @param e
     */
    @SubscribeEvent
    synchronized void onTick(TickEvent e)
    {
        this.notify();
    }

    synchronized public void kill()
    {
        isRunning = false;
        botBody.unbindEntity();

        // Unregister from the event bus.
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void finalize() throws Throwable {
        super.finalize();
    }

    /**
     * Registers a Body to its list of available bodies for automatic Body selection.
     * @param bodyClass : the body class to register to the list of available bodies
     */
    public static void registerBody(Class<? extends Body> bodyClass)
    {
        if(!BodyList.contains(bodyClass)) BodyList.add(bodyClass);
    }

    /**
     * Registers an Action to its list of available Actions.
     * @param actionClass : the action class to be registered.
     * @param modid : the modid of the mod introducing the action.
     * @param NodeMetaDataObjectName : the name of the public static {@link NodeMetaData} field in the Action's class.
     */
    public static void registerAction(Class<? extends Action> actionClass,String modid,String NodeMetaDataObjectName)
    {
        // Add the action corresponding to its modid.
        try {
            // Get the name of the Action from its meta data object.
            String name = ((NodeMetaData)(actionClass.getDeclaredField(NodeMetaDataObjectName).get(null))).getName();

            // If list does not contain the name key then make it.
            if(!ActionList.containsKey(name))
                ActionList.put(name,new HashMap<>());

            // Add action to list if there is no other action with the same name and modid already present.
            if(!ActionList.get(name).containsKey(modid))
                ActionList.get(name).put(modid,actionClass);
            // Else print error and skip this duplicate action.
            else
                System.err.println("Duplicate Action("+actionClass.getName()+") with same modid("+modid+") and name("+name+") skipped.");
        }
        // Print messages in case of errors.
        catch (NoSuchFieldException e) {
            System.err.println("Could not find metadata object("+NodeMetaDataObjectName+") for "+modid+":"+actionClass.getName()+" skipping registering this Action.");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("Could not get access to metadata object("+NodeMetaDataObjectName+") for "+modid+":"+actionClass.getName()+" maybe it is not defined as a 'public static' field. Skipping registering this Action.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the Action class from the ActionList corresponding the given action name.
     * The action name can be preceded by the modid for further clarity in case of naming conflicts.
     * Eg: botapi:MoveStraightToPosAction ; where the modid and the name are separated by ':'
     * @param name : The name of the action to retrieve.
     * @return The Action class from the list.
     *         Returns null if no such action is found.
     */
    public static Class<? extends Action> getActionByName(String name)
    {
        String modid = name.split(":")[0];
        if(!name.contains(":"))
        {
            modid = null;
        }
        else
            name = name.split(":")[1];

        Class<? extends Action> c = null;
        if(modid == null) c = ActionList.get(name).entrySet().iterator().next().getValue();
        else c = ActionList.get(name).get(modid);
        return c;
    }

    /**
     * Binds with the body passed.
     * @param b : the body to bind with.
     */
    public void bind(Body b)
    {
        botBody = b;
    }

    /**
     * Binds the object passed to a compatible body in the list of registered bodies and returns a bot with that body.
     * One can send entities here and the Bot wil automatically decide a suitable body for it.
     * @param object : the object to bind a body with and create a Bot.
     */
    @Nullable
    public static <T> Bot getInstanceFromFactory(T object,String name)
    {
        Body body = null;
        for(Class<? extends Body> c:BodyList)
        {
            try {
                try {
                    body = c.getDeclaredConstructor().newInstance();
                } catch (InvocationTargetException | NoSuchMethodException e) {
                    try {
                        body = c.getDeclaredConstructor(object.getClass()).newInstance(object);
                    } catch (InvocationTargetException | NoSuchMethodException e1) {
                        e1.printStackTrace();
                    }
                }
                Set<Class<?>> list = null;

                if (body != null)list = body.getCompatibleClassList();
                else {System.out.println("Null Body");continue;}

                for(Class<?> s:list)
                {
                    if(s.isAssignableFrom(object.getClass()))
                    {
                        body.bindEntity(object);
                        return new Bot(body,name);
                    }
                }
            } catch (IllegalAccessException | InstantiationException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        System.out.println("No compatible body !");
        return null;
    }

    public Body getBody()
    {
        return botBody;
    }

    //======================================================================
    //
    //                        BasicActions Start Here
    //
    //======================================================================

    @Override
    public void moveForward() {
        botBody.moveForward();
    }

    @Override
    public void jump() {
        botBody.jump();
    }

    @Override
    public void jumpHold() {
        botBody.jumpHold();
    }

    @Override
    public void jumpRelease() {
        botBody.jumpRelease();
    }

    @Override
    public void doubleJump() {
        botBody.doubleJump();
    }

    @Override
    public void startSneaking() {
        botBody.startSneaking();
    }

    @Override
    public void stopSneaking() {
        botBody.stopSneaking();
    }

    @Override
    public void moveBackward() {
        botBody.moveBackward();
    }

    @Override
    public void setMotion(MovementFront front,MovementSide side, boolean sneak) {
        botBody.setMotion(front,side,sneak);
    }

    @Override
    public void stopMoving() {
        botBody.stopMoving();
    }

    @Override
    public void strafeLeft() {
        botBody.strafeLeft();
    }

    @Override
    public void strafeRight() {
        botBody.strafeRight();
    }

    @Override
    public void lookLeft(double degrees) {
        botBody.lookLeft(degrees);
    }

    @Override
    public void lookRight(double degrees) {
        botBody.lookRight(degrees);
    }

    @Override
    public void lookUp(double degrees) {
        botBody.lookUp(degrees);
    }

    @Override
    public void lookDown(double degrees) {
        botBody.lookDown(degrees);
    }

    @Override
    public void setTurnSpeed(double turnSpeed) {
        botBody.setTurnSpeed(turnSpeed);
    }

    @Override
    public void turnToPitch(double degree) {
        botBody.turnToPitch(degree);
    }

    @Override
    public void turnToYaw(double degree) {
        botBody.turnToYaw(degree);
    }

    @Override
    public void faceTowards(double x, double y, double z) {
        botBody.faceTowards(x,y,z);
    }

    @Override
    public void interactFacingBlock() {
        botBody.interactFacingBlock();
    }

    @Override
    public void interactItemInHand() {
        botBody.interactItemInHand();
    }

    @Override
    public void startInteractItemInHand() {
        botBody.startInteractItemInHand();
    }

    @Override
    public void stopInteractItemInHand() {
        botBody.stopInteractItemInHand();
    }

    @Override
    public void startBreakingBlock() {
        botBody.startBreakingBlock();
    }

    @Override
    public void stopBreakingBlock() {
        botBody.stopBreakingBlock();
    }

    @Override
    public void hit() {
        botBody.hit();
    }
}
