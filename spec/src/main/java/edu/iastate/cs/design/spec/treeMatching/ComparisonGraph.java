package edu.iastate.cs.design.spec.treeMatching;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComparisonGraph implements Comparable<ComparisonGraph> {

    private HashMap<ComparisonGraphNode, List<ComparisonGraphNode>> edgeMap;
    private int frequencyCount;

    public ComparisonGraph() {
        edgeMap = new HashMap<ComparisonGraphNode, List<ComparisonGraphNode>>();
        frequencyCount = 1;
    }

    public void addEdge(ComparisonGraphNode source, ComparisonGraphNode destination) {
        List<ComparisonGraphNode> outgoingEdges;
        if (edgeMap.containsKey(source)) {
            outgoingEdges = edgeMap.get(source);
            outgoingEdges.add(destination);
        } else {
            outgoingEdges = new ArrayList<ComparisonGraphNode>(3);
            outgoingEdges.add(destination);
            edgeMap.put(source, outgoingEdges);
        }
    }

    public int getFrequencyCount() {
        return frequencyCount;
    }

    public void incrementFrequencyCount() {
        ++this.frequencyCount;
    }

    public int compareTo(ComparisonGraph o) {
        return Integer.compare(o.frequencyCount, this.frequencyCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComparisonGraph that = (ComparisonGraph) o;
        return edgeMap.equals(that.edgeMap);
    }

    @Override
    public int hashCode() {
        return edgeMap.hashCode();
    }
}
