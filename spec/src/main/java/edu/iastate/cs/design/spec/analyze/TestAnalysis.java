package edu.iastate.cs.design.spec.analyze;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.CoreMapExpressionExtractor;
import edu.stanford.nlp.ling.tokensregex.MatchedExpression;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;


public class TestAnalysis {

  public static void main(String[] args) throws IOException {
    PrintWriter out;

    String rules;
    if (args.length > 0) {
      rules = args[0];
    } else {
      rules = "math.rules.txt";
    }
    if (args.length > 2) {
      out = new PrintWriter(args[2]);
    } else {
      out = new PrintWriter(System.out);
    }

    CoreMapExpressionExtractor extractor = CoreMapExpressionExtractor.createExtractorFromFiles(TokenSequencePattern.getNewEnv(), rules);

    Properties nlpProperties = new Properties();
    nlpProperties.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner, parse, relation");
    
    StanfordCoreNLP pipeline = new StanfordCoreNLP(nlpProperties);
    Annotation annotation;
    if (args.length > 1) {
      annotation = new Annotation(IOUtils.slurpFileNoExceptions(args[1]));
    } else {
    	annotation = new Annotation("Returns the trigonometric sine of an angle.");
      //annotation = new Annotation("I know Fred has acne.  And Wilma has breast cancer.");
      //annotation = new Annotation("( ( five plus three plus four ) * 2 ) divided by three");

    }

    pipeline.annotate(annotation);

    // An Annotation is a Map and you can get and use the various analyses individually.
    out.println();
    // The toString() method on an Annotation just prints the text of the Annotation
    // But you can see what is in it with other methods like toShorterString()
    out.println("The top level annotation");
    out.println(annotation.toShorterString());
    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

    for (CoreMap sentence : sentences) {
      List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);
      for (MatchedExpression matched:matchedExpressions) {
        // Print out matched text and value
        out.println("matched: " + matched.getText() + " with value " + matched.getValue());
        // Print out token information
        CoreMap cm = matched.getAnnotation();
        for (CoreLabel token : cm.get(CoreAnnotations.TokensAnnotation.class)) {
          String word = token.get(CoreAnnotations.TextAnnotation.class);
          String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
          String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
          String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
          out.println("matched token: " + "word="+word + ", lemma="+lemma + ", pos=" + pos + ", ne=" + ne);
        }
      }
    }
    out.flush();
  }

}
