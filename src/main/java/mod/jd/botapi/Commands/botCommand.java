package mod.jd.botapi.Commands;
import mod.jd.botapi.BotAPI;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class botCommand implements ICommand {
    /**
     * Gets the name of the command
     */
    @Override
    public String getName(){
        return "bot";
    }

    /**
     * Gets the usage string for the command.
     */
    @Override
    public String getUsage(ICommandSender sender){
        return "Main BotAPI command.";
    }

    @Override
    public List<String> getAliases(){
        ArrayList<String> l = new ArrayList<String>();
        l.add("botapi");
        return l;
    }

    /**
     * Callback for when the command is executed
     *
     * @param server The server instance
     * @param sender The sender who executed the command
     * @param args The arguments that were passed
     */
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        float a=0,b=0,c=0;
        try
        {
            a=Float.parseFloat(args[1]);
            b=Float.parseFloat(args[2]);
            if(args.length>3)c=Float.parseFloat(args[3]);
            if(Integer.parseInt(args[0])==1) {
                BotAPI.INSTANCE.pmc.moveStraightTo(new BlockPos(a,b,c));
            }
            else if(Integer.parseInt(args[0])==2)
            {
                BotAPI.INSTANCE.pmc.faceTowards(a,b,c);
            }
            else if(Integer.parseInt(args[0])==3)
            {
                BotAPI.INSTANCE.pmc.setAngles(a,b);
            }
        }
        catch (Exception e)
        {
            BotAPI.INSTANCE.pmc.resetPlayerInput();
        }
    }

    /**
     * Check if the given ICommandSender has permission to execute this command
     *
     * @param server The server instance
     * @param sender The ICommandSender to check permissions on
     */
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender){
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     *
     * @param args The arguments of the command invocation
     * @param index The index
     */
    @Override
    public boolean isUsernameIndex(String[] args, int index){
        return false;
    }

    @Override
    public int compareTo(ICommand o){
        return 0;
    }
}
