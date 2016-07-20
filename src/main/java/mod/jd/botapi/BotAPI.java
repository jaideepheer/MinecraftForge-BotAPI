package mod.jd.botapi;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = BotAPI.MODID, version = BotAPI.VERSION, clientSideOnly = true, name = "BotAPI",acceptedMinecraftVersions = "[1.9,)", canBeDeactivated = true)
public class BotAPI
{
    public static final String MODID = "botapi";
    public static final String VERSION = "1.0";
    public PlayerMovementController pmc;

    @Mod.Instance(value = "instance")
    public static BotAPI instance = new BotAPI();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println("Initialising BotAPI");
        registerCommands();
        pmc = new PlayerMovementController();
    }

    private void registerCommands() {
        ClientCommandHandler.instance.registerCommand(new mod.jd.botapi.Commands.botCommand());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
