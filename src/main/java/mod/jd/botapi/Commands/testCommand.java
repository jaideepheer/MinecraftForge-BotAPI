package mod.jd.botapi.Commands;

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
import sun.misc.FloatingDecimal;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * For testing purposes only.
 */
public class testCommand implements ICommand {
    static PlayerBody playerHook;
    static Bot b;
    Thread t;
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

            if(t==null||args[0]!=null) {
                b = new Bot();
                b.bind(new PlayerBody((EntityPlayerSP) sender.getCommandSenderEntity()));
            }
            for(int ji=0;ji<10;++ji){
            t=new Thread(() -> {
                int i=1;
                while ((i--)>0){
                b.jump();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                b.stopMoving();
            });

        t.start();
            }

        if(true)return;

        //======================================================================

        EntityLivingBase e = (EntityLivingBase) sender.getCommandSenderEntity();

        double speed =e.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
        System.out.println("SPEED = "+speed);

        if(e instanceof EntityPlayerSP)
        {
            if(playerHook==null|| Integer.parseInt(args[0])==-1)
            {
                playerHook=new PlayerBody((EntityPlayerSP) e);
            }
            System.out.println("SPEED = "+playerHook.getSensor().getMovementSpeed()
                    +"\nLooking Block = "+playerHook.getSensor().getFacingBlock(Integer.parseInt(args[0])>0,Integer.parseInt(args[1])>0,Integer.parseInt(args[2])>0)
                    +"\nHealth = "+playerHook.getSensor().getHealth()+"/"+playerHook.getSensor().getMaxHealth()
            +"\n\nFacingRayTrace = "+playerHook.getSensor().getFacingRayTrace(Integer.parseInt(args[0])>0,Integer.parseInt(args[1])>0,Integer.parseInt(args[2])>0));

            System.out.println("\nArmour = "+playerHook.getSensor().getArmour()
            +"\nTotalArmour = "+((EntityPlayerSP)(playerHook.getBindedObject())).getTotalArmorValue()
            +"\nLuck = "+playerHook.getSensor().getLuck()
            +"\nAttackDM = "+playerHook.getSensor().getAttackDamage()
            +"\nAttackSpeed = "+playerHook.getSensor().getAttackSpeed());
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
