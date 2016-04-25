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

public class ExceptionParseAnalysis extends ExceptionDocProcessor {
	
	private static final String RULES_1 = "param.rules.txt";
	private static final String RULES_2 = "math.rules.txt";
    private int numProcessed;
    private int numSkipped;
    private int numMatched;

    public ExceptionParseAnalysis() {
        numProcessed = 0;
        numSkipped = 0;
        numMatched = 0;
    }

    public static void main(String[] args) {
        ExceptionParseAnalysis analysis = new ExceptionParseAnalysis();
        JavadocParse.run("C:\\Users\\Alex\\Desktop\\src\\java\\util", analysis);
        System.out.println("processed: " + analysis.getNumProcessed());
        System.out.println("skipped: " + analysis.getNumSkipped());
        System.out.println("matched: " + analysis.getNumMatched());
    }

    @Override
    public void process(String exceptionType, String documentation, List<String> paramTypes, List<String> paramNames, MethodDeclaration methodNode) {
        System.out.println("Processing " + methodNode.getName().toString());
        ++numProcessed;
        if (methodNode.getName().toString().length() < 3) {
            // TODO
            System.out.println("Method name too short, skipping this for now");
            ++numSkipped;
            return;
        }
        if (documentation.length() < 10) {
            // happens when {@inheritdoc} is used
            System.out.println("No documentation found, skipping");
            ++numSkipped;
            return;
        }
        CoreMapExpressionExtractor extractor = CoreMapExpressionExtractor.createExtractorFromFiles(TokenSequencePattern.getNewEnv(), RULES_1, RULES_2);

        StanfordCoreNLP pipeline = initPipeline(methodNode.getName().toString(), paramTypes, paramNames);
        if (pipeline == null) {
            ++numSkipped;
            return;
        }
        Annotation exceptionAnnotation = new Annotation(documentation);
        pipeline.annotate(exceptionAnnotation);

        List<CoreMap> sentences = exceptionAnnotation.get(SentencesAnnotation.class);
        String rawSpec = "";
        for (CoreMap sentence : sentences) {
            List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);
            for(MatchedExpression matched : matchedExpressions) {
                System.out.println("matched: '" + matched.getText() + "' with value " + matched.getValue());
                rawSpec = matched.getValue().toString();
                ++numMatched;
            }
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                System.out.println("matched token: " + "word="+word + ", pos=" + pos + ", ne=" + ne);
                if(ne.equals("PARAMETER") && rawSpec.contains(word)) {
                    rawSpec = handleParameterReplacement(rawSpec, paramNames, paramTypes, word);
                }
                if(ne.equals("FINAL_COMPARATIVE")) {
                	rawSpec = handleFinalComparative(rawSpec, word);
                }

                
            }
        }
        if(!rawSpec.equals("")) {
	    	PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("results.txt", true)));
	            out.println(methodNode.getName() +  "  ---  " + rawSpec);
	            out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        System.out.println(rawSpec);
        }
    }

    public int getNumProcessed() {
        return numProcessed;
    }

    public int getNumSkipped() {
        return numSkipped;
    }

    public int getNumMatched() {
        return numMatched;
    }
    
    public String handleFinalComparative(String rawSpec, String word) {
    	if(word.equals("negative")) {
    		rawSpec = rawSpec.replace("*", "< 0");
    	}
    	else if(word.equals("positive")) {
    		rawSpec = rawSpec.replace("*", "> 0");
    	}
    	else if(word.equals("zero")) {
    		rawSpec = rawSpec.replace("*", "== 0");
    	}
    	return rawSpec;
    }
    
    public String handleParameterReplacement(String rawSpec, List<String> paramNames, List<String> paramTypes, String word) {
    	if(paramNames.contains(word)) {
    		return rawSpec;
    	}
    	
    	for(int i = 0; i < paramTypes.size(); i++) {
    		String tempType = paramTypes.get(i);
    		if(!tempType.equals("byte") && !tempType.equals("short") &&
    				!tempType.equals("int") && !tempType.equals("long") && 
    				!tempType.equals("float") && !tempType.equals("double") && 
    				!tempType.equals("boolean") && !tempType.equals("char")) {
    			rawSpec = rawSpec.replace(word, paramNames.get(0));
    		}
    	}
    	return rawSpec;
    }
    
    
}
