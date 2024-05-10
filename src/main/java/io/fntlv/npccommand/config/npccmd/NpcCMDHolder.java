package io.fntlv.npccommand.config.npccmd;

import io.fntlv.npccommand.NPCCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NpcCMDHolder {

    public static HashMap<String, NpcCMD> npcCMDMap = new HashMap<>();
    public static List<UUID> debugPlayerList = new ArrayList<>();
}
