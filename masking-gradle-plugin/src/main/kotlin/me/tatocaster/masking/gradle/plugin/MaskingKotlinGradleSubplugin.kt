package me.tatocaster.masking.gradle.plugin

import com.google.auto.service.AutoService
import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@AutoService(KotlinGradleSubplugin::class)
class MaskingKotlinGradleSubplugin : KotlinGradleSubplugin<AbstractCompile> {
    override fun apply(
        project: Project,
        kotlinCompile: AbstractCompile,
        javaCompile: AbstractCompile?,
        variantData: Any?,
        androidProjectHandler: Any?,
        kotlinCompilation: KotlinCompilation<KotlinCommonOptions>?
    ): List<SubpluginOption> {
        val extension = project.extensions.findByType(MaskingGradleExtension::class.java) ?: MaskingGradleExtension()

        /*if (!extension.enabled) {
            error("Masking plugin is not enabled")
        }*/

        if (extension.maskingAnnotation == PLUGIN_DEFAULT_ANNOTATION) {
            project.dependencies.add(
                "implementation",
                "me.tatocaster.masking:masking-compiler-plugin-annotation:$PLUGIN_VERSION"
            )
        }

        return listOf(
            SubpluginOption(key = "enabled", value = extension.enabled.toString()),
            SubpluginOption(key = "mask", value = extension.mask),
            SubpluginOption(key = "maskingAnnotation", value = extension.maskingAnnotation),
        )
    }

    override fun isApplicable(project: Project, task: AbstractCompile): Boolean {
        return project.plugins.hasPlugin(MaskingGradlePlugin::class.java)
    }

    override fun getCompilerPluginId(): String {
        return "masking-compiler-plugin"
    }

    override fun getPluginArtifact(): SubpluginArtifact {
        return SubpluginArtifact(
            "me.tatocaster.masking",
            "masking-compiler-plugin",
            PLUGIN_VERSION
        )
    }
}
