package edu.iastate.cs.design.spec.analyze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import edu.iastate.cs.design.spec.entities.Method;
import edu.stanford.nlp.international.Language;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.EnglishGrammaticalRelations;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.util.CoreMap;

public class AnswerAnalysis {

    public static void main(String[] args) throws Exception {
        Method testMethod = new Method("testMethod", "void testMethod(String s, int i, List<String> list)", null, 0L);
        analyzeAnswer(testMethod, "The big list will contain the same elements. However, when testMethod runs, the String s will be changed.");
    }

    public static void analyzeAnswer(Method method, String answerText) throws IOException {
        File nerFile = null;
        try {
            nerFile = createRegexNerFile(method);
        } catch (IOException ioe) {
            System.out.println("Failed to create regex file. Exiting");
            return;
        }
        Properties nlpProperties = new Properties();
        nlpProperties.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner, parse, relation");
        nlpProperties.put("regexner.mapping", nerFile.getAbsolutePath());
        StanfordCoreNLP nlpPipeline = new StanfordCoreNLP(nlpProperties);
        Annotation answerAnnotation = new Annotation(answerText);
        nlpPipeline.annotate(answerAnnotation);
        List<CoreMap> sentences = answerAnnotation.get(SentencesAnnotation.class);
        List<String> paramsFound = new ArrayList<String>();
        for (CoreMap sentence : sentences) {
            SemanticGraph semanticGraph = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            System.out.println(semanticGraph.toString());
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                Set<IndexedWord> adjectives = semanticGraph.getChildrenWithReln(new IndexedWord(token), EnglishGrammaticalRelations.ADJECTIVAL_MODIFIER);
                String namedEntity = token.get(NamedEntityTagAnnotation.class);
                Set<IndexedWord> childList = new HashSet<IndexedWord>();
                for (SemanticGraphEdge edge : semanticGraph.outgoingEdgeIterable(new IndexedWord(token))) {
                    GrammaticalRelation relation = EnglishGrammaticalRelations.ADJECTIVAL_MODIFIER;
                    if (edge.getRelation().equals(EnglishGrammaticalRelations.ADJECTIVAL_MODIFIER)) {
                        childList.add(edge.getTarget());
                    }
                }
                if (namedEntity.equals("PARAM_NAME")) {
                    paramsFound.add(token.get(CoreAnnotations.TextAnnotation.class));

                    Set<IndexedWord> children = semanticGraph.getChildren(new IndexedWord(token));
                    for (IndexedWord adjective : adjectives) {
                        System.out.println(adjective);
                    }
                }
            }
        }

        nlpPipeline.xmlPrint(answerAnnotation, System.out);
    }

    private static File createRegexNerFile(Method method) throws IOException{
        File regexNerFile = File.createTempFile(method.getName(), ".tmp");
        regexNerFile.deleteOnExit();
        // extract return type and paramater data from signature
        String signature = method.getSignature();
        String parameterString = signature.substring(signature.indexOf('(') + 1, signature.indexOf(')'));
        List<String> parameterList = Arrays.asList(parameterString.split(",\\s"));
        List<String> paramTypes = new ArrayList<String>();
        List<String> paramNames = new ArrayList<String>();
        for (String parameter : parameterList) {
            String[] typeAndName = parameter.split("\\s");
            paramTypes.add(typeAndName[0]);
            paramNames.add(typeAndName[1]);
        }
        // write custom NER data to file
        FileWriter nerFileWriter = null;
        try {
            nerFileWriter = new FileWriter(regexNerFile);
            nerFileWriter.append(method.getName() + "\tTARGET_METHOD\n");
            // TODO return type?
            for (String paramType : paramTypes) {
                nerFileWriter.append(paramType + "\tPARAM_TYPE\n");
            }
            for (String paramName : paramNames) {
                nerFileWriter.append(paramName + "\tPARAM_NAME\n");
            }
        } finally {
            if (nerFileWriter != null) {
                nerFileWriter.close();
            }
        }
        return regexNerFile;
    }
}