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
    val videoUrl = jsonData.meta.detail_1.qqdocurl.substringBefore('?')
    var previewUrl=jsonData.meta.detail_1.preview.substringBefore('?')
    if(!previewUrl.startsWith("http"))previewUrl= "http://$previewUrl"//如果无http则添加
    return """<?xml version='1.0' encoding='UTF-8' standalone='yes'?><msg templateID="123" url="$videoUrl" serviceID="1" action="web" actionData="" a_actionData="" i_actionData="" brief="${jsonData.prompt}" flag="0"><item layout="2"><picture cover="$previewUrl"/><title>哔哩哔哩</title><summary>${jsonData.meta.detail_1.desc}</summary></item><source url="$videoUrl" icon="${jsonData.meta.detail_1.icon}" name="${jsonData.meta.detail_1.title}" appid="0" action="web" actionData="" a_actionData="tencent0://" i_actionData=""/></msg>"""
}