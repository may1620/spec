package edu.iastate.cs.design.spec.analyze;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComparisonGraph {

    private HashMap<ComparisonGraphNode, List<ComparisonGraphNode>> edgeMap;

    public ComparisonGraph() {
        edgeMap = new HashMap<ComparisonGraphNode, List<ComparisonGraphNode>>();
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
