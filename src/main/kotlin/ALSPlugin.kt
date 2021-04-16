import com.google.gson.Gson
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.SimpleServiceMessage
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.message.data.messageChainOf


object AntiLightappShare:KotlinPlugin(
    JvmPluginDescription(
        id = "org.yorin.anti-lightappshare",
        name = "AntiLightappShare",
        version = "0.10.4"
    )
){
    override fun onEnable() {
        logger.info("AntiLightappShare插件载入")
        ConfigStore.reload();

        if(!ConfigStore.mainSwitch) logger.info("AntiLightappShare未开启，请开启前更改配置文件")
        else{
            logger.info("AntiLightappShare启动")

            globalEventChannel().subscribeAlways<GroupMessageEvent> {
                if(ConfigStore.debugMode)logger.warning("群${this.group.id}的Key是${ConfigStore.targetGroup[this.group.id]}")
                if (!ConfigStore.targetGroup.containsKey(this.group.id)){
                    if(!ConfigStore.otherGroupTurnOn)return@subscribeAlways
                }else if(ConfigStore.targetGroup[this.group.id] != true)return@subscribeAlways
                if(this.message.serializeToMiraiCode().startsWith("""[mirai:app""")){
                    if(ConfigStore.debugMode)logger.warning("获取的信息开头符合规则")
                    val gotRawData=this.message.content;
                    try {
                        if(ConfigStore.debugMode)logger.info("获取到b站小程序转发信息，开始拆分")
                        val jsonData = Gson().fromJson(gotRawData,MiniAppJsonData::class.java)
                        if(jsonData.app!="com.tencent.miniapp_01"||jsonData.meta.detail_1.appid!="1109937557"){
                            if(ConfigStore.debugMode)logger.warning("json解析结果非b站小程序分享")
                            return@subscribeAlways
                        }
                        else{
                            if(ConfigStore.replyXml){
                                if(ConfigStore.debugMode)logger.info("组合xml卡片")
                                val xmlData=XmlDataReplace(jsonData);
                                this.group.sendMessage(messageChainOf(SimpleServiceMessage(60,xmlData)))
                                logger.info("xml卡片发送完成");
                            }else{
                                this.group.sendMessage("该视频地址为 ${jsonData.meta.detail_1.qqdocurl.substringBefore('?')}");
                                logger.info("提取的信息发送完成");
                            }
                        }
                    }catch (e:Exception){
                        logger.error("Json解析失败");
                        this.bot.getFriend(ConfigStore.masterQQid)?.sendMessage("[Error]群${this.group.name}(${this.group.id})的Json解析失败，原因[${e.message}]")
                    }
                }
            }


        }
    }
}
