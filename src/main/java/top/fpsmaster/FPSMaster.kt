package top.fpsmaster

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.minecraft.client.Minecraft
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.jetbrains.annotations.NotNull
import top.fpsmaster.core.managers.CheckManager
import top.fpsmaster.core.managers.ModuleManager
import top.fpsmaster.core.script.ScriptManager
import top.fpsmaster.data.ConfigManager
import top.fpsmaster.data.Language
import top.fpsmaster.data.Theme
import top.fpsmaster.gui.font.FontLoader
import top.fpsmaster.gui.guiScreen.GuiCustom
import top.fpsmaster.gui.notification.NotificationsManager

class FPSMaster {
    val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    @JvmField
    var fontLoader: FontLoader? = null

    @JvmField
    var guiCustom: GuiCustom? = null

    @JvmField
    var language: Language? = null

    @JvmField
    var moduleManager: ModuleManager? = null

    @JvmField
    var configManager: ConfigManager? = null
    var scriptManager: ScriptManager? = null

    @JvmField
    var checkManager: CheckManager? = null

    @JvmField
    var theme: Theme = Theme()
    var mc: Minecraft? = null

    @JvmField
    @NotNull
    var notificationsManager: NotificationsManager? = null

    @JvmField
    var logger: Logger = LogManager.getLogger()

    @JvmField
    val description = " "

    fun initClient() {
        fontLoader = FontLoader()
        logger.info("[FPSMaster] Font Loaded")
        INSTANCE.moduleManager = ModuleManager()
        INSTANCE.moduleManager!!.init()
        INSTANCE.language = Language()
        logger.info("[FPSMaster] Languages Loaded")
        scriptManager = ScriptManager()
        scriptManager!!.onClientStart(this)
        logger.info("[FPSMaster] " + scriptManager!!.scripts.size.toString() + " scripts loaded")
        guiCustom = GuiCustom()
        logger.info("[FPSMaster] Gui Loaded")
        configManager = ConfigManager()
        logger.info("[FPSMaster] Config Loaded")
        checkManager = CheckManager()
        logger.info("[FPSMaster] Anti-Cheat Loaded")
        println(
            """
    嘉然吸引我的是小作文破防时那句 “大家要好好吃饭，每天都要开开心心的”是改变V圈的那句 “你们是在和sc聊天还是在和然然聊天”是提
    及被车时那句 “大家其实都是很善良的人”是被乌龟咬时那句 “说不定它是因为很喜欢我才咬我呢”是反驳弹幕的那句 “不许这样说了哈，我的嘉心糖都是很
    厉害的人”是回应问题的那句 “就每天都在想怎么可以让你们更开心一点”是故作坚强的那句 “我每天都很开心，我不开心的时候肯定会告诉你们的”是夸奖粉丝的
    那句 “你们在我心里永远都是黑夜中那颗最闪亮的星”是双向奔赴的那句 “我觉得每一次，每一次能单独跟嘉心糖见面的时间都很短，要好好珍惜”我喜欢的是嘉然
    的魂，她的温柔，她的坚强，她那种“我的孩子很怪，但我还是喜欢他们”的母性。不论中之人姓甚名谁，有何经历，作何模样，吸引我的，相信也是吸引嘉心糖们的
    ，是那颗会为鼠鼠流眼泪的心。而这颗心是不会变的。
    """.trimIndent()
        )
        logger.info("Service Loaded")
        mc = Minecraft.getMinecraft()
        notificationsManager = NotificationsManager()
        logger.info("Starting...")
    }

    companion object {
        @JvmField
        var INSTANCE = FPSMaster()
        const val CLIENT_NAME = "FPSMaster"
        const val VERSION = "release 2.52"
    }
}