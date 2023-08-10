package us.timinc.mc.cobblemon.capturexp

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.pokemon.experience.SidemodExperienceSource
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer
import net.fabricmc.api.ModInitializer
import us.timinc.mc.cobblemon.capturexp.config.CaptureXPConfig

object CaptureXP : ModInitializer {
    const val MOD_ID = "capture_xp"
    private lateinit var captureXPConfig: CaptureXPConfig

    override fun onInitialize() {
        AutoConfig.register(
            CaptureXPConfig::class.java,
            ::JanksonConfigSerializer
        )
        captureXPConfig = AutoConfig.getConfigHolder(CaptureXPConfig::class.java)
            .config

        CobblemonEvents.POKEMON_CAPTURED.subscribe { event ->
            val playerParty = Cobblemon.storage.getParty(event.player)
            val source = SidemodExperienceSource(MOD_ID)
            val targetPokemon = playerParty.firstOrNull { it != event.pokemon } ?: return@subscribe
            val experience = Cobblemon.experienceCalculator.calculate(
                BattlePokemon.safeCopyOf(targetPokemon),
                BattlePokemon.safeCopyOf(event.pokemon),
                captureXPConfig.multiplier
            )
            targetPokemon.addExperienceWithPlayer(event.player, source, experience)
        }
    }
}