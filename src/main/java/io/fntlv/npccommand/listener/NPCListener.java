package io.fntlv.npccommand.listener;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.locale.FCLocale;
import br.com.finalcraft.evernifecore.locale.LocaleMessage;
import io.fntlv.npccommand.NPCCommand;
import io.fntlv.npccommand.config.npccmd.NpcCMD;
import io.fntlv.npccommand.config.npccmd.NpcCMDHolder;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Optional;

public class NPCListener implements ECListener {

    @FCLocale(lang = "ZH_CN",text = "§7[§6NPC命令§7] §f当前实体为:%name%,不属于NPC,无法绑定命令")
    public static LocaleMessage NO_NPC;

    @FCLocale(lang = "ZH_CN",text = "§7[§6NPC命令§7] §f当前NPC名字为: %name%")
    public static LocaleMessage NPC_NAME;

    @FCLocale(lang = "ZH_CN", text = "§7[§6NPC命令§7] §f当前NPC绑定命令为: %cmd% §7(处于调试模式下的玩家右键NPC无法执行命令,关闭后可正常执行)")
    public static LocaleMessage NPC_COMMAND;

    @EventHandler
    public void onInteractNPC(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        Entity npc = event.getRightClicked();
        String type = npc.getType().name();

        if (!type.contains("CUSTOMNPCS")&&!type.contains("MOD_CUSTOM")) {
            if (NpcCMDHolder.debugPlayerList.contains(player.getUniqueId())) {
                NO_NPC
                        .addPlaceholder("%name%",type)
                        .send(player);
            }
            return;
        }

        String npcName = npc.getName();
        Optional<NpcCMD> optionalNpcCmd = Optional.ofNullable(NpcCMDHolder.npcCMDMap.get(npcName));

        if (NpcCMDHolder.debugPlayerList.contains(player.getUniqueId())) {
            NPC_NAME
                    .addPlaceholder("%name%", npcName)
                    .send(player);
            NPC_COMMAND
                    .addPlaceholder(
                            "%cmd%",
                            optionalNpcCmd.map(
                                    NpcCMD::getCommand
                            ).orElse("§c空")
                    )
                    .send(player);
            return;
        }

        optionalNpcCmd.ifPresent(npcCmd -> {
            String command = npcCmd.getCommand();
            if (NPCCommand.isEnablePAPI()){
                command = PlaceholderAPI.setPlaceholders(player,command);
            }
            Server server = Bukkit.getServer();
            switch (npcCmd.getCmdType()) {
                case CONSOLE:
                    server.dispatchCommand(Bukkit.getConsoleSender(), command);
                    break;
                case PLAYER:
                    server.dispatchCommand(player, command);
                    break;
            }
        });
    }
}
