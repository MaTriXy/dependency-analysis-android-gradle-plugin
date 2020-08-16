package com.autonomousapps.graph

/**
 * TODO.
 *
 * With inspiration from [Algorithms](https://algs4.cs.princeton.edu/42digraph/Digraph.java.html).
 */
class DependencyGraph(
  val nodeCount: Int
) {

  init {
    if (nodeCount < 0) throw IllegalArgumentException("Number of vertices in a DependencyGraph must be non-negative")
  }

  var edgeCount: Int = 0
    private set

  private val adj = LinkedHashMap<Node, MutableCollection<Node>>(nodeCount)//Array<Collection<Node>>(nodeCount) { emptyList() }
  private val inDegree = LinkedHashMap<Node, Int>(nodeCount)

  /**
   * Adds the directed edge v→w to this digraph.
   *
   * @param  v the tail vertex
   * @param  w the head vertex
   */
  fun addEdge(v: Node, w: Node) {
    adj.merge(v, mutableListOf(w)) { oldValue, increment -> oldValue.apply { addAll(increment) } }
    inDegree.merge(w, 1) { old, new -> old + new }
    edgeCount++
  }

  /**
   * Returns the vertices adjacent from vertex `v` in this digraph.
   *
   * @param  v the vertex
   * @return the vertices adjacent from vertex `v` in this digraph, as an iterable
   * @throws IllegalArgumentException unless `v` is in the graph.
   */
  fun adj(v: Node): Iterable<Node> = adj[v] ?: missingNode(v)

  fun map(): Map<Node, Collection<Node>> =
    LinkedHashMap<Node, Collection<Node>>(nodeCount).apply {
      putAll(adj)
    }

  /**
   * Returns the number of directed edges incident from vertex `v`.
   *
   * @param  v the vertex
   * @return the outdegree of vertex `v`
   * @throws IllegalArgumentException unless `v` is in the graph.
   */
  fun outdegree(v: Node): Int {
    return adj[v]?.size ?: missingNode(v)
  }

  /**
   * Returns the number of directed edges incident to vertex `v`.
   *
   * @param  v the vertex
   * @return the indegree of vertex `v`
   * @throws IllegalArgumentException unless `v` is in the graph.
   */
  fun indegree(v: Node): Int {
    return inDegree[v] ?: missingNode(v)
  }

  /**
   * Returns the reverse of the digraph.
   *
   * @return the reverse of the digraph
   */
  fun reverse(): DependencyGraph? {
    val reverse = DependencyGraph(nodeCount)
    for (v in adj.keys) {
      for (w in adj(v)) {
        reverse.addEdge(w, v)
      }
    }
    return reverse
  }

  /**
   * Returns a string representation of the graph.
   *
   * @return the number of vertices *nodeCount*, followed by the number of edges *edgeCount*,
   * followed by the *node* adjacency lists
   */
  override fun toString(): String = buildString {
    append("$nodeCount vertices, $edgeCount edges\n")
    for (v in adj.keys) {
      append("$v >> ")
      append(adj[v]!!.joinToString(separator = ", "))
      append("\n")
    }
  }

  private fun missingNode(v: Node): Nothing {
    throw IllegalArgumentException("Node $v is not in the graph")
  }

}
