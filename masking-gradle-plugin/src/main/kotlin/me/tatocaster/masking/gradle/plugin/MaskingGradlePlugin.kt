package me.tatocaster.masking.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MaskingGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {

        /**
         * Enable this plugin and configure
         * maskingGradlePlugin {
         *     enabled = true,
         *     mask = "*",
         *     maskingAnnotation = ""
         * }
         */
        project.extensions.create(
            "maskingGradlePlugin",
            MaskingGradleExtension::class.java
        )
    }
}
