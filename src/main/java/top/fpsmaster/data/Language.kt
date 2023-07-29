package top.fpsmaster.data

import top.fpsmaster.FPSMaster
import top.fpsmaster.core.ModuleCategory

class Language {
    @JvmField
    var texts: MutableMap<String, String> = HashMap()

    init {
        //实例
        /*
        UI语言命名规则
        显示的界面.显示的按钮/文本
        功能命名规则
        功能名: mod.功能名
        功能描述: mod.功能名.desc
        功能设置: mod.功能名.设置名
        功能设置描述: mod.功能名.设置名.desc
        通用翻译: global.随便命名个id，不能重复(会在所有客户的显示的文字处调用)
         */
        texts["language.done"] = "Done"
        texts["language.import"] = "Import"
        texts["clickgui.custom"] = "Custom"
        texts["gui.login.done"] = "Done"
        texts["gui.login.cancel"] = "Cancel"
        texts["mainmenu.info"] = "Made by FPSMasterTeam"
        for ((_, value) in FPSMaster.INSTANCE.moduleManager!!.modules) {
            texts["mod." + value.name] = value.name
            texts["mod." + value.name + ".desc"] = value.desc
            for (v in value.values) {
                texts["mod." + value.name + "." + v.name] = v.name
                texts["mod." + value.name + "." + v.name + ".desc"] = ""
            }
        }
        loadScriptLanguage()
        for (m in ModuleCategory.values()) {
            texts["type." + m.name] = m.name
        }
    }

    fun loadScriptLanguage() {
        for ((_, value) in FPSMaster.INSTANCE.moduleManager!!.scriptModules) {
            texts["mod." + value.scriptModule.name] = value.scriptModule.name
            texts["mod." + value.scriptModule.name + ".desc"] = value.scriptModule.desc
            for (v in value.scriptModule.values) {
                texts["mod." + value.name + "." + v.name] = v.name
                texts["mod." + value.name + "." + v.name + ".desc"] = ""
            }
        }
    }

    fun removeScriptLanguage() {
        for ((_, value) in FPSMaster.INSTANCE.moduleManager!!.scriptModules) {
            texts.remove("mod." + value.scriptModule.name)
            texts.remove("mod." + value.scriptModule.name + ".desc")
            for (v in value.scriptModule.values) {
                texts.remove("mod." + value.name + "." + v.name)
                texts.remove("mod." + value.name + "." + v.name + ".desc")
            }
        }
    }
}