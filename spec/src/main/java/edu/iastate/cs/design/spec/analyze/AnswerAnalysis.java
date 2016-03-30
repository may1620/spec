package edu.iastate.cs.design.spec.analyze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

import edu.iastate.cs.design.spec.entities.Method;
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
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.UniversalEnglishGrammaticalRelations;
import edu.stanford.nlp.util.CoreMap;

public class AnswerAnalysis {
	
	private static List<SimpleEntry<String, Integer>> paramIds = new ArrayList<SimpleEntry<String, Integer>>();

    public static void main(String[] args) throws Exception {
        Method testMethod = new Method("randInt", "int randInt(int min, int max)", null, 0L);
        analyzeAnswer(testMethod, "Returns a number less than ten. This method returns an int greater than 5. randInt returns a number less than max");
        
    //    Method testMethod = new Method("randInt", "int randInt(int n)", null, 0L);
    //    analyzeAnswer(testMethod, "Returns a pseudorandom, uniformly distributed int value between 0 (inclusive) and the specified value (exclusive), drawn from this random number generator's sequence.");
    }

    public static void analyzeAnswer(Method method, String answerText) throws IOException {
        File nerFile = null;
        try {
            nerFile = createRegexNerFile(method);
        } catch (IOException ioe) {
            System.out.println("Failed to create regex file. Exiting");
            return;
        }
               
        
        answerText = createParamIds(method, answerText);
        
        Properties nlpProperties = new Properties();
        nlpProperties.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner, parse, relation");
        nlpProperties.put("regexner.mapping", nerFile.getAbsolutePath());
        nlpProperties.put("parse.flags", "-makeCopulaHead");
        StanfordCoreNLP nlpPipeline = new StanfordCoreNLP(nlpProperties);
        
        Annotation answerAnnotation = new Annotation(answerText);
        nlpPipeline.annotate(answerAnnotation);
        List<CoreMap> sentences = answerAnnotation.get(SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            SemanticGraph semanticGraph = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            SentenceClassification classification = classifySentence(sentence);
            switch (classification) {
                case NOT_HELPFUL:
                    System.out.println("Nothing useful was found in this sentence");
                    break;
                case PRECONDITION:
                    break;
                case POSTCONDITION:
                    break;
                case RETURN:
                    analyzeReturnSentence(sentence);
                    break;
                case EXCEPTIONAL:
                    break;
            }

        }
        nlpPipeline.xmlPrint(answerAnnotation, System.out);
    }



    private static void analyzeReturnSentence(CoreMap sentence) {
        System.out.println("Analyzing return information: " + sentence.get(CoreAnnotations.TextAnnotation.class));
        SemanticGraph semanticGraph = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
        IndexedWord root = semanticGraph.getFirstRoot();
        Collection<GrammaticalRelation> returnValueRelations = new ArrayList<GrammaticalRelation>();
        returnValueRelations.add(GrammaticalRelation.DEPENDENT);
        returnValueRelations.add(UniversalEnglishGrammaticalRelations.DIRECT_OBJECT);
        Set<IndexedWord> deps = semanticGraph.getChildrenWithRelns(root, returnValueRelations);

        IndexedWord dep = null;
        SpecificationValueType returnType;
        for (IndexedWord word : deps) {
            if (isDependentRelevant(word)) {
                returnType = getReturnType(word, semanticGraph);
                dep = word;
            }
        }
        Collection<GrammaticalRelation> relevantRelations = new ArrayList<GrammaticalRelation>();
        relevantRelations.add(UniversalEnglishGrammaticalRelations.MODIFIER);
        relevantRelations.add(UniversalEnglishGrammaticalRelations.NOMINAL_MODIFIER);
        relevantRelations.add(GrammaticalRelation.DEPENDENT);
        relevantRelations.add(UniversalEnglishGrammaticalRelations.NUMERIC_MODIFIER);
        if (dep != null) {
            Set<IndexedWord> depModifiers = semanticGraph.getChildrenWithRelns(dep, relevantRelations);
            System.out.println("Modifiers: " + depModifiers.toString());
            for (IndexedWord modifier : depModifiers) {
                CoreLabel token = modifier.backingLabel();
                String ner = token.get(NamedEntityTagAnnotation.class);
                String value = "";
                if (modifier.ner().equals("NUMBER")) {
                    String normNer = token.get(CoreAnnotations.NormalizedNamedEntityTagAnnotation.class);
                    System.out.println(normNer);
                    value = normNer.substring(1, normNer.length());
                }
                System.out.println(modifier.toString() + " " + ner);
                relevantRelations.add(UniversalEnglishGrammaticalRelations.ADV_CLAUSE_MODIFIER);
                relevantRelations.add(UniversalEnglishGrammaticalRelations.ADVERBIAL_MODIFIER);
                Set<IndexedWord> modifierAdjectives = semanticGraph.getChildrenWithRelns(modifier, relevantRelations);
                for (IndexedWord adjective : modifierAdjectives) {
                    System.out.println("adj: " + adjective.toString());
                }
                IndexedWord comparison = filterRelevantAdjectives(modifierAdjectives);
                ComparisonOperator comp = stringToComparison(comparison.originalText());
                
                try {
	                for(SimpleEntry<String, Integer> paramId : paramIds) {
	                	int id = paramId.getValue().intValue();
	                	if(Double.valueOf(value).intValue() == id) {
	                		value = paramId.getKey();
	                		break;
	                	}
	                }
                }
                catch(NumberFormatException nfe) {
                	
                }
                System.out.println("Final specification: ensures " + "\\return " + comp.toString() + " " + value);
            }
        }
        System.out.println(semanticGraph.toString() + '\n');
    }

