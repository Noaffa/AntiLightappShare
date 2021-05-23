package com.yorin

import com.google.gson.Gson
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.SimpleServiceMessage
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.message.data.messageChainOf
import net.mamoe.mirai.utils.MiraiExperimentalApi


object AntiLightappShare:KotlinPlugin(
    JvmPluginDescription(
        id = "org.yorin.anti-lightappshare",
        name = "AntiLightappShare",
        version = "0.13.4"
    )
){
    @OptIn(MiraiExperimentalApi::class)
    override fun onEnable() {
        logger.info("反小程序分享插件载入")
        ConfigStore.reload()

        CommandManager.registerCommand(AntiLightappSettingCommand)
        AbstractPermitteeId.AnyMemberFromAnyGroup.permit(AntiLightappShare.permissionId("command.als"))//给所有群的所有人添加控制当前群开关的命令权限

        globalEventChannel().subscribeAlways<GroupMessageEvent>{
            if(!ConfigStore.enable)return@subscribeAlways
            if(ConfigStore.groupRule[group.id] == false)return@subscribeAlways
            if(this.message.serializeToMiraiCode().startsWith("""[mirai:app""")){
                val gotRawData=this.message.content
                try {
                    val jsonData = Gson().fromJson(gotRawData,MiniAppJsonData::class.java)
                    if(jsonData.app=="com.tencent.miniapp_01"&&jsonData.meta.detail_1.appid=="1109937557"){//Bilibili小程序ID
                        if(ConfigStore.xmlCard){//XML卡片
                            val xmlData=XmlDataReplace(jsonData)
                            this.group.sendMessage(messageChainOf(SimpleServiceMessage(60,xmlData)))
                            logger.info("xml卡片发送完成")
                        }else{//直接删减多余信息后输出地址
                            this.group.sendMessage("该视频地址为 ${jsonData.meta.detail_1.qqdocurl.substringBefore('?')}")
                            logger.info("提取的信息发送完成")
                        }
                    }
                }catch (e:Exception){
                    logger.error("Json解析失败")
                    this.bot.getFriend(ConfigStore.reportQQ)?.sendMessage("[Error]群${this.group.name}(${this.group.id})的Json解析失败，原因[${e.message}]")
                }
            }
        }
    }
}

object AntiLightappSettingCommand: CompositeCommand(
    AntiLightappShare,"als","小程序转发设置"
){
    @SubCommand("enable","启用")
    suspend fun CommandSender.enableThisGroup(){
        setTargetGroup(this,true)
    }
    @SubCommand("disable","禁用")
    suspend fun CommandSender.disableThisGroup(){
        setTargetGroup(this,false)
    }
    private suspend fun setTargetGroup(commandSender: CommandSender, bool: Boolean) {
        val group=commandSender.getGroupOrNull()
        if (group != null) {
            ConfigStore.groupRule[group.id]=bool
            if(bool)group.sendMessage("启用本群小程序分享消息的解析")
            else group.sendMessage("禁用本群小程序分享消息的解析")
        }
    }
}
