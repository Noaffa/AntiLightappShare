import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader

suspend fun main(){
    MiraiConsoleTerminalLoader.startAsDaemon();
    AntiLightappShare.load();
    AntiLightappShare.enable();


    val bot = MiraiConsole.addBot(123456789,"nya"){
        fileBasedDeviceInfo();
    }.alsoLogin();


    MiraiConsole.job.join();
}