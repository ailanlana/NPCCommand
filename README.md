# NPC Command

让MOD[CustomNPCs]的NPC可以绑定命令

### 主要功能
- 玩家右键NPC执行命令
- 命令类型分为玩家执行和控制台执行
- 支持解析PAPI变量

### 命令
- /npcc add <NPC名称> <命令类型> <命令> 为指定NPC添加一条命令
- /npcc list 查看已经绑定命令的NPC列表
- /npcc debug 调试模式开关,可右键NPC查看NPC相关信息

### 前置
- Bukkit 1.7.10 or higher
- EverNifeCore plugin
- PlaceholderAPI plugin