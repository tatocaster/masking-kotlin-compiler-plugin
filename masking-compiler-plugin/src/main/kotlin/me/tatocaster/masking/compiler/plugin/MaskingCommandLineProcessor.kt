package me.tatocaster.masking.compiler.plugin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

@AutoService(CommandLineProcessor::class)
class MaskingCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String = "masking-compiler-plugin"
    override val pluginOptions: Collection<AbstractCliOption> = listOf(
        CliOption(
            optionName = "enabled",
            valueDescription = "<true|false>",
            required = true,
            description = "Option to enable or disable this plugin"
        ),
        CliOption(
            optionName = "mask",
            valueDescription = "String",
            required = false,
            description = "any string which will replace the original one"
        ),
        CliOption(
            optionName = "maskingAnnotation",
            valueDescription = "String",
            required = false,
            allowMultipleOccurrences = true,
            description = "annotation name"
        )
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        super.processOption(option, value, configuration)
        when (option.optionName) {
            "enabled" -> {
                configuration.put(KEY_ENABLED, value.toBoolean())
            }
            "mask" -> {
                configuration.put(KEY_MASK, value)
            }
            "maskingAnnotation" -> {
                configuration.put(KEY_ANNOTATION, value)
            }
            else -> {
                error("Unknown plugin option: ${option.optionName}")
            }
        }
    }

    companion object {
        val KEY_ENABLED = CompilerConfigurationKey<Boolean>("enabled")
        val KEY_MASK = CompilerConfigurationKey<String>("mask")
        val KEY_ANNOTATION = CompilerConfigurationKey<String>("maskingAnnotation")
    }
}
