# AntiLightappShare

第一个kotlin程序，练手用

插件作用就是为了让不支持小程序的QQ客户端能够知道通过小程序转发的B站视频是什么


## 配置文件内容如下：
```
# 插件总开关
enable: true
# 当发生错误时尝试向该Q号好友报告，如为0或无法根据当前机器人找到该Q号好友则不报告
reportQQ: 0
# 是否启用卡片式回复
xmlCard: true
# 群规则，如果不在规则里默认为启用
groupRule: 
  xxxxxxxx: true
```

##要有[chat-command](https://github.com/project-mirai/chat-command)插件才能在群里使用命令

## 命令：  
```
/als disable
/als 禁用
/als enable
/als 启用

设置当前所在群的规则，如果不是群则无反应
```



## 相关：  
[chat-command](https://github.com/project-mirai/chat-command)


[mirai-console](https://github.com/mamoe/mirai-console)


[mirai](https://github.com/mamoe/mirai)  