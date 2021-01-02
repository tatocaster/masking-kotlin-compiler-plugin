package me.tatocaster.masking.gradle.plugin

open class MaskingGradleExtension {
    var enabled: Boolean = true
    var mask: String = "*"
    var maskingAnnotation: String = PLUGIN_DEFAULT_ANNOTATION
}

const val PLUGIN_DEFAULT_ANNOTATION = "me.tatocaster.masking.annotation.Masking"