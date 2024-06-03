package com.eltonls.chess.model.adjList;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<TileNode, List<TileNode>> nodes; // Adjacency List

    public Graph(Map<TileNode, List<TileNode>> nodes) {
        this.nodes = nodes;
    }

    public void addVertex(TileNode newVertex) {
        List<TileNode> edges = new java.util.ArrayList<>();
        nodes.putIfAbsent(newVertex, edges);
    }

    public void removeVertex(TileNode vertex) {
        nodes.values().forEach(edges -> edges.remove(vertex));
        nodes.remove(vertex);
    }

    public void addEdge(TileNode from, TileNode to) {
        nodes.get(from).add(to);
    }

    public void removeEdge(TileNode from, TileNode to) {
        nodes.get(from).remove(to);
    }

    public Set<TileNode> getVertices() { // <Node>
        return nodes.keySet();
    }

    public List<TileNode> getEdges(TileNode vertex) {
        return nodes.get(vertex);
    }

    public List<TileNode> getNeighbors() {
        // Get neighbors from the first vertex
        return nodes.get(nodes.keySet().iterator().next());
    }

    public boolean hasEdge(TileNode from, TileNode to) {
        return nodes.get(from).contains(to);
    }

    public boolean hasVertex(TileNode vertex) {
        return nodes.containsKey(vertex);
    }

    public void mergeGraph(Graph graph) {
        nodes.putAll(graph.getNodes());
    }

    public Map<TileNode, List<TileNode>> getNodes() {
        return nodes;
    }

    public void setNodes(Map<TileNode, List<TileNode>> nodes) {
        this.nodes = nodes;
    }
}
