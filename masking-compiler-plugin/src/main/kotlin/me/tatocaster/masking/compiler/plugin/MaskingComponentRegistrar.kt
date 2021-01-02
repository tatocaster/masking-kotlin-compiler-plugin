package me.tatocaster.masking.compiler.plugin

import com.google.auto.service.AutoService
import me.tatocaster.masking.compiler.plugin.MaskingCommandLineProcessor.Companion.KEY_ANNOTATION
import me.tatocaster.masking.compiler.plugin.MaskingCommandLineProcessor.Companion.KEY_ENABLED
import me.tatocaster.masking.compiler.plugin.MaskingCommandLineProcessor.Companion.KEY_MASK
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.codegen.extensions.ExpressionCodegenExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(ComponentRegistrar::class)
class MaskingComponentRegistrar : ComponentRegistrar {
    override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
        if (configuration[KEY_ENABLED] != true) {
            return
        }

        val mask = checkNotNull(configuration[KEY_MASK])
        val annotation = checkNotNull(configuration[KEY_ANNOTATION])

        ClassBuilderInterceptorExtension.registerExtension(
            project,
            MaskingClassBuilderInterceptorExtension(maskingAnnotation = annotation)
        )

        /*val messageCollector = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
        messageCollector.let {
            ExpressionCodegenExtension.registerExtension(
                project,
                MaskingCodegenExtension(messageCollector, mask, annotation)
            )
        }*/
    }
}
