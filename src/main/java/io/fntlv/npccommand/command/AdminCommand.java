package io.fntlv.npccommand.command;

import br.com.finalcraft.evernifecore.argumento.MultiArgumentos;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.Arg;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.evernifecore.locale.FCLocale;
import br.com.finalcraft.evernifecore.locale.LocaleMessage;
import br.com.finalcraft.evernifecore.pageviwer.PageViewer;
import br.com.finalcraft.evernifecore.util.FCCommandUtil;
import io.fntlv.npccommand.PermissionNode;
import io.fntlv.npccommand.config.npccmd.NpcCMD;
import io.fntlv.npccommand.config.npccmd.NpcCMDHolder;
import io.fntlv.npccommand.config.npccmd.NpcCMDManager;
import io.fntlv.npccommand.config.npccmd.type.CMDType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@FinalCMD(
        aliases = "npcc",
        permission = PermissionNode.ADMIN_COMMAND
)
public class AdminCommand {

    @FCLocale(lang = "ZH_CN", text = "§7[§6NPC命令§7] §f调试模式已%status%")
    public static LocaleMessage DEBUG_SWITCH;

    @FCLocale(lang = "ZH_CN",text = "§7[§6NPC命令§7] §fNPC: %name% 已经绑定命令: %cmd%&7(%type%)")
    public static LocaleMessage NPC_HAS_COMMAND;

    @FCLocale(lang = "ZH_CN",text = "§7[§6NPC命令§7] §f如果你想覆盖该NPC的命令, §7[§a点击这里§7]")
    public static LocaleMessage IF_YOU_WANNA_OVERRIDE_CLICK_HERE;

    @FCLocale(lang = "ZH_CN",text = "§7[§6NPC命令§7] §f成功向NPC: %name% 绑定命令: %cmd%&7(%type%)")
    public static LocaleMessage NPC_COMMAND_ADD_SUCCESS;


    @FinalCMD.SubCMD(
            subcmd = "debug"
    )
    public void debugCommand(Player player){
        List<UUID> debugPlayerList = NpcCMDHolder.debugPlayerList;
        if (debugPlayerList.contains(player.getUniqueId())){
            debugPlayerList.remove(player.getUniqueId());
            DEBUG_SWITCH
                    .addPlaceholder("%status%","§c关闭")
                    .send(player);
        } else {
            debugPlayerList.add(player.getUniqueId());
            DEBUG_SWITCH
                    .addPlaceholder("%status%","§a开启")
                    .send(player);
        }
    }


    @FinalCMD.SubCMD(
            subcmd = "add"
    )
    public void add(
            Player player,
            MultiArgumentos argumentos,
            @Arg(name = "<NPC名称>")String name,
            @Arg(name = "<控制台or玩家>")CMDType cmdType,
            @Arg(name = "<命令>")String command
    ){
        String finalCommand = argumentos.joinStringArgs(3);

        NpcCMD npcCMD = NpcCMDHolder.npcCMDMap.get(name);

        if (npcCMD != null){
            NPC_HAS_COMMAND
                    .addPlaceholder("%name%",npcCMD.getNpcName())
                    .addPlaceholder("%cmd%",npcCMD.getCommand())
                    .addPlaceholder("%type%",npcCMD.getCmdType().getName())
                    .send(player);

            IF_YOU_WANNA_OVERRIDE_CLICK_HERE
                    .addAction(FCCommandUtil.dynamicCommand(()->{
                        NpcCMDManager.removeNpcCMD(npcCMD);
                        add(player,argumentos,name,cmdType,finalCommand);
                    }))
                    .send(player);
            return;
        }
        NpcCMD finalNpcCMD = new NpcCMD(name,finalCommand,cmdType);
        NpcCMDManager.addNpcCMD(finalNpcCMD);

        NPC_COMMAND_ADD_SUCCESS
                .addPlaceholder("%name%",finalNpcCMD.getNpcName())
                .addPlaceholder("%cmd%",finalNpcCMD.getCommand())
                .addPlaceholder("%type%",finalNpcCMD.getCmdType().getName())
                .send(player);
    }

    @FinalCMD.SubCMD(
            subcmd = "list"
    )
    public void list(Player player, @Arg(name = "[页数]", context = "[0:*]") Integer page){
        PageViewer.targeting(NpcCMD.class)
                .withSuplier(()-> new ArrayList<>(NpcCMDHolder.npcCMDMap.values()))
                .extracting(npcCMD -> npcCMD.showText(player))
                .setFormatHeader(" §7[§bNPC命令列表§7] " + "\n")
                .setFormatLine(
                        FancyText.of("§7#  %number%:  ")
                                .append("§c[移除]")
                                .setHoverText("§c点击删除")
                                .setRunCommandAction("%remove_npc_command%")
                                .append("§a %value%")
                )
                .addPlaceholder("%remove_npc_command%", npcCMD -> FCCommandUtil.dynamicCommand(() -> {
                    NpcCMDManager.removeNpcCMD(npcCMD);
                    list(player, page);
                }))
                .setPageSize(5)
                .build()
                .send(page,player);
    }

}
