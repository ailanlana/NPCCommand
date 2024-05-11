package io.fntlv.npccommand.config.npccmd;

import br.com.finalcraft.evernifecore.config.yaml.section.ConfigSection;
import io.fntlv.npccommand.NPCCommand;
import io.fntlv.npccommand.config.ConfigManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NpcCMDManager {

    public static void init(){
        loadNpcCMD();
    }

    private static void loadNpcCMD(){
        ConfigSection section = ConfigManager.getMainConfig().getConfigSection("NPCCommands");
        int line = 0;
        for (String cmdString : section.getOrSetDefaultValue(null, new ArrayList<String>(),"NPC命令")) {
            line++;
            try {
                addNpcCMD(NpcCMD.deserialize(cmdString));
            } catch (Exception e){
                NPCCommand.warn("&c配置文件config节点[NPCCommands]第"+line+"个NPC命令配置出现错误!");
            }
        }
    }

    public static void removeNpcCMD(NpcCMD npcCMD){
        NpcCMDHolder.npcCMDMap.remove(npcCMD.getNpcName());
        ConfigManager.getMainConfig().setValue(
                "NPCCommands",
                NpcCMDHolder.npcCMDMap.values().stream()
                        .map(NpcCMD::serialize)
                        .collect(Collectors.toList())
        );
        ConfigManager.getMainConfig().saveAsync();
    }

    public static void addNpcCMD(NpcCMD npcCMD){
        NpcCMDHolder.npcCMDMap.put(npcCMD.getNpcName(),npcCMD);
        ConfigManager.getMainConfig().setValue(
                "NPCCommands",
                NpcCMDHolder.npcCMDMap.values().stream()
                        .map(NpcCMD::serialize)
                        .collect(Collectors.toList())
        );
        ConfigManager.getMainConfig().saveAsync();
    }
}
