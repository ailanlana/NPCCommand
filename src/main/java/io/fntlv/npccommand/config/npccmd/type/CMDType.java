package io.fntlv.npccommand.config.npccmd.type;

public enum CMDType {
    CONSOLE("控制台执行命令"),
    PLAYER("玩家执行命令");

    private final String name;

    CMDType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
