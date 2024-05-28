package io.fntlv.npccommand;

import br.com.finalcraft.evernifecore.ecplugin.annotations.ECPlugin;
import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.locale.FCLocaleManager;
import io.fntlv.npccommand.command.CommandRegister;
import io.fntlv.npccommand.config.ConfigManager;
import io.fntlv.npccommand.listener.NPCListener;
import io.fntlv.npccommand.message.Message;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

@ECPlugin(
        bstatsID = "22035"
)
public final class NPCCommand extends JavaPlugin {

    private static JavaPlugin inst;
    private static boolean enablePAPI;

    public static void info(String msg){
        inst.getLogger().info("[NPCCommand][Info] " + msg.replace("&","§"));
    }

    public static void warn(String msg){
        inst.getLogger().info("[NPCCommand][warn] " + msg.replace("&","§"));
    }

    @Override
    public void onEnable() {
        inst = this;
        info("&6NPCCommand插件开始加载...");
        CommandRegister.init(this);
        info("&a命令注册成功");
        ConfigManager.init(this);;
        FCLocaleManager.loadLocale(this, Message.class);
        info("&a配置初始化成功");
        initPAPI();
        ECListener.register(this, NPCListener.class);
        info("&a监听器注册成功");
        info("&f插件作者: &bFnTlv &fQQ: &b1781872216");
        info("&f插件交流群: &a539651313");
    }

    @Override
    public void onDisable() {

    }

    public void initPAPI(){
        Plugin placeholderAPI = getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (placeholderAPI != null){
            enablePAPI = true;
            info("&aPAPI对接成功");
        } else {
            enablePAPI = false;
            info("&cPAPI对接失败");
        }
    }

    public static JavaPlugin getInst() {
        return inst;
    }

    public static boolean isEnablePAPI() {
        return enablePAPI;
    }
}
