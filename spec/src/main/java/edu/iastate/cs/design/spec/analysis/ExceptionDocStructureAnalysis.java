package edu.iastate.cs.design.spec.analysis;

import edu.iastate.cs.design.spec.parsing.JavadocParse;
import edu.iastate.cs.design.spec.treeMatching.ComparisonGraph;
import edu.iastate.cs.design.spec.treeMatching.ComparisonGraphNode;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ExceptionDocStructureAnalysis extends ExceptionDocProcessor {

    private Map<Integer, ComparisonGraph> frequencyMap;
    private Map<Integer, List<String>> documentationMap;

    public ExceptionDocStructureAnalysis() {
        frequencyMap = new HashMap<Integer, ComparisonGraph>();
        documentationMap = new HashMap<Integer, List<String>>();
    }

    public static void main(String[] args) {
        ExceptionDocStructureAnalysis analysis = new ExceptionDocStructureAnalysis();
        JavadocParse.run("C:\\Users\\chanika\\Desktop\\src\\java\\util", analysis);
        Map<Integer, ComparisonGraph> frequencyMap = analysis.getFrequencyMap();
        PriorityQueue<ComparisonGraph> frequencySortingQueue = new PriorityQueue<ComparisonGraph>(frequencyMap.values());
        while (!frequencySortingQueue.isEmpty()) {
            ComparisonGraph graph = frequencySortingQueue.remove();
            List<String> docs = analysis.getDocumentationMap().get(graph.hashCode());
            System.out.println(graph.getFrequencyCount());
        }
    }

    @Override
    public void process(String exceptionType, String documentation, List<String> paramTypes, List<String> paramNames, MethodDeclaration methodNode) {
        System.out.println("Processing " + methodNode.getName().toString());
        if (methodNode.getName().toString().length() < 3) {
            // TODO
            System.out.println("Method name too short, skipping this for now");
            return;
        }
        if (documentation.length() < 5) {
            // happens when {@inheritdoc} is used
            System.out.println("No documentation found, skipping");
            return;
        }
        StanfordCoreNLP pipeline = initPipeline(methodNode.getName().toString(), paramTypes, paramNames);
        Annotation exceptionAnnotation = new Annotation(documentation);
        pipeline.annotate(exceptionAnnotation);

        List<CoreMap> sentences = exceptionAnnotation.get(CoreAnnotations.SentencesAnnotation.class);
        System.out.println(documentation);
        List<ComparisonGraph> comparisonGraphs = new ArrayList<ComparisonGraph>();
        for (CoreMap sentence : sentences) {
            SemanticGraph semanticGraph = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            comparisonGraphs.addAll(createComparisonGraphs(semanticGraph));
        }

        for (ComparisonGraph graph : comparisonGraphs) {
            Integer graphHash = graph.hashCode();
            if (!frequencyMap.containsKey(graphHash)) {
                frequencyMap.put(graphHash, graph);
                List<String> docList = new ArrayList<String>();
                docList.add(documentation);
                documentationMap.put(graphHash, docList);
            } else {
                frequencyMap.get(graphHash).incrementFrequencyCount();
                documentationMap.get(graphHash).add(documentation);
            }
        }
    }

    public Map<Integer, ComparisonGraph> getFrequencyMap() {
        return frequencyMap;
    }

    public Map<Integer, List<String>> getDocumentationMap() {
        return documentationMap;
    }

    private static List<ComparisonGraph> createComparisonGraphs(SemanticGraph semanticGraph) {
        List<ComparisonGraph> subgraphs = new ArrayList<ComparisonGraph>();
        Set<IndexedWord> tokens = semanticGraph.vertexSet();
        for (IndexedWord token : tokens) {
            ComparisonGraph comparisonGraph = createComparisonGraph(token, semanticGraph);
            if (comparisonGraph != null) {
                subgraphs.add(comparisonGraph);
            }
        }
        return subgraphs;
    }

    private static ComparisonGraph createComparisonGraph(IndexedWord token, SemanticGraph semanticGraph) {
        if (semanticGraph.getChildren(token).size() == 0) {
            return null;
        }
        ComparisonGraph comparisonGraph = new ComparisonGraph();
        Stack<IndexedWord> tokenStack = new Stack<IndexedWord>();
        tokenStack.push(token);
        while (!tokenStack.empty()) {
            IndexedWord root = tokenStack.pop();
            CoreLabel rootLabel = root.backingLabel();
            String rootPos = rootLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            String rootNer = rootLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
            ComparisonGraphNode rootNode = new ComparisonGraphNode(rootPos, rootNer);
            Iterator<SemanticGraphEdge> iter = semanticGraph.outgoingEdgeIterator(root);
            while (iter.hasNext()) {
                IndexedWord dep = iter.next().getDependent();
                CoreLabel depLabel = dep.backingLabel();
                String depPos = depLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String depNer = depLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                ComparisonGraphNode depNode = new ComparisonGraphNode(depPos, depNer);
                comparisonGraph.addEdge(rootNode, depNode);
                tokenStack.push(dep);
            }
        }
        return comparisonGraph;
    }
}
