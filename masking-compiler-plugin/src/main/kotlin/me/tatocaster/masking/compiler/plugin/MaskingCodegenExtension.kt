package me.tatocaster.masking.compiler.plugin

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.codegen.ImplementationBodyCodegen
import org.jetbrains.kotlin.codegen.JvmKotlinType
import org.jetbrains.kotlin.codegen.StackValue
import org.jetbrains.kotlin.codegen.extensions.ExpressionCodegenExtension
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.kotlin.resolve.jvm.diagnostics.OtherOrigin
import org.jetbrains.kotlin.resolve.substitutedUnderlyingType
import org.jetbrains.org.objectweb.asm.Opcodes
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter

/**
 * A compiler code generation extension which generates custom `toString()` implementations for
 * `Masking` annotations.
 */
class MaskingCodegenExtension(
    private val messageCollector: MessageCollector,
    private val mask: String,
    private val annotation: String
) : ExpressionCodegenExtension {

    override fun applyProperty(
        receiver: StackValue,
        resolvedCall: ResolvedCall<*>,
        c: ExpressionCodegenExtension.Context
    ): StackValue? {
        messageCollector.logMessage("apply property")

        return super.applyProperty(receiver, resolvedCall, c)
    }

    override fun generateClassSyntheticParts(codegen: ImplementationBodyCodegen) {
        val targetClass = codegen.descriptor
        val maskingAnnotationExists = targetClass.annotations.hasAnnotation(FqName("Masking"))
        messageCollector.logMessage("Reading ${targetClass.name} with annotation: $maskingAnnotationExists")

        val mv = codegen.v.newMethod(
            JvmDeclarationOrigin.NO_ORIGIN,
            Opcodes.ACC_PUBLIC,
            "toString",
            "(Ljava/lang/Object;)V",
            null,
            null
        )
        val type = JvmKotlinType(
            codegen.typeMapper.mapType(codegen.descriptor),
            codegen.descriptor.defaultType.substitutedUnderlyingType()
        ).type
        val typeDescriptor = type.descriptor

        // TODO generate toString() for masked properties
        InstructionAdapter(mv).apply {
        }
    }
}

fun MessageCollector.logMessage(message: String) {
    report(
        CompilerMessageSeverity.LOGGING,
        "MASKING plugin: $message",
        CompilerMessageLocation.create(null)
    )
}