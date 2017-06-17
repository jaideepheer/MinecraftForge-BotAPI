package mod.jd.botapi.Bot.AI.Nodes.Actions;

import mod.jd.botapi.Bot.AI.Nodes.BotAPIAlgorithmNode;
import mod.jd.botapi.Bot.AI.Nodes.NodeMetaData;
import mod.jd.botapi.Bot.Bot;
import mod.jd.botapi.BotAPI;
import net.minecraft.util.math.Vec3d;

/**
 * Makes the Bot face towards and move straight towards a given BlockPos.
 */
@BotAPIAlgorithmNode(modid = BotAPI.MODID, NodeMetaDataObjectName = "meta")
public class MoveStraightToPosAction implements Action {

    private State currentState = State.Not_Initialised;
    public static final NodeMetaData meta = new NodeMetaData("MoveStraightToPosAction","Makes the bot face a x-z position and move towards it.");
    private float x,z;

    public MoveStraightToPosAction(float x,float z)
    {
        this.x=x;
        this.z=z;
        currentState = State.Not_Initialised;
    }

    @Override
    public State getState() {
        return currentState;
    }

    @Override
    public void initialise(Bot bot) {
        bot.stopMoving();
        currentState = State.Never_Performed;
    }

    @Override
    public void perform(Bot bot) {
        currentState = State.Is_Being_Performed;
        // TODO: make this accurate.
        Vec3d tempVec = bot.getBody().getSensor().getPosition();
        if((int)tempVec.xCoord == x && (int)tempVec.zCoord == z)
        {
            bot.stopMoving();
            currentState = State.Successful;
            return;
        }
        bot.faceTowards(x+0.5d,bot.getBody().getSensor().getEntity().getEyeHeight()+bot.getBody().getSensor().getPosition().yCoord,z+0.5d);
        bot.moveForward();
    }

    @Override
    public NodeMetaData getMetaData() {
        return meta;
    }
}
