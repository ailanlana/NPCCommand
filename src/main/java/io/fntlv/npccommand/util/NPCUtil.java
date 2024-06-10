package io.fntlv.npccommand.util;

import org.bukkit.inventory.ItemStack;

public class NPCUtil {

    public static boolean isNPCItem(ItemStack itemStack){
        String name = itemStack.getType().name();
        return name.contains("CUSTOMNPCS") || name.contains("MOD_CUSTOM");
    }

}
