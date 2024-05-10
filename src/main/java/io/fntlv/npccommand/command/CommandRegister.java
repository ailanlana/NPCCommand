package io.fntlv.npccommand.command;

import br.com.finalcraft.evernifecore.commands.finalcmd.FinalCMDManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegister {

    public static void init(JavaPlugin plugin){
        FinalCMDManager.registerCommand(plugin,AdminCommand.class);
    }

}
