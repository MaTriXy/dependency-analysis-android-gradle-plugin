@file:Suppress("UnstableApiUsage")

package com.autonomousapps.internal

import org.gradle.api.GradleException
import org.gradle.api.artifacts.component.ComponentIdentifier
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.api.artifacts.component.ProjectComponentIdentifier
import java.util.*

internal fun String.capitalize() = substring(0, 1).toUpperCase(Locale.ROOT) + substring(1)

internal fun ComponentIdentifier.asString(): String {
    return when (this) {
        is ProjectComponentIdentifier -> projectPath
        is ModuleComponentIdentifier -> moduleIdentifier.toString()
        // OpaqueComponentArtifactIdentifier implements ComponentArtifactIdentifier, ComponentIdentifier
//        is ComponentArtifactIdentifier -> toString()
        else -> throw GradleException("Cannot identify ComponentIdentifier subtype. Was ${javaClass.simpleName}, named $this")
    }
}

internal fun ComponentIdentifier.resolvedVersion(): String? {
    return when (this) {
        is ProjectComponentIdentifier -> null
        is ModuleComponentIdentifier -> version
        else -> throw GradleException("Cannot identify ComponentIdentifier subtype. Was ${javaClass.simpleName}, named $this")
    }
}

// Begins with an 'L'
// followed by at least one word character
// followed by one or more word char, /, or $, in any combination
// ends with a ';'
// Not perfect, but probably close enough
internal val METHOD_DESCRIPTOR_REGEX = """L\w[\w/$]+;""".toRegex()

// TODO sync with above. Note this has a capturing group.
internal val DESC_REGEX = """L(\w[\w/$]+);""".toRegex()

// This regex matches a Java FQCN.
// https://stackoverflow.com/questions/5205339/regular-expression-matching-fully-qualified-class-names#comment5855158_5205467
internal val JAVA_FQCN_REGEX =
    "(\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*\\.)+\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*".toRegex()
