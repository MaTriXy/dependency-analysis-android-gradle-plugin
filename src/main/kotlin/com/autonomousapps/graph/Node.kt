package com.autonomousapps.graph

/**
 * TODO.
 */
sealed class Node(
  val identifier: String
) {

  override fun toString(): String = identifier

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Node

    if (identifier != other.identifier) return false

    return true
  }

  override fun hashCode(): Int {
    return identifier.hashCode()
  }
}

/**
 * The project under analysis. It "consumes" its dependencies.
 */
class ConsumerNode(
  identifier: String
) : Node(identifier) {

}

/**
 * A dependency. May be a project or an external binary.
 */
class ProducerNode(
  identifier: String
) : Node(identifier) {

}
