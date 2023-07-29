package top.fpsmaster.data

import com.google.gson.JsonObject
import top.fpsmaster.FPSMaster
import top.fpsmaster.core.values.values.BooleanValue
import top.fpsmaster.core.values.values.ColorValue
import top.fpsmaster.core.values.values.ModeValue
import top.fpsmaster.core.values.values.NumberValue
import top.fpsmaster.core.values.values.TextValue

class Configure {
    fun getConfig(): String {
        val jsonObject = JsonObject();
        for (module in FPSMaster.INSTANCE.moduleManager?.modules!!.values) {
            val je = JsonObject()
            je.add("stage", FPSMaster.INSTANCE.gson.toJsonTree(module.stage))
            je.add("key", FPSMaster.INSTANCE.gson.toJsonTree(module.key))
            je.add("x", FPSMaster.INSTANCE.gson.toJsonTree(module.x))
            je.add("y", FPSMaster.INSTANCE.gson.toJsonTree(module.y))
            je.add("scale", FPSMaster.INSTANCE.gson.toJsonTree(module.scale))

            for (value in module.values) {
                when (value) {
                    is BooleanValue -> {
                        je.add(value.name, FPSMaster.INSTANCE.gson.toJsonTree(value.value))
                    }
                    is NumberValue -> {
                        je.add(value.name, FPSMaster.INSTANCE.gson.toJsonTree(value.value))
                    }
                    is ModeValue -> {
                        je.add(value.name, FPSMaster.INSTANCE.gson.toJsonTree(value.value))
                    }
                    is ColorValue -> {
                        val color = JsonObject()
                        color.addProperty("red", value.value.red)
                        color.addProperty("green", value.value.green)
                        color.addProperty("blue", value.value.blue)
                        color.addProperty("alpha", value.value.alpha)
                        je.add(value.name, color)
                    }
                    is TextValue -> {
                        je.add(value.name, FPSMaster.INSTANCE.gson.toJsonTree(value.value))
                    }
                }
            }
            jsonObject.add(module.name, je)
        }
        return FPSMaster.INSTANCE.gson.toJson(jsonObject)
    }

    fun setConfig(config: String) {
        val jsonObject = FPSMaster.INSTANCE.gson.fromJson(config, JsonObject::class.java)
        for (module in FPSMaster.INSTANCE.moduleManager?.modules!!.values) {
            val je = jsonObject.getAsJsonObject(module.name)
            FPSMaster.INSTANCE.moduleManager?.modules?.get(module.name)?.fromJson(je)
        }
    }
}