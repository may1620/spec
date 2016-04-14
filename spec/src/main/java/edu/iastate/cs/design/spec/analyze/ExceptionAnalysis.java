package edu.iastate.cs.design.spec.analyze;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.tokensregex.CoreMapExpressionExtractor;
import edu.stanford.nlp.ling.tokensregex.EnvLookup;
import edu.stanford.nlp.ling.tokensregex.MatchedExpression;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.semgrex.Env;
import edu.stanford.nlp.util.CoreMap;

public class ExceptionAnalysis {
	
	private static final String RULES_1 = "param.rules.txt";
	private static final String RULES_2 = "math.rules.txt";

	
    public static void analyzeThrows(String exceptionType, String documentation, MethodDeclaration node) throws IOException {
        List<SingleVariableDeclaration> params = node.parameters();
        List<String> paramTypes = new ArrayList<String>();
        List<String> paramNames = new ArrayList<String>();
        for (SingleVariableDeclaration param : params) {
            paramTypes.add(param.getType().toString());
            paramNames.add(param.getName().toString());
        }
        System.out.println(paramTypes);
        System.out.println(paramNames);
        System.out.println(exceptionType);
        System.out.println(documentation);
        analyze(documentation, paramNames);
    }
	
    
    public static void analyze(String throwsText, List<String> paramNames) throws IOException {
        
    	CoreMapExpressionExtractor extractor = CoreMapExpressionExtractor.createExtractorFromFiles(TokenSequencePattern.getNewEnv(), RULES_1, RULES_2);
    	Properties nlpProperties = new Properties();
        nlpProperties.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner, parse, relation");
        
        StanfordCoreNLP pipeline = new StanfordCoreNLP(nlpProperties);
        
        Annotation exceptionAnnotation = new Annotation(throwsText);
        
        
        pipeline.annotate(exceptionAnnotation);
        List<CoreMap> sentences = exceptionAnnotation.get(SentencesAnnotation.class);
        String rawSpec = "";
        for (CoreMap sentence : sentences) {
        	
        	List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);
        	for(MatchedExpression matched : matchedExpressions) {
        		System.out.println("matched: '" + matched.getText() + "' with value " + matched.getValue());
        		rawSpec = matched.getValue().toString();
        	}
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                System.out.println("matched token: " + "word="+word + ", pos=" + pos + ", ne=" + ne);
                if(ne.equals("PARAMETER") && rawSpec.contains(word)) {
                	rawSpec = rawSpec.replace(word, paramNames.get(0));
                }
              }  
        }
        System.out.println(rawSpec);

    }
	
}
