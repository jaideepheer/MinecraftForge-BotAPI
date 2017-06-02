package mod.jd.botapi.Bot.AI.Nodes.Actions;

import mod.jd.botapi.Bot.Bot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Makes the Bot face towards and move straight towards a given BlockPos.
 */
public class MoveStraightToPosAction implements Action {

    private State currentState = State.Not_Initialised;
    private BlockPos p;

    public MoveStraightToPosAction(BlockPos bp){p=bp;currentState = State.Not_Initialised;}

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
        if((int)tempVec.xCoord == p.getX() && (int)tempVec.zCoord == p.getZ())
        {
            bot.stopMoving();
            currentState = State.Successful;
            return;
        }
        bot.faceTowards(p.getX()+0.5d,bot.getBody().getSensor().getEntity().getEyeHeight()+p.getY()+1,p.getZ()+0.5d);
        bot.moveForward();
    }

    @Override
    public String getName() {
        return "MoveStraightToPosAction";
    }
}
