package io.fntlv.npccommand.config;


import br.com.finalcraft.evernifecore.config.Config;
import io.fntlv.npccommand.config.npccmd.NpcCMD;
import io.fntlv.npccommand.config.npccmd.NpcCMDHolder;
import io.fntlv.npccommand.config.npccmd.NpcCMDManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.omg.CORBA.PUBLIC_MEMBER;

public class ConfigManager {

    private static Config mainConfig;

    public static Config getMainConfig() {
        return mainConfig;
    }

    public static void init(JavaPlugin javaPlugin){
        mainConfig = new Config(javaPlugin,"config.yml",false);
        mainConfig.setValue("version","1.0.1","版本号,请勿修改");
        NpcCMDManager.init();
        mainConfig.save();
    }

}
