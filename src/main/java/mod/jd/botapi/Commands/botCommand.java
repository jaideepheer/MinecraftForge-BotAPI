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
    public String getCommandName(){
        return "bot";
    }

    /**
     * Gets the usage string for the command.
     */
    public String getCommandUsage(ICommandSender sender){
        return "Main BotAPI command.";
    }

    public List<String> getCommandAliases(){
        ArrayList<String> l = new ArrayList<String>();
        l.add("botapi");
        return l;
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        float a=0,b=0,c=0;
        try
        {
            a=Float.parseFloat(args[1]);
            b=Float.parseFloat(args[2]);
            if(args.length>3)c=Float.parseFloat(args[3]);
            if(Integer.parseInt(args[0])==1) {
                BotAPI.instance.pmc.moveStraightTo(new BlockPos(a,b,c));
            }
            else if(Integer.parseInt(args[0])==2)
            {
                BotAPI.instance.pmc.faceTowards(a,b,c);
            }
            else if(Integer.parseInt(args[0])==3)
            {
                BotAPI.instance.pmc.setAngles(a,b);
            }
        }
        catch (Exception e)
        {
            BotAPI.instance.pmc.resetPlayerInput();
        }
    }

    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    public boolean checkPermission(MinecraftServer server, ICommandSender sender){
        return true;
    }

    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos){
        return null;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int index){
        return false;
    }

    public int compareTo(ICommand o){
        return 0;
    }
}
