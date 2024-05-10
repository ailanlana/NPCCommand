package io.fntlv.npccommand.config.npccmd;

import io.fntlv.npccommand.config.npccmd.type.CMDType;
import io.fntlv.npccommand.message.Message;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class NpcCMD {
    private final String npcName;
    private final String command;
    private final CMDType cmdType;

    public NpcCMD(String npcName,String command,CMDType cmdType){
        this.npcName = npcName;
        this.command = command;
        this.cmdType = cmdType;
    }

    public String getCommand() {
        return command;
    }

    public String getNpcName() {
        return npcName;
    }

    public CMDType getCmdType() {
        return cmdType;
    }

    public String showText(Player player){
        return Message.NPC_CMD_SHOW
                .addPlaceholder("%name%",npcName)
                .addPlaceholder("%cmd%",command)
                .addPlaceholder("%type%",cmdType.getName())
                .getFancyText(player)
                .getText();
    }

    public String serialize(){
        return npcName +"|"+cmdType+":"+command;
    }

    public static NpcCMD deserialize(String serializedString) {
        String[] split = serializedString.split(Pattern.quote("|"));

        if (split.length != 2) {
            return null;
        }
        String npcName = split[0];
        String[] cmdParts = split[1].split(":");

        if (cmdParts.length != 2) {
            return null;
        }

        String cmdTypeName = cmdParts[0];
        String command = cmdParts[1];

        CMDType cmdType = CMDType.valueOf(cmdTypeName);

        return new NpcCMD(npcName, command, cmdType);
    }

}
