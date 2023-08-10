package us.timinc.mc.cobblemon.capturexp.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment
import us.timinc.mc.cobblemon.capturexp.CaptureXP

@Config(name = CaptureXP.MOD_ID)
class CaptureXPConfig : ConfigData {
    @Comment("The multiplier for the bonus experience")
    val multiplier = 1.0
}