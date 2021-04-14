import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object ConfigStore:AutoSavePluginConfig("AntiLightAppShare"){
    val mainSwitch:Boolean by value(true);
    val targetGroup:Map<Long,Boolean> by value(mapOf(Pair(100,false),Pair(200,false)));
    val otherGroupTurnOn:Boolean by value(false);
    val replyXml:Boolean by value(true);
    val masterQQid by value<Long>();
    val debugMode:Boolean by value(false);
}