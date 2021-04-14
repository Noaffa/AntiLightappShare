# AntiLightappShare

第一个kotlin程序，练手用

插件作用就是为了让不支持小程序的QQ客户端能够知道通过小程序转发的B站视频是什么

## 启动前需要设置配置文件，如果没有配置文件启动一次就好(x

## 配置文件内容如下：


```
mainSwitch: true
targetGroup: 
  1234567: true
  2345678: true
  3456789: true
otherGroupTurnOn: false
replyXml: false
masterQQid: 0
debugMode: false
```

## 说明：  
mainSwitch是总开关，控制整个插件的启用与否  
targetGroup是针对某个群的设置，按格式来就行  
otherGroupTurnOn是对targetGroup没有设置的其他群的控制  
replyXml是是否启用xml卡片的开关，由于SimpleServiceMessage并不稳定，可能不久就会失效，算是玩具吧  
masterQQid是管理人的QQ号，通过getFriend发送解析json失败的消息给管理人  
debugMode是debug信息的开关  