    private static IndexedWord filterRelevantAdjectives(Set<IndexedWord> adjectives) {
        for (IndexedWord adjective : adjectives) {
            if (adjective.originalText().equals("less") || adjective.originalText().equals("greater")) {
                return adjective;
            }
        }
        return null;
    }

    private static ComparisonOperator stringToComparison(String s) {
        if (s.equals("less")) {
            return ComparisonOperator.LESS_THAN;
        } else  if (s.equals("greater")){
            return ComparisonOperator.GREATER_THAN;
        }
        return null;
    }


    // This function can look at relevant information from the signature and words used in the phrase
    private static SpecificationValueType getReturnType(IndexedWord word, SemanticGraph graph) {
        return SpecificationValueType.INTEGER;
    }

    private enum SpecificationValueType {
        INTEGER, FLOAT, STRING
    }

    private static boolean isDependentRelevant(IndexedWord dependent) {
        return true;
    }


    private enum ComparisonOperator {
        EQUAL,
        NOT_EQUAL,
        LESS_THAN,
        LESS_THAN_EQUAL,
        GREATER_THAN,
        GREATER_THAN_EQUAL,
        AND,
        OR;

        public String toString() {
            switch (this) {
                case EQUAL:
                    return "==";
                case NOT_EQUAL:
                    return "!=";
                case LESS_THAN:
                    return "<";
                case LESS_THAN_EQUAL:
                    return "<=";
                case GREATER_THAN:
                    return ">";
                case GREATER_THAN_EQUAL:
                    return ">=";
                case AND:
                    return "&&";
                case OR:
                    return "||";
                default:
                    return "";
            }
        }
    }

    private enum SentenceClassification {
        NOT_HELPFUL,
        PRECONDITION,
        POSTCONDITION,
        RETURN,
        EXCEPTIONAL
    }

    // TODO currently only looks for return statements
    private static SentenceClassification classifySentence(CoreMap sentence) {
        SemanticGraph semanticGraph = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
        IndexedWord root = semanticGraph.getFirstRoot();
        if (root.get(CoreAnnotations.TextAnnotation.class).matches("[Rr]eturn+(s)?")) {
            return SentenceClassification.RETURN;
        } else {
            return SentenceClassification.NOT_HELPFUL;
        }
    }

    private static File createRegexNerFile(Method method) throws IOException{
        File regexNerFile = File.createTempFile(method.getName(), ".tmp");
        //regexNerFile.deleteOnExit();
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
            nerFileWriter.append(method.getName() + "\tTARGET_METHOD\r\n");
            // TODO return type?
            for (String paramType : paramTypes) {
                nerFileWriter.append(paramType + "\tPARAM_TYPE\r\n");
            }
            for (String paramName : paramNames) {
            	if(paramName.equals("max")) {
            		 nerFileWriter.append("max" + "\tNUMBER\r\n");
            	} else {
            		 nerFileWriter.append(paramName + "\tPARAM_NAME\r\n");
            	}
               
            }
           
        } finally {
            if (nerFileWriter != null) {
                nerFileWriter.close();
            }
        }
        return regexNerFile;
    }
    
    private static String createParamIds(Method method, String text) {
        String signature = method.getSignature();
        String parameterString = signature.substring(signature.indexOf('(') + 1, signature.indexOf(')'));
        List<String> parameterList = Arrays.asList(parameterString.split(",\\s"));
        List<String> paramTypes = new ArrayList<String>();
        List<String> paramNames = new ArrayList<String>();
        int paramId = 54164;
        for (String parameter : parameterList) {
            String[] typeAndName = parameter.split("\\s");
            paramTypes.add(typeAndName[0]);
            paramNames.add(typeAndName[1]);
            if(typeAndName[0].equals("int")) {
            	paramIds.add(new SimpleEntry<String, Integer>(typeAndName[1], paramId));
            	text = text.replaceAll("\\b" + typeAndName[1] + "\\b", String.valueOf(paramId));
            	paramId++;
            }
            
        }
        
        return text;
    }
}