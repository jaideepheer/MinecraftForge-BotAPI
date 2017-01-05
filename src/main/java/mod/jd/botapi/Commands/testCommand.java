package mod.jd.botapi.Commands;

import mod.jd.botapi.Body.PlayerHook;
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
        EntityLivingBase e = (EntityLivingBase) sender.getCommandSenderEntity();

        double speed =e.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
        System.out.println("SPEED = "+speed);
        PlayerHook playerHook;
        if(e instanceof EntityPlayerSP)
        {
            playerHook=new PlayerHook((EntityPlayerSP) e);
            System.out.println("Looking Block = "+playerHook.getSensor().getFacingBlock(Integer.parseInt(args[0])>0,Integer.parseInt(args[1])>0,Integer.parseInt(args[2])>0)
                    +"\nHealth = "+playerHook.getSensor().getHealth()
            +"\n\nFacingRayTrace = "+playerHook.getSensor().getFacingRayTrace(Integer.parseInt(args[0])>0,Integer.parseInt(args[1])>0,Integer.parseInt(args[2])>0));
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
