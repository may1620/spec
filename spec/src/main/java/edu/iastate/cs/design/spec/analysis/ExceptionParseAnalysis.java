package edu.iastate.cs.design.spec.analysis;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import edu.iastate.cs.design.spec.parsing.JavadocParse;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.tokensregex.CoreMapExpressionExtractor;
import edu.stanford.nlp.ling.tokensregex.MatchedExpression;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class ExceptionParseAnalysis implements IExceptionDocProcessor {
	
	private static final String RULES_1 = "param.rules.txt";
	private static final String RULES_2 = "math.rules.txt";

    public static void main(String[] args) {
        ExceptionParseAnalysis analysis = new ExceptionParseAnalysis();
        JavadocParse.run("C:\\Users\\Alex\\Desktop\\src\\java\\util", analysis);
    }

    public void process(String exceptionType, String documentation, List<String> paramTypes, List<String> paramNames, MethodDeclaration methodNode) {
        CoreMapExpressionExtractor extractor = CoreMapExpressionExtractor.createExtractorFromFiles(TokenSequencePattern.getNewEnv(), RULES_1, RULES_2);

        Properties nlpProperties = new Properties();
        nlpProperties.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner, parse, relation");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(nlpProperties);
        Annotation exceptionAnnotation = new Annotation(documentation);
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
                	PrintWriter out;
					try {
						out = new PrintWriter(new BufferedWriter(new FileWriter("results.txt", true)));
	                    out.println(methodNode.getName() +  "  ---  " + rawSpec);
	                    out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

                }
            }
        }
        System.out.println(rawSpec);
    }
}
