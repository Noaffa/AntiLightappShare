package com.yorin

data class MiniAppJsonData(
    var app:String,//程序ID
    var desc:String,//应用名
    var prompt:String,
    var meta:MetaData,

)
data class MetaData(
    var detail_1:MetaData_detail_1Data
)
data class MetaData_detail_1Data(
    var appid:String,
    var desc:String,
    var icon:String,
    var preview:String,
    var qqdocurl:String,
    var title:String,
)

fun XmlDataReplace(jsonData: MiniAppJsonData):String{//传入json数据，输出xml信息
    var previewUrl=jsonData.meta.detail_1.preview
    if(!previewUrl.startsWith("http"))previewUrl= "http://$previewUrl"//如果无http则添加
    return """<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID="1"  templateID="123" action="web" brief="${jsonData.prompt}" sourceMsgId="0" url="${jsonData.meta.detail_1.qqdocurl.substringBefore('?')}" flag="0" adverSign="0" multiMsgFlag="0"><item layout="2"><picture cover="$previewUrl" w="0" h="0" /><title>哔哩哔哩</title><summary>${jsonData.meta.detail_1.desc}</summary></item><source name="${jsonData.meta.detail_1.title}" icon="${jsonData.meta.detail_1.icon}" url="${jsonData.meta.detail_1.qqdocurl.substringBefore('?')}" action="web" a_actionData="tencent0://" appid="0" /></msg>"""
}