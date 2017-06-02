package mod.jd.botapi.Commands;

import mod.jd.botapi.Bot.AI.Nodes.Actions.MoveStraightToPosAction;
import mod.jd.botapi.Bot.Body.PlayerBody;
import mod.jd.botapi.Bot.Bot;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * For testing purposes only.
 */
public class testCommand implements ICommand {
    static Bot playerHook;
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "BotAPI test command.";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<String>();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        //======================================================================

        EntityLivingBase e = (EntityLivingBase) sender.getCommandSenderEntity();

        double speed =e.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
        System.out.println("SPEED = "+speed);

        if(e instanceof EntityPlayerSP)
        {
            if(playerHook==null|| Integer.parseInt(args[0])==-1)
            {
                playerHook = null;
                playerHook=new Bot(new PlayerBody((EntityPlayerSP) sender.getCommandSenderEntity()));
            }
            System.out.println("SPEED = "+playerHook.getBody().getSensor().getMovementSpeed()
                    +"\nLooking Block = "+playerHook.getBody().getSensor().getFacingBlock(Integer.parseInt(args[0])>0,Integer.parseInt(args[1])>0,Integer.parseInt(args[2])>0)
                    +"\nHealth = "+playerHook.getBody().getSensor().getHealth()+"/"+playerHook.getBody().getSensor().getMaxHealth()
            +"\n\nFacingRayTrace = "+playerHook.getBody().getSensor().getFacingRayTrace(Integer.parseInt(args[0])>0,Integer.parseInt(args[1])>0,Integer.parseInt(args[2])>0));

            System.out.println("\nArmour = "+playerHook.getBody().getSensor().getArmour()
            +"\nTotalArmour = "+((EntityPlayerSP)(playerHook.getBody().getBindedObject())).getTotalArmorValue()
            +"\nLuck = "+playerHook.getBody().getSensor().getLuck()
            +"\nAttackDM = "+playerHook.getBody().getSensor().getAttackDamage()
            +"\nAttackSpeed = "+playerHook.getBody().getSensor().getAttackSpeed());
            switch (Integer.parseInt(args[3])) {
                case 0:
                    playerHook.stopMoving();
                    break;
                case 1:
                    playerHook.moveForward();
                    break;
                case 2:
                    playerHook.moveBackward();
                    break;
                case 3:
                    playerHook.strafeLeft();
                    break;
                case 4:
                    playerHook.strafeRight();
                    break;
                case 5:
                    playerHook.startSneaking();
                    break;
                case 6:
                    playerHook.stopSneaking();
                    break;
                case 7:
                    playerHook.jump();
                    break;
                case 8:
                    playerHook.lookUp(Float.parseFloat(args[4]));
                    break;
                case 9:
                    playerHook.lookDown(Float.parseFloat(args[4]));
                    break;
                case 10:
                    playerHook.lookRight(Float.parseFloat(args[4]));
                    break;
                case 11:
                    playerHook.lookLeft(Float.parseFloat(args[4]));
                    break;
                case 12:
                    playerHook.startBreakingBlock();
                    break;
                case 13:
                    playerHook.stopBreakingBlock();
                    break;
                case 14:playerHook.interactFacingBlock();
                    break;
                case 15:playerHook.interactItemInHand();
                    break;
                case 16:playerHook.startInteractItemInHand();
                    break;
                case 17:playerHook.stopInteractItemInHand();
                    break;
                case 18:
                    playerHook.jumpHold();
                    break;
                case 19:
                    playerHook.jumpRelease();
                    break;
                case 20:
                    playerHook.faceTowards(Double.parseDouble(args[4]),Double.parseDouble(args[5]),Double.parseDouble(args[6]));
                    break;
                case 21:
                    playerHook.actionList.add(new MoveStraightToPosAction(new BlockPos(Double.parseDouble(args[4]),Double.parseDouble(args[5]),Double.parseDouble(args[6]))));
                    break;
                case 22:
                    playerHook.setTurnSpeed(Double.parseDouble(args[4]));
                    break;
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
