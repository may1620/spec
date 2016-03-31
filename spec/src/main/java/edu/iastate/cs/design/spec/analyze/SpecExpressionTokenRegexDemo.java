package edu.iastate.cs.design.spec.analyze;

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

public class SpecExpressionTokenRegexDemo {

    public static void main(String[] args) throws IOException {
        String rules = "C:\\Users\\chanika\\git\\spec\\spec\\src\\main\\java\\edu\\iastate\\cs\\design\\spec\\analyze\\spec.rules.txt";
        PrintWriter out = new PrintWriter(System.out);

        CoreMapExpressionExtractor extractor = CoreMapExpressionExtractor
                .createExtractorFromFiles(
                        TokenSequencePattern.getNewEnv(),
                        rules);

        StanfordCoreNLP pipeline = new StanfordCoreNLP();
        Annotation annotation = new Annotation("if length < 5. if max less than twelve. if original is null. if original equals null. ");
        pipeline.annotate(annotation);

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);
            for (MatchedExpression matched:matchedExpressions) {
                out.println("matched: " + matched.getText() + " with value " + matched.getValue());
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
        pipeline.xmlPrint(annotation, out);
        out.flush();
    }
}
