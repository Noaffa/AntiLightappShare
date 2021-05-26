package com.yorin

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object ConfigStore:AutoSavePluginConfig("AntiLightAppShare"){
    @ValueDescription("插件总开关")
    val enable:Boolean by value(true)
    @ValueDescription("当发生错误时向该人报告，如为空则不报告")
    val reportQQ by value<Long>()
    @ValueDescription("是否启用卡片式回复")
    val xmlCard:Boolean by value(true)
    @ValueDescription("群规则，在列表里为禁用")
    val disableGroupList:MutableList<Long> by value()
}