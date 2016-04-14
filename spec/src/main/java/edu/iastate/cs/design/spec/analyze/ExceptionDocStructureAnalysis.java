package edu.iastate.cs.design.spec.analyze;

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

public class ExceptionDocStructureAnalysis implements ExceptionDocProcessor {

    private Map<Integer, ComparisonGraph> frequencyMap;

    public ExceptionDocStructureAnalysis() {
        frequencyMap = new HashMap<Integer, ComparisonGraph>();
    }

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
            } else {
                frequencyMap.get(graphHash).incrementFrequencyCount();
            }
        }
    }

    public Map<Integer, ComparisonGraph> getFrequencyMap() {
        return frequencyMap;
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

    private static StanfordCoreNLP initPipeline(String methodName, List<String> paramTypes, List<String> paramNames) {
        File regexNerFile = createRegexNerFile(methodName, paramTypes, paramNames);
        Properties nlpProperties = new Properties();
        nlpProperties.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner, parse, relation");
        nlpProperties.put("regexner.mapping", regexNerFile.getAbsolutePath());
        nlpProperties.put("parse.flags", "-makeCopulaHead");
        return new StanfordCoreNLP(nlpProperties);
    }

    private static File createRegexNerFile(String methodName, List<String> paramTypes, List<String> paramNames) {
        File regexNerFile = null;
        try {
            regexNerFile = File.createTempFile(methodName, ".tmp");
        } catch (IllegalArgumentException iae) {
            System.out.println("IAE. methodName=" + methodName);
            return null;
        } catch (IOException ioe) {
            System.out.println("Error creating temp regexner file" + ioe.toString());
        }
        // regexner doesn't like [] or <? extends Object> on the types
        for (int i = 0; i < paramTypes.size(); ++i) {
            String type = paramTypes.get(i);
            if (type.contains("[")) {
                String arrayStrippedType = type.replaceAll("\\[\\]", "");
                paramTypes.set(i, arrayStrippedType);
            }
            if (type.contains("<")) {
                int genericTypeStart = type.indexOf("<");
                int genericTypeIndexEnd = type.lastIndexOf(">");
                String genericTypeStrippedType = type.substring(0, genericTypeStart) + type.substring(genericTypeIndexEnd + 1);
                paramTypes.set(i, genericTypeStrippedType);
            }
        }
        //regexNerFile.deleteOnExit();
        String allowOverrideNers = "LOCATION,PERSON,ORGANIZATION,MONEY,PERCENT,DATE,TIME";
        // write custom NER data to file
        FileWriter nerFileWriter = null;
        try {
            nerFileWriter = new FileWriter(regexNerFile);
            nerFileWriter.append(methodName + "\tTARGET_METHOD\t" + allowOverrideNers + "\r\n");
            // TODO return type?
            for (String paramType : paramTypes) {
                nerFileWriter.append(paramType + "\tPARAM_TYPE\t" + allowOverrideNers + "\r\n");
            }
            for (String paramName : paramNames) {
                nerFileWriter.append(paramName + "\tPARAM_NAME\t" + allowOverrideNers + "\r\n");
            }
            nerFileWriter.append("null" + "\tJAVA_NULL\t" + allowOverrideNers + "\r\n");
        } catch (IOException ioe) {
            System.out.println("Error writing regexner file");
            return null;
        } finally {
            if (nerFileWriter != null) {
                try {
                    nerFileWriter.close();
                } catch (IOException ioe) {}
            }
        }
        return regexNerFile;
    }

}
